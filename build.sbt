name := """spgroup"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "commons-beanutils" % "commons-beanutils" % "1.9.3",
  "org.mockito" % "mockito-core" % "2.10.0" % "test"
)
