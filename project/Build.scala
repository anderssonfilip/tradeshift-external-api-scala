import sbt._
import Keys._

object ClientBuild extends Build {

  scalaVersion in ThisBuild := "2.10.4"

  lazy val client = project.in(file("."))
    .aggregate(http, json)
    .dependsOn(http)
    .dependsOn(json)

  lazy val http = project.in(
    file("http.dispatch")
    //file("http.spray")
  )

  lazy val json = project.in(
    file("json.lift")
    //file("json.spray")
  )
}