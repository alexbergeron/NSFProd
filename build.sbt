name := "nsfprod"

organization := "org.loutresoft"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.5"

libraryDependencies += "org.spire-math" %% "cats" % "0.1.0-SNAPSHOT"

scalacOptions ++= Seq(
  "-deprecation",           
  "-encoding", "UTF-8",       // yes, this is 2 args
  "-feature",                
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",       
  "-Xlint",
  "-Yno-adapted-args",       
  "-Ywarn-dead-code",        // N.B. doesn't work well with the ??? hole
  "-Ywarn-numeric-widen",   
  "-Xfuture",
  "-Ywarn-unused-import"     // 2.11 only
)
