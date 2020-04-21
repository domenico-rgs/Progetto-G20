drop SCHEMA IF EXISTS Gestione_Cinema;
create SCHEMA Gestione_Cinema;
use Gestione_Cinema;

drop TABLE IF EXISTS amministratore;
drop TABLE IF EXISTS cinema;
drop TABLE IF EXISTS sala;
drop TABLE IF EXISTS posto;
drop TABLE IF EXISTS postoOccupato;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS proiezione;
DROP TABLE IF EXISTS film;

CREATE TABLE amministratore
        ( cf CHAR(11) primary key);

CREATE TABLE cinema
        ( id INT PRIMARY KEY,
          nome CHAR(10) NOT NULL,
          cf_amministratore CHAR(11),

          FOREIGN KEY (cf_amministratore) REFERENCES amministratore(cf));

CREATE TABLE proiezione
        (dataProiezione TIMESTAMP,
         nomeSala CHAR(10),
         idCinema INT,
         prezzo_base DECIMAL(4,2) NOT NULL,
	 film CHAR(10) REFERENCES film(titolo),

	 PRIMARY KEY (dataProiezione,nomeSala,idCinema));

CREATE TABLE sala
        ( nome CHAR(10) PRIMARY KEY,
          idCinema INT,
          n_righe SMALLINT,
	  n_colonne SMALLINT,

          FOREIGN KEY (idCinema) REFERENCES cinema(id));

CREATE TABLE posto
        ( num CHAR(4),
          sala CHAR(10),
	  idCinema INT,
         
	  PRIMARY KEY (num,sala,idCinema),
	  FOREIGN KEY (sala) REFERENCES sala(nome),
          FOREIGN KEY (idCinema) REFERENCES cinema(id));

CREATE TABLE postoOccupato
        ( posto CHAR(4),
          dataProiezione TIMESTAMP,
	  nomeSala CHAR(10),
          idCinema INT,

	  PRIMARY KEY (posto,dataProiezione,nomeSala,idCinema),
	  FOREIGN KEY (dataProiezione, nomeSala, idCinema) REFERENCES proiezione(dataProiezione, nomeSala, idCinema));

CREATE TABLE ticket
        (codice CHAR(4) PRIMARY KEY,
         prezzo_finale DECIMAL (4,2) NOT NULL,
         posto_occupato CHAR(4) NOT NULL,
         proiezione INTEGER,
	   dataProiezione TIMESTAMP,
	 nomeSala CHAR(10),
         idCinema INT,

         FOREIGN KEY (dataProiezione, nomeSala, idCinema) REFERENCES proiezione(dataProiezione, nomeSala, idCinema));

CREATE TABLE film
        (titolo CHAR(10) PRIMARY KEY,
         durata SMALLINT NOT NULL,
         genere CHAR(10) ,
         descrizione VARCHAR(1000));

