# CS 1699 - Object Oriented Design
 
## Final Project

### Authors

* [Adam Richman](https://github.com/adamrichman1)
* [Josh Gulden](https://github.com/jdg78)

### Requirements
* [Java 1.8.0+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.3.9+](https://maven.apache.org/install.html)
* [PostgreSQL 10.6+](https://www.postgresql.org/download/)

#### Setup PostgreSQL
* [Install Postgres App](https://postgresapp.com)
* Open GUI
* Click Initialize
* Alternatively, setup local database instance with the following credentials: (port=5432, dbName=postgres)

#### Build
```
cd BarkPark
mvn package
```

#### Run
```
cd BarkPark
java -jar target/BarkPark-1.0.jar
```

#### Build & Run
```
cd BarkPark
bash run.sh
```

#### Access
```
http://localhost:8080/
```
