package com.avinash.log

object LogLevel extends Enumeration {
  type LogLevel = Value
  val WARN: LogLevel.Value = Value("WARN")
  val INFO: LogLevel.Value = Value("INFO")
  val DEBUG: LogLevel.Value = Value("DEBUG")
  val TRACE: LogLevel.Value = Value("TRACE")
  val AUDIT: LogLevel.Value = Value("AUDIT")
  val ERROR: LogLevel.Value = Value("ERROR")

  def unApply(x: String): Value = {
    x match {
      case "WARN" => WARN
      case "INFO" => INFO
      case "DEBUG" => DEBUG
      case "AUDIT" => AUDIT
      case "ERROR" => ERROR
      case "TRACE" => TRACE
    }
  }
}
