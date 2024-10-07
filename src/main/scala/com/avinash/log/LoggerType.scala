package com.avinash.log

trait LoggerType

case object SPLUNK extends LoggerType

case object CONSOLE extends LoggerType

case object LOG4J extends LoggerType