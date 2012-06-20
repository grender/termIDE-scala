import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq(
    name := "TermIDE-scala",
    version := "0.001",
    scalaVersion := "2.9.1",
    platformName in Android := "android-10"
  )

  lazy val fullAndroidSettings =
    General.settings ++
      AndroidProject.androidSettings ++
      TypedResources.settings ++
      AndroidMarketPublish.settings ++ Seq(
      keyalias in Android := "change-me"
//      libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "1.6.1" % "test",
//        "org.apache.httpcomponents" % "httpclient" % "4.1.2",
//        "org.apache.httpcomponents" % "httpmime" % "4.1.3"
//        "org.apache.james" % "apache-mime4j" % "0.7.1"
//      )

    )
}

object AndroidBuild extends Build {
  lazy val main = Project(
    "TerminalIDE-scala",
    file("."),
    //TODO: understand why not working
    settings = General.fullAndroidSettings ++ AndroidNdk.settings
    ++ Seq( 
	useProguard in Android := false,
	proguardOption in Android := "-keepclasseswithmembers class * { native <methods>; }"
    )
  )

  lazy val tests = Project(
    "tests",
    file("tests"),
    settings = General.settings ++ AndroidTest.androidSettings ++ Seq(
      name := "Winston TabletTests"
    )
  ) dependsOn main
}
