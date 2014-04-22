name := "http.dispatch"

version := "0.1"

sbtVersion := "0.13.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
                            "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
                            "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test",
                            "com.typesafe" % "config" % "1.2.0" % "test"
                           )