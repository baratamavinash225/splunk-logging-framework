package com.avinash.log.splunk.v2

import com.avinash.log.LogLevel._
import com.avinash.log.{LogLevel, _}
import io.circe.Json
import org.slf4j.Logger

import java.util.concurrent.{Executors, ThreadFactory}
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

case class SplunkConfig(splunkHecUrl: String,
                        token: String,
                        source: String,
                        sourceType: String,
                        topic: String,
                        index: String,
                        notifyGroup: String,
                        logLevel: LogLevel,
                        logToConsole: Boolean = false,
                        async: Boolean = true)

class SplunkLogger(clazz: Class[_], logLevel: LogLevel, config: SplunkConfig)
                  (implicit log: Logger, ec: ExecutionContext)
  extends CentLogger(logLevel) {

  var splunk: SplunkClient = new SplunkClient(config.splunkHecUrl, config.token)

  def getBaseSplunkMessage(splunkMsg: LogMessage, messageLogLevel: LogLevel): List[(String, Json)] = {

    val notifyGroupToLog = if (splunkMsg.notifyGroup == noGroup) config.notifyGroup else splunkMsg.notifyGroup

    val baseEventList = List(
      ("message", Json.fromString(splunkMsg.msg)),
      ("notifyGroup", Json.fromString(notifyGroupToLog)),
      ("logLevel", Json.fromString(messageLogLevel.toString)),
      ("user", Json.fromString(UserInfo.name)),
      ("topic", Json.fromString(config.topic))
    )
    val additionalInfoList = splunkMsg.additionalInfo.map {
      case (key, value) => (key, Json.fromString(value))
    }.toList

    List(
      ("event", Json.fromFields(baseEventList ::: additionalInfoList)),
      ("index", Json.fromString(config.index)),
      ("source", Json.fromString(config.source)),
      ("sourcetype", Json.fromString(config.sourceType)), // sourcetype must be in lower case or else it gives 400 bad message
      ("host", Json.fromString(MachineInfo.hostname)),
      ("time", Json.fromString((System.currentTimeMillis() / 1000).toString))
    )
  }

  override def info(msg: LogMessage): Unit = {
    if (config.logToConsole)
      log.info(msg.toLogString)
    splunk.log(toJson(getBaseSplunkMessage(msg, INFO)), config.async)
  }

  override def error(msg: LogMessage): Unit = {
    if (config.logToConsole)
      log.error(msg.toLogString)
    splunk.log(toJson(getBaseSplunkMessage(msg, ERROR)), async = false)   //error logging is always using sync client
  }

  override def warn(msg: LogMessage): Unit = {
    if (config.logToConsole)
      log.warn(msg.toLogString)
    splunk.log(toJson(getBaseSplunkMessage(msg, WARN)), config.async)
  }

  override def debug(msg: LogMessage): Unit = {
    if (config.logToConsole)
      log.debug(msg.toLogString)
    splunk.log(toJson(getBaseSplunkMessage(msg, DEBUG)), config.async)
  }

  override def trace(msg: LogMessage): Unit = {
    if (config.logToConsole)
      log.trace(msg.toLogString)
    splunk.log(toJson(getBaseSplunkMessage(msg, TRACE)), config.async)
  }

  override def audit(msg: LogMessage): Unit = {
    if (config.logToConsole)
      log.info(msg.toLogString)
    splunk.log(toJson(getBaseSplunkMessage(msg, AUDIT)), config.async)
  }

  private def toJson(fields: Iterable[(String, Json)]): Json = Json.fromFields(fields)

  def getConf: SplunkConfig = config
}

object SplunkLogger {

  /**
   * Two named threads will be used for logging to splunk
   */
  implicit val ec: ExecutionContextExecutor =
    ExecutionContext.fromExecutor(
      Executors.newFixedThreadPool(2,
        new ThreadFactory {
          var counter: Int = -1

          override def newThread(r: Runnable): Thread = {
            counter += 1
            new Thread(r, s"splunk-logger-$counter")
          }
        }))

  def apply(clazz: Class[_], splunkConfig: SplunkConfig)(implicit log: Logger): SplunkLogger = {
    new SplunkLogger(clazz, splunkConfig.logLevel, splunkConfig)
  }

  def apply(clazz: Class[_], config: Map[String, String])(implicit log: Logger): SplunkLogger = {
    checkRequired(config)
    val splunkConfig: SplunkConfig = SplunkConfig(
      config.getOrElse("splunk.logging.hecUrl", "https://splunk-hec-tpc.global.tesco.org/services/collector"),
      config("splunk.auth.token"),
      config.getOrElse("splunk.search.source", "cef"),
      config("splunk.search.sourceType"),
      config.getOrElse("splunk.search.topic", "NO_TOPIC"),
      config("splunk.search.index"),
      config.getOrElse("splunk.appLogging.notifyGroup", "cef"),
      LogLevel.unApply(config.getOrElse("splunk.appLogging.logLevel", "INFO")),
      config.getOrElse("splunk.logToConsole", "false").toBoolean,
      config.getOrElse("splunk.async", "true").toBoolean
    )
    apply(clazz, splunkConfig)
  }

  def checkRequired(config: Map[String, String]): Unit = {
    val requiredKeys = Seq("splunk.search.sourceType", "splunk.search.index", "splunk.auth.token")
    requiredKeys.foreach(key => {
      if (!config.contains(key))
        throw SplunkInitializationException(s"Can not initialize Splunk Logger, required key not found. $key")
    })
  }
}