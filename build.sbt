name := "old48_32_game"

version := "1.0"

scalaVersion := "2.11.6"

fork in Compile := true

unmanagedResourceDirectories in Compile += file("assets")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" % "scala-swing_2.11" % "1.0.1",
  "com.badlogicgames.gdx" % "gdx" % "1.4.1",
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.4.1",
  "com.badlogicgames.gdx" % "gdx-platform" % "1.4.1" classifier "natives-desktop",
  "com.badlogicgames.gdx" % "gdx-freetype" % "1.4.1",
  "com.badlogicgames.gdx" % "gdx-freetype-platform" % "1.4.1" classifier "natives-desktop"
)