name := "xmldiff"

version := "0.4"

organization := "in.org.patel"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)

crossScalaVersions := Seq("2.9.0", "2.9.1")

publishTo <<= (version) { version: String =>
  val keyFile = new File(Path.userHome + "/.ssh/code.patel.org.in.pem")
  Some(Resolver.ssh("patel.org.in repo", "code.patel.org.in",
                    "/usr/share/nginx/code.patel.org.in/repo-" +
                      (if  (version.trim.endsWith("SNAPSHOT")) "snapshots/" else "releases/"))
         as ("ubuntu", keyFile)
         withPermissions("0644"))
}
