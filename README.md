# Splunk Logging Framework

This is the common utility for the different types of loggers that you would want to have it in your application. 
This integrates with the below loggers and bring it as a single dependency of use.

* **Console Logger** - To print on the Console
* **Splunk Logger** - To log to the Splunk Index using Token
* **Log4j Logger** - To have the log4j Logging

## Dependency
You could add this package as a dependency in any of your scala / java projects to make use of the logger functionality.

## Splunk Logging Configuration
To have the Splunk Logging enabled, below configuration map has to be passed into the Logger to initiate the Splunk Logger Object
```scala
val confMap: Map[String, String] = Map[String, String](
  "splunk.logging.enabled" -> "true",
  "splunk.auth.token" -> "<HEC Token>",
  "splunk.search.source" -> "Sample",
  "splunk.search.sourceType" -> "Local_System",
  "splunk.search.index" -> "<Search_index_name>",
  "splunk.appLogging.notifyGroup" -> "central",
  "splunk.appLogging.logLevel" -> "INFO",
  "splunk.async"-> "<false/true>"
)
val splunkLogger: CentLogger = CentLogger(getClass, confMap)
splunkLogger.audit("Hello splunk!")
```