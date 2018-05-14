name := """zettel"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)


scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  filters,
  guice,
  "org.eclipse.rdf4j" % "rdf4j-runtime" % "2.0.1",
  "com.github.jsonld-java" % "jsonld-java" % "0.8.3",
  "com.github.hbz" %"lobid-rdf-to-json" % "8dcd67c7aa7f4fb7b53e875ea0561a4fbb951f89",
  "commons-validator" % "commons-validator" % "1.5.1"
)

resolvers += "jitpack" at "https://jitpack.io"

EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes