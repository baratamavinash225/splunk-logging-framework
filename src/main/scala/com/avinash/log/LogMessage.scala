package com.avinash.log

import scala.collection.mutable

protected[log] case class LogMessage(msg: String, notifyGroup: String = noGroup, additionalInfo: mutable.Map[String, String] = emptyMap) {
  def toLogString = s"$msg \n notifyGroup: $notifyGroup \n Additional Info: $additionalInfo"
}

protected[log] object LogMessage {
  def build(msg: String): LogMessage =
    LogMessage(msg)

  def build(msg: String, notifyGroup: String): LogMessage =
    LogMessage(msg, notifyGroup)

  def build(msg: String, additionalInfo: mutable.Map[String, String]): LogMessage =
    LogMessage(msg, noGroup, additionalInfo)

  def build(msg: String, throwable: Throwable): LogMessage = {
    val additionalInfo = emptyMap
    additionalInfo += ("Exception" -> exceptionAsString(throwable))
    build(msg, additionalInfo)
  }

  def build(msg: String, notifyGroup: String, throwable: Throwable): LogMessage = {
    val additionalInfo = emptyMap
    additionalInfo += ("Exception" -> exceptionAsString(throwable))
    LogMessage(msg, notifyGroup, additionalInfo)
  }
}