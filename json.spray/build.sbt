name := "json.spray"

version := "0.1"

sbtVersion := "0.13.1"

scalaVersion := "2.10.4"

resolvers += "spray" at "http://repo.spray.io/"

libraryDependencies ++= Seq(
                            "io.spray" %% "spray-json" % "1.2.6",
                            "com.typesafe.akka" %% "akka-actor" % "2.3.1"
                            )