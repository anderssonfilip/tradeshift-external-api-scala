import sbt._
import Keys._

object ClientBuild extends Build {

  scalaVersion in ThisBuild := "2.10.4"

  val typesafeConfig = "com.typesafe" % "config" % "1.2.0"
  val test =  "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

  lazy val client = project.in(file("."))
    .aggregate(http, json, container)
    .settings(libraryDependencies ++= Seq(typesafeConfig, test))
    .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
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