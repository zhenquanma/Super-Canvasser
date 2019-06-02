# Super Canvasser Server

This server was construct based on [Spring Boot](https://spring.io/projects/spring-boot) version 1.5.16.RELEASE with using [Maven](https://maven.apache.org/) as dependency management and [MySQL](https://www.mysql.com/) as database. 

## Prerequisites
* [Jave jdk8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* IDE([Intellij](https://www.jetbrains.com/idea/) recommended)
* [MySQL](https://www.mysql.com/)
* [Maven](https://maven.apache.org/)(Optional)

## Running the server
You can either run the server from an IDE or Maven command as below

```
cd Super-Canvasser/Super_Canvasser BackEnd
mvn spring-boot:run
```

## Deploying the database
The application use MySQL as databse. If you do not have install MySQL, you can download from [here](https://www.mysql.com/).
Once you've done with the installation, you can find a SQL script under the path: 
```
Super_Canvasser BackEnd/src/main/resources/sql/SQLscript.sql
```
Run the script in the terminal of MySQL client, and set the root account password to 123456(or go to the application.yml file to modify passsword within it).
