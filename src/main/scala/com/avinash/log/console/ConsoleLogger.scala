package com.avinash.log.console

import com.avinash.log.LogLevel.{INFO, LogLevel}
import com.avinash.log.{CentLogger, LogMessage}

class ConsoleLogger(clazz: Class[_], logLevel: LogLevel) extends CentLogger(logLevel) {
  override def info(msg: LogMessage): Unit = {
    println(s"INFO: ${msg.toLogString} ($clazz)")
  }

  override def debug(msg: LogMessage): Unit = {
    println(s"DEBUG: ${msg.toLogString} ($clazz)")
  }

  override def warn(msg: LogMessage): Unit = {
    println(s"WARN: ${msg.toLogString} ($clazz)")
  }

  override def error(msg: LogMessage): Unit = {
    println(s"ERROR: ${msg.toLogString} ($clazz)")
  }

  override def audit(msg: LogMessage): Unit = {
    println(s"AUDIT: ${msg.toLogString} ($clazz)")
  }

  override def trace(msg: LogMessage): Unit = {
    println(s"TRACE: ${msg.toLogString} ($clazz)")
  }
}

object ConsoleLogger {
  def apply(clazz: Class[_])(implicit logLevel: LogLevel = INFO): ConsoleLogger = {
    new ConsoleLogger(clazz, logLevel)
  }
}