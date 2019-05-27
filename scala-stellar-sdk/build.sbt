scalaVersion := "2.12.8"
scalacOptions ++= Seq(
    "-Yrangepos",
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:postfixOps",
    "-encoding",
    "UTF-8",
    "-target:jvm-1.8")
fork := true
libraryDependencies ++= List(
  "io.cucumber" % "cucumber-core" % "4.3.1" % Test,
  "io.cucumber" % "cucumber-junit" % "4.3.1" % Test,
  "io.cucumber" %% "cucumber-scala" % "4.3.1" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test,

  "io.github.synesso" %% "scala-stellar-sdk" % "0.7.0" % Test
)

