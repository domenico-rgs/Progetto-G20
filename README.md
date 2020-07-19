# Project-G20
Web application for booking tickets in a multiplex cinema

## System set up
### Set up database
To start the program you must first of all have the database available and functional.
 To avoid having to modify parameters within the code, create the db via MySQL Workbench with the same parameters
 present within the project's "IMapper" interface. Then create a new scheme and set it as the default scheme.
 To create the tables run the MySQL script contained in the "DBCinema.sql" file.
 
### Requirements
- [JAVA JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
(tested on version 11)

- [JDBC](https://dev.mysql.com/downloads/connector/j/) - version 8.0.20

- [Jetty](https://www.eclipse.org/jetty/) - version 9.4.30.v20200611

- [Gradle](https://gradle.org) - version 6.0

- [JavaMail](https://javaee.github.io/javamail/) - version 1.4.7

- [iTextPDF](https://itextpdf.com/en) - version 7.1.11

- [SLF4J API](http://www.slf4j.org/) - version 1.7.30

- [JQuery Plugin For Gradle](https://mvnrepository.com/artifact/com.jgeppert.struts2.jquery/struts2-jquery-plugin) - version 4.0.3
 
### Starting the server
Starting the server via terminal:
the server port number can be configured via the command line (if not specified, the program uses port 8080)

- move to the main folder of the project and compile the project by downloading all the dependencies using the command:

``` $ ./gradlew clean build```

- run the project using the default port:

``` $  ./gradlew run```

- to execute the project by specifying a different port:

``` $  ./gradlew run --args='portNumber' ```

where portNumber is the desired port number.

### Log in to the service
In general, if addressOfMyServer is the address of the server and ServerPort is the port of the latter dedicated to the service, connect via the address http: // addressOfMyServer: ServerPort

- #### User
Assuming that the connection is made from the same machine on which the server is active, connect to the server on the page:
http: // localhost: 8080

- #### Administrator
Assuming that the connection is made from the same machine on which the server is active, connect to the server on the page:
http: // localhost: 8080 / administrator
<br> The default password is * password *
