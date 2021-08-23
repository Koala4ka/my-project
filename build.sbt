
name := "MyProject"

version := "1.0"

lazy val `myproject` = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

swaggerDomainNameSpaces := Seq("models")

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.6"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq( guice,
  "io.monix" %% "monix" % "3.3.0",
  "org.webjars" % "swagger-ui" % "3.45.0",
  "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "org.scalamock" %% "scalamock" % "5.1.0" % Test,
  "org.postgresql" % "postgresql" % "42.2.19",

  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",

  "org.flywaydb" % "flyway-core" % "7.7.0",
  "org.flywaydb" %% "flyway-play" % "7.7.0",

  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
)






