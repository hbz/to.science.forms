name := """zettel"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters,
  "org.openrdf.sesame" % "sesame-runtime" % "2.7.9",
  "com.github.jsonld-java"%"jsonld-java-sesame"%"0.3",
  "com.github.hbz" %"lobid-rdf-to-json" % "d7c013261299658be5ad5016dedb20af8927e698"
)
resolvers += "jitpack" at "https://jitpack.io"

EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes