$ git clone <this repository>
$ cd AutoAxeCreator
# Download jar files of tomcat-embed.
$ ls lib/
tomcat-embed-core-9.0.0.M6.jar		tomcat-embed-logging-juli-9.0.0.M6.jar
$ scalac -d target/ -cp lib/tomcat-embed-core-9.0.0.M6.jar:lib/tomcat-embed-logging-juli-9.0.0.M6.jar src/main/AutoAxeCreator.scala 
$ scala -cp target:lib/tomcat-embed-core-9.0.0.M6.jar:lib/tomcat-embed-logging-juli-9.0.0.M6.jar src/main/AutoAxeCreator.scala  

# Access http://localhost:8080/app/hello by browser.
