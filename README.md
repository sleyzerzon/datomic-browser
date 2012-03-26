Datomic Browswer
================

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
