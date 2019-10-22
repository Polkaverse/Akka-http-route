name := "Akka-Http-Crud"

version := "1.0"

scalaVersion := "2.12.1"
mainClass in Compile:= Some("RestServer")
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.1"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.11" // or whatever the latest version is
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.24"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.5"
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-SNAP9" % Test
libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % Test
