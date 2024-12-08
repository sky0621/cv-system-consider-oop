name := "cv-system-consider"
version := "0.1.0"
scalaVersion := "2.13.15"
resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.17" % Test,
  "org.typelevel" %% "cats-core" % "2.12.0",
  "org.typelevel" %% "cats-effect" % "3.5.2",
  "com.typesafe.slick" %% "slick" % "3.5.2",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.2",
  "org.xerial" % "sqlite-jdbc" % "3.47.1.0",
  "org.slf4j" % "slf4j-nop" % "2.0.16"
)
