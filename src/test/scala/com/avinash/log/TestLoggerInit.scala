package com.avinash.log

object TestLoggerInit {
  def main(args: Array[String]): Unit = {
    val confMap: Map[String, String] = Map[String, String](
          "splunk.logging.enabled" -> "true",
          "splunk.auth.token" -> "<HEC Token>",
          "splunk.search.source" -> "Sample",
          "splunk.search.sourceType" -> "Local_System",
          "splunk.search.index" -> "<Search_index_name>",
          "splunk.appLogging.notifyGroup" -> "central",
          "splunk.appLogging.logLevel" -> "INFO",
      "splunk.async"-> "false"
        )
    val splunkLogger: CentLogger = CentLogger(getClass, confMap)
    splunkLogger.audit("Hello splunk!")

  }
}
