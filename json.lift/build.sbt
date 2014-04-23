name := "json.lift"

version := "0.1"

sbtVersion := "0.13.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
                            "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
                            "net.liftweb" %% "lift-json" % "2.5.1" % "provided",
                            "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"
                            )