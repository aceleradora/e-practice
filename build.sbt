name := "e-Practice"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)

play.Project.playJavaSettings
