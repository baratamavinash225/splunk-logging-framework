import Settings._

val root = Project("splunk-logging-framework", file("."))
  .settings(Defaults.itSettings: _*)
  .settings(commonSettings: _*)
  .settings(rootSettings: _*)
  .settings(dependencies: _*)
  .settings(assemblySettings: _*)
  .settings(publishSettings:_*)