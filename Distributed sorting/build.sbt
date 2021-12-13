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

// lazy val master = (project in file(".")).
//   settings(
//     name := "master",
//     assembly / mainClass := Some("distrSortTest.Master"),
//     assembly / assemblyJarName := "master",
//     /* Netty deduplicate error -  https://github.com/sbt/sbt-assembly/issues/362 */
//     assembly / assemblyMergeStrategy := {
//       case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
//       case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.first
//       case x => MergeStrategy.first
//     }
//   )

lazy val worker = (project in file(".")).
  settings(
    name := "worker",
    assembly / mainClass := Some("distrSortTest.Worker"),
    assembly / assemblyJarName := "worker",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
      case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.first
      case x => MergeStrategy.first
    }
  )

// mainClass in assembly := Some("com.distrSortTest.Master")

// assemblyMergeStrategy in assembly := {
//  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
//  case x => MergeStrategy.first
// }

