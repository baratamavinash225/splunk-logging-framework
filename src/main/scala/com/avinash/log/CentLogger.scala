package com.avinash.log

import com.avinash.log.LogLevel.{LogLevel, _}
import com.avinash.log.console.ConsoleLogger
import com.avinash.log.log4j.Log4jLogger
import com.avinash.log.splunk.v2.SplunkLogger
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters.mapAsScalaMapConverter
import scala.collection.mutable

/**
 * Abstract class that must be implemented to get a Logger compatible with various cef components.
 * <b>Beware</b> that the functions which are not implemented do not honor the [[logLevel]]
 * and will result in logging of message when called. It is up to the specefic implementation to
 * take care of omitting messages published at a log level which is lower than [[logLevel]].
 *
 * In general functions which are not taking pre-built [[LogMessage]] instance should be used.
 *
 * @param logLevel The level of log messages
 */
abstract class CentLogger(logLevel: LogLevel) {

  def info(msg: LogMessage): Unit

  def debug(msg: LogMessage): Unit

  def warn(msg: LogMessage): Unit

  def error(msg: LogMessage): Unit

  def audit(msg: LogMessage): Unit

  def trace(msg: LogMessage): Unit

  def log(msg: LogMessage, level: LogLevel): Unit = {
    level match {
      case WARN => warn(msg)
      case INFO => info(msg)
      case AUDIT => audit(msg)
      case ERROR => error(msg)
      case DEBUG =>
        if ((DEBUG == logLevel) || (TRACE == logLevel))
          debug(msg)
      case TRACE =>
        if (TRACE == logLevel)
          trace(msg)
    }
  }

  def info(msg: String): Unit = {
    log(LogMessage.build(msg), INFO)
  }

  def info(msg: String, notifyGroup: String): Unit = {
    log(LogMessage.build(msg, notifyGroup), INFO)
  }

  def info(msg: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, throwable), INFO)
  }

  def info(msg: String, notifyGroup: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo), INFO)
  }

  def info(msg: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo), INFO)
  }

  def info(msg: String, notifyGroup: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo.asScala), INFO)
  }

  def info(msg: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo.asScala), INFO)
  }

  def info(msg: String, notifyGroup: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, notifyGroup, throwable), INFO)
  }

  def debug(msg: String): Unit = {
    log(LogMessage.build(msg), DEBUG)
  }

  def debug(msg: String, notifyGroup: String): Unit = {
    log(LogMessage.build(msg, notifyGroup), DEBUG)
  }

  def debug(msg: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, throwable), DEBUG)
  }

  def debug(msg: String, notifyGroup: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo), DEBUG)
  }

  def debug(msg: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo), DEBUG)
  }

  def debug(msg: String, notifyGroup: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo.asScala), DEBUG)
  }

  def debug(msg: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo.asScala), DEBUG)
  }

  def debug(msg: String, notifyGroup: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, notifyGroup, throwable), DEBUG)
  }

  def warn(msg: String): Unit = {
    log(LogMessage.build(msg), WARN)
  }

  def warn(msg: String, notifyGroup: String): Unit = {
    log(LogMessage.build(msg, notifyGroup), WARN)
  }

  def warn(msg: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, throwable), WARN)
  }

  def warn(msg: String, notifyGroup: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo), WARN)
  }

  def warn(msg: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo), WARN)
  }

  def warn(msg: String, notifyGroup: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo.asScala), WARN)
  }

  def warn(msg: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo.asScala), WARN)
  }

  def warn(msg: String, notifyGroup: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, notifyGroup, throwable), WARN)
  }

  def error(msg: String): Unit = {
    log(LogMessage.build(msg), ERROR)
  }

  def error(msg: String, notifyGroup: String): Unit = {
    log(LogMessage.build(msg, notifyGroup), ERROR)
  }

  def error(msg: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, throwable), ERROR)
  }

  def error(msg: String, notifyGroup: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo), ERROR)
  }

  def error(msg: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo), ERROR)
  }

  def error(msg: String, notifyGroup: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo.asScala), ERROR)
  }

  def error(msg: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo.asScala), ERROR)
  }

  def error(msg: String, notifyGroup: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, notifyGroup, throwable), ERROR)
  }

  def audit(msg: String): Unit = {
    log(LogMessage.build(msg), AUDIT)
  }

  def audit(msg: String, notifyGroup: String): Unit = {
    log(LogMessage.build(msg, notifyGroup), AUDIT)
  }

  def audit(msg: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, throwable), AUDIT)
  }

  def audit(msg: String, notifyGroup: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo), AUDIT)
  }

  def audit(msg: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo), AUDIT)
  }

  def audit(msg: String, notifyGroup: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo.asScala), AUDIT)
  }

  def audit(msg: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo.asScala), AUDIT)
  }

  def audit(msg: String, notifyGroup: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, notifyGroup, throwable), AUDIT)
  }

  def trace(msg: String): Unit = {
    log(LogMessage.build(msg), TRACE)
  }

  def trace(msg: String, notifyGroup: String): Unit = {
    log(LogMessage.build(msg, notifyGroup), TRACE)
  }

  def trace(msg: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, throwable), TRACE)
  }

  def trace(msg: String, notifyGroup: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo), TRACE)
  }

  def trace(msg: String, additionalInfo: mutable.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo), TRACE)
  }

  def trace(msg: String, notifyGroup: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage(msg, notifyGroup, additionalInfo.asScala), TRACE)
  }

  def trace(msg: String, additionalInfo: java.util.Map[String, String]): Unit = {
    log(LogMessage.build(msg, additionalInfo.asScala), TRACE)
  }

  def trace(msg: String, notifyGroup: String, throwable: Throwable): Unit = {
    log(LogMessage.build(msg, notifyGroup, throwable), TRACE)
  }

}

object CentLogger {

  def apply(clazz: Class[_]): CentLogger = {
    apply(clazz, Map.empty, LOG4J)
  }

  def apply(clazz: Class[_], config: java.util.Map[String, String]): CentLogger = {
    apply(clazz, config.asScala.toMap)
  }

  def apply(clazz: Class[_], config: Map[String, String] = Map.empty, loggerType: LoggerType = SPLUNK): CentLogger = {
    implicit val log: Logger = LoggerFactory.getLogger(clazz.getName)

    loggerType match {
      case SPLUNK =>
        if (config.exists(_ == "splunk.logging.enabled" -> "true")) {
          CentLogger(clazz, config)
        } else {
          log.warn("You tried to create cef logger of SPLUNK type but config does not have " +
            "splunk.logging.enabled set to true, falling back to Log4j")
          Log4jLogger(clazz) //fallback to default log4j logger
        }
      case CONSOLE => ConsoleLogger(clazz)
      case LOG4J => Log4jLogger(clazz)
      case _ => throw new NotImplementedError(s"Logger type $loggerType is not implemented.")
    }
  }

  def apply(clazz: Class[_], cefLogger: Logger): CentLogger = {
    implicit val log: Logger = LoggerFactory.getLogger(clazz.getName)
    cefLogger match {
      case s: SplunkLogger => SplunkLogger(clazz, s.getConf)
      case _: Log4jLogger => Log4jLogger(clazz)
      case _ => ConsoleLogger(clazz)
    }
  }
}

