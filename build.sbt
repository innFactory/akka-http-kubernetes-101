import sbt.Keys.scalaVersion

scalaVersion := "2.12.8"

lazy val k8sdemo = project.in(file("."))
  .enablePlugins(SbtReactiveAppPlugin)
  .settings(Seq(
    name := "akka-http-kubernetes-101",
    version := "1.0",
    dockerRepository := Some("eu.gcr.io"),
    packageName in Docker := "innfactory-k8s101/k8sdemo",
    dockerUpdateLatest := true
  ))


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.5",
  "io.circe" %% "circe-core" % "0.10.0",
  "io.circe" %% "circe-generic" % "0.10.0",
  "io.circe" %% "circe-parser" % "0.10.0",
  "de.heikoseeberger" %% "akka-http-circe" % "1.22.0",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test")