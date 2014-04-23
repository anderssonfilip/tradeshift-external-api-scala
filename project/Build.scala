import com.github.retronym.SbtOneJar
import sbt._
import Keys._

object ClientBuild extends Build {

  scalaVersion in ThisBuild := "2.10.4"

  def standardSettings = Seq(
    exportJars := true
  ) ++ Defaults.defaultSettings

  val typesafeConfig = "com.typesafe" % "config" % "1.2.0" % "provided"

  lazy val client = Project("client",
    file("."),
    settings = standardSettings ++ SbtOneJar.oneJarSettings
  )
    .aggregate(http, json, container)
    .settings(libraryDependencies ++= Seq(typesafeConfig))
    .dependsOn(http)
    .dependsOn(json)
    .dependsOn(container)

  lazy val container = project.in(
    file("container"))

  lazy val http = project.in(
    file("http.dispatch")
    //file("http.spray")
    )
    .dependsOn("container")

  lazy val json = project.in(
    file("json.lift")
    //file("json.spray")
    )
    .dependsOn("container")
}