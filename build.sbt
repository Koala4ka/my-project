name := "MyProject"

version := "1.0"
lazy val flywaymod = RootProject(file("flyway-mod"))

lazy val `myproject` = (project in file("."))
  .dependsOn(flywaymod)
  .aggregate(flywaymod)
  .enablePlugins(
    SwaggerPlugin,
    FlywayPlugin,
      PlayScala
  )

swaggerDomainNameSpaces := Seq("models")

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.6"
val slickVersion = "3.3.3"

resolvers += "jitpack" at "https://jitpack.io"
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(guice,
  "io.monix" %% "monix" % "3.3.0",
  "org.webjars" % "swagger-ui" % "3.45.0",
  "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "org.scalamock" %% "scalamock" % "5.1.0" % Test,
  "org.postgresql" % "postgresql" % "42.2.19",
  "com.auth0" % "java-jwt" % "3.15.0",

  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,

  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",

  "org.flywaydb" % "flyway-core" % "7.7.0",

  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
)
libraryDependencies += "com.pauldijou" %% "jwt-core" % "5.0.0"

sourceGenerators in Compile += slick.taskValue

flywayClean := {
  (flywayClean in flywaymod).value
}

flywayMigrate := {
  (flywayMigrate in flywaymod).value
}

flywayInfo := {
  (flywayInfo in flywaymod).value
}


lazy val slick = taskKey[Seq[File]]("Generate Tables.scala")
slick := {
  val dir = (sourceManaged in Compile) value
  val outputDir = dir / "slick"
  val url = "jdbc:postgresql://localhost:5432/user1"
  val jdbcDriver = "org.postgresql.Driver"
  val slickDriver = "slick.jdbc.PostgresProfile"
  val user = "user1"
  val password = "dm"
  val pkg = "demo"

  val cp = (dependencyClasspath in Compile) value
  val s = streams value

  (runner in Compile).value.run("slick.codegen.SourceCodeGenerator",
    cp.files,
    Array(slickDriver, jdbcDriver, url, outputDir.getPath, pkg, user, password),
    s.log).failed foreach (sys error _.getMessage)

  val file = outputDir / pkg / "Tables.scala"

  Seq(file)
}

flywayClean := {
  (flywayClean in flywaymod).value
}

flywayMigrate := {
  (flywayMigrate in flywaymod).value
}

flywayInfo := {
  (flywayInfo in flywaymod).value
}


