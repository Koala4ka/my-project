scalaVersion := "2.13.5"
enablePlugins(FlywayPlugin)

libraryDependencies ++= Seq(
  "org.flywaydb" % "flyway-core" % "7.7.0",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc41"
)


flywayUrl := "jdbc:postgresql://localhost:5432/user1"
flywayUser := "user1"
flywayPassword := "dm"
flywayLocations := Seq("classpath:migration")