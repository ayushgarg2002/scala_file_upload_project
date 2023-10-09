ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "file-upload-akka-http"
  )


val AkkaVersion = "2.6.15"
val AkkaHttpVersion = "10.2.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "org.mockito" % "mockito-all" % "1.10.19" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % "10.2.8" % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "org.scalatestplus" %% "mockito-1-10" % "3.1.0.0" % Test,
  "org.mockito" % "mockito-all" % "1.10.19" % Test
)