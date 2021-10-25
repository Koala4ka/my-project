scalaVersion := "2.13.5"
enablePlugins(FlywayPlugin)

val slickVersion = "3.3.3"

libraryDependencies ++= Seq(
  "org.flywaydb" % "flyway-core" % "7.7.0",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
  "com.github.tminglei" %% "slick-pg" % "0.19.7",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.19.7",
  "com.typesafe.play" %% "play-json" % "2.7.4",
"com.typesafe.slick" %% "slick-codegen" % slickVersion,
)


flywayUrl := "jdbc:postgresql://localhost:5432/user1"
flywayUser := "user1"
flywayPassword := "dm"
flywayLocations := Seq("classpath:migration")