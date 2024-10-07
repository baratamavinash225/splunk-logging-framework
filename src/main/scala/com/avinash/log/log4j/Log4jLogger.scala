package com.avinash.log.log4j

import com.avinash.log.LogLevel.{INFO, LogLevel}
import com.avinash.log.{CentLogger, LogMessage}
import org.slf4j.Logger

class Log4jLogger(clazz: Class[_], logLevel: LogLevel)(implicit log: Logger) extends CentLogger(logLevel) {
  override def info(msg: LogMessage): Unit = log.info(msg.toLogString)

  override def debug(msg: LogMessage): Unit = log.debug(msg.toLogString)

  override def warn(msg: LogMessage): Unit = log.warn(msg.toLogString)

  override def error(msg: LogMessage): Unit = log.error(msg.toLogString)

  override def audit(msg: LogMessage): Unit = log.info(msg.toLogString)

  override def trace(msg: LogMessage): Unit = log.trace(msg.toLogString)
}

object Log4jLogger {
  def apply(clazz: Class[_])(implicit log: Logger, logLevel: LogLevel = INFO): Log4jLogger = {
    new Log4jLogger(clazz, logLevel)
  }
}