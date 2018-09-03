
Build-Server-Logger
===================










This is a Java Spring Boot project.

Key Building Blocks,

* Java 8
* Spring
* Hibernate
* Gradle
* H2 Database
* Flyway
* CheckStyle
* Findbugs

Project Structure (key components):
* **src**: source folder of the project.
* **src/main/resource**:
  * **db.migration**: contains db scripts, used by Flyway.
  * **application.properties**: contains application properties for datasource and hibernate.

To build application, move to location where you wish to clone repository.
```
git clone https://github.com/Amitg1987/build-server-logger.git
cd build-server-logger
gradle clean build
```  

The java application can be started locally from your IDE, after importing it as an existing project in workspace.  
It can also be started manually with following command, from the same location, from where build was triggered as above.
```
java -jar build\libs\build-server-logger-0.0.1-SNAPSHOT.jar input.txt
```

To see content of H2 database, make sure you have a running application as done above.
As it has an embedded Tomcat running and h2 console have been enabled to view data in brower, use below information.
Please refer to application.properties for other datasource related properties.
```
* JDBC Url for DB
jdbc:h2:file:./h2/eventDB

* DB Link 
http://localhost:8080/h2-console
```
** Application developed on Windows machine, Unix/Mac not tested.

* **NOTE**: Application once started will remain in running state until interrupted, 
as EventProcessor thread keep waiting for new events in Event Queue.
