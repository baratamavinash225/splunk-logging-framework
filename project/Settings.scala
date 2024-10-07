import Dependencies._
import sbt.Keys.{publishConfiguration, _}
import sbt.{Def, _}
import sbtassembly.AssemblyPlugin.autoImport._

object Settings {
  type Setting = Seq[Def.SettingsDefinition]

  lazy val confluentRepo = "Confluent" at "https://packages.confluent.io/maven"

  lazy val commonSettings: Setting = Seq(
    organization := "com.avinash",
    scalaVersion := "2.12.13",
    crossScalaVersions := Seq("2.12.13", "2.13.4"),
    allowZombieClassLoaders := false,
    version := "0.0.1"
  )

  //Added in case if we're to overwrite
  lazy val publishSettings: Setting = Seq (
    publishConfiguration := publishConfiguration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
  )

  val rootSettings: Setting = Seq(
    concurrentRestrictions in Global += Tags.limit(Tags.Test, 1),
    resolvers ++= Seq(confluentRepo)
  )

  lazy val dependencies: Setting = Seq(
    libraryDependencies ++= centSplunkLoggingDependencies,
    testFrameworks += new TestFramework("munit.Framework")
  )

  lazy val assemblySettings: Setting = Seq(
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs@_*) => MergeStrategy.discard
      case x => MergeStrategy.first
    },
    assembly / assemblyJarName := s"${name.value}-${version.value}.jar",
    Compile / assembly / artifact := {
      val art = (Compile / assembly / artifact).value
      art.withClassifier(Some("assembly"))
    },
    addArtifact(Compile / assembly / artifact, assembly)
  )
}
