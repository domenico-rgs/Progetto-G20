# Progetto-G20 
Applicazione web per la prenotazione di biglietti in un cinema multisala

## Set up del sistema

### Requisiti
- [JAVA JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) 
(testato sulla versione 11)

- [JDBC](https://dev.mysql.com/downloads/connector/j/) - versione 8.0.20

- [Jetty](https://www.eclipse.org/jetty/) - versione 9.4.30.v20200611

- [Gradle](https://gradle.org) - versione 6.0

- [JavaMail](https://javaee.github.io/javamail/) - versione 1.4.7

- [iTextPDF](https://itextpdf.com/en) - versione 7.1.11

- [SLF4J API](http://www.slf4j.org/) - versione 1.7.30

- [JQuery Plugin For Gradle](https://mvnrepository.com/artifact/com.jgeppert.struts2.jquery/struts2-jquery-plugin) - versione 4.0.3

### Avvio del server
Avvio del server tramite terminale :
il numero della porta del server è configurabile tramite linea di comando(se non specificata il programma usa la porta 8080)

- spostarsi sulla cartella principale del progetto e compilare il progetto scaricando tutte le dipendenze tramite il comando:

``` $ ./gradlew clean build```

- eseguire il progetto usando la porta di default :

``` $  ./gradlew run```

- eseguire il progetto,  volendo specificare una porta  :

``` $  ./gradlew run --args='portNumber' ```

dove portNumber è il numero della porta desiderata. 

### Accedere al servizio
In generale, se addressOfMyServer è l'indirizzo del server e ServerPort è la porta di quest'ultimo dedicata al servizio, connettersi tramite l'indirizzo http://addressOfMyServer:ServerPort

- #### Utente
Supponendo che la connessione avvenga dalla stessa macchina su cui è attivo il server, connettersi al server alla pagina:
http://localhost:8080

- #### Amministratore
Supponendo che la connessione avvenga dalla stessa macchina su cui è attivo il server, connettersi al server alla pagina:
http://localhost:8080/administrator
