import sbt._

object Version {
  val slf4jVersion = "2.0.4"
  val logbackVersion = "1.4.14"
  val sttp3Version = "3.9.5"
  val syncHttpVersion = "3.9.5"
  val okioJvmVersion = "3.9.0"
}

object Library {
  import Version._

  val slf4j = "org.slf4j" % "slf4j-api" % slf4jVersion
  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion
  val sttp3 = "com.softwaremill.sttp.client3" %% "core" % sttp3Version
  val sttp3Circe = "com.softwaremill.sttp.client3" %% "circe" % sttp3Version
  val sttp3SyncOkHttp = "com.softwaremill.sttp.client3" %% "okhttp-backend" % syncHttpVersion excludeAll
      (ExclusionRule(organization="com.squareup.okio"))
  val okioJvm = "com.squareup.okio" % "okio-jvm" % okioJvmVersion
}

object Dependencies {

  lazy val centSplunkLoggingDependencies: Seq[ModuleID] = Seq(
    Library.sttp3,
    Library.logback,
    Library.sttp3Circe,
    Library.sttp3SyncOkHttp,
    Library.slf4j,
    Library.okioJvm
  )
}
