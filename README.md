Datomic Browser
===============

Create and explore Datomic in-memory databases.

Prerequisites
-------------

Before building Datomic Browser, install Datomic into your local Maven repo. In your Datomic 0.1.2753 install directory, do:

    mvn install:install-file -DgroupId=com.datomic -DartifactId=datomic -Dfile=datomic-0.1.2753.jar -DpomFile=pom.xml

Build
-----

To build the executable war, execute:

    mvn clean package -P executable-war

Run
---

To run the war:

    java -jar datomic-browser.war

To run the war on a different port than 8080, use:

    java -Dport=9090 -jar datomic-browser.war

To run with JVM args:

    java -Xms5g -Xmx7g -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -jar datomic-browser.war
