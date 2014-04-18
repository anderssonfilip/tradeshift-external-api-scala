name := "http.spray"

version := "0.1"

sbtVersion := "0.13.1"

scalaVersion := "2.10.4"

resolvers += "spray" at "http://repo.spray.io/"

libraryDependencies ++= Seq(
                            "com.typesafe.akka" %% "akka-actor" % "2.3.1",
                             "com.typesafe.akka" %% "akka-slf4j" % "2.3.1",
                             "io.spray" %%  "spray-client" % "1.3.1"
                            )