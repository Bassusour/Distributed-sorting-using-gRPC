name := "Distributed sorting"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies ++= Seq(
    "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
    // "org.scalactic" %% "scalactic" % "3.2.10",
    // "org.scalatest" %% "scalatest" % "3.2.10" % "test",
    "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)