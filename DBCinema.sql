drop SCHEMA IF EXISTS cinemaManagement;
create SCHEMA cinemaManagement;
use cinemaManagement;

drop TABLE IF EXISTS theatre;
drop TABLE IF EXISTS seat;
drop TABLE IF EXISTS occupiedSeat;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS movieShowing;
DROP TABLE IF EXISTS movie;
drop TABLE IF EXISTS typeOfSeat;

CREATE TABLE movie
        (title CHAR(10) PRIMARY KEY,
         duration SMALLINT NOT NULL,
         category CHAR(10) ,
         plot VARCHAR(1000),
         pathCover VARCHAR(100));
	
     
CREATE TABLE theatre
        (theatreName CHAR(10) PRIMARY KEY,
         filePath VARCHAR(100));

CREATE TABLE movieShowing
        (dateMovieShowing TIMESTAMP,
         theatre CHAR(10),
         price DECIMAL(4,2) NOT NULL,
		 movieTitle CHAR(10) NOT NULL,
         id CHAR(10) NOT NULL UNIQUE,
         
	

	 PRIMARY KEY (dateMovieShowing, theatre),
	 FOREIGN KEY (theatre) REFERENCES theatre(theatreName),
	 FOREIGN KEY (movieTitle) REFERENCES movie(title));
      
CREATE TABLE typeOfSeat
		(typeName CHAR(10) PRIMARY KEY,
         addition DECIMAL (4,2));

CREATE TABLE seat
        ( pos CHAR(4),
          theatre CHAR(10),
          typeOfSeat CHAR(10),
         
	  PRIMARY KEY (pos,theatre),
	  FOREIGN KEY (theatre) REFERENCES theatre(theatreName), 
      FOREIGN KEY (typeOfSeat) REFERENCES typeOfSeat(typeName));

CREATE TABLE occupiedSeat
        ( pos CHAR(4),
          theatre CHAR(10),
          dateMovieShowing TIMESTAMP,

	  PRIMARY KEY (pos, dateMovieShowing, theatre),
	  FOREIGN KEY (pos, theatre) REFERENCES seat(pos, theatre),
      FOREIGN KEY(dateMovieShowing) REFERENCES movieShowing(dateMovieShowing));


CREATE TABLE ticket
        (ticketCode CHAR(4) PRIMARY KEY,
         totalPrice DECIMAL (4,2) NOT NULL,
         occupiedSeat CHAR(4) NOT NULL,
		 theatre CHAR(10),

         FOREIGN KEY (occupiedSeat, theatre) REFERENCES occupiedSeat(pos, theatre));