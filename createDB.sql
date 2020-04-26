drop SCHEMA IF EXISTS cinemaManagement;
create SCHEMA cinemaManagement;
use cinemaManagement;

drop TABLE IF EXISTS theatre;
drop TABLE IF EXISTS seat;
drop TABLE IF EXISTS occupiedSeat;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS movieShowing;
DROP TABLE IF EXISTS movie;

CREATE TABLE movie
        (title CHAR(10) PRIMARY KEY,
         duration SMALLINT NOT NULL,
         genere CHAR(10) ,
         description VARCHAR(1000),
	 pathMovieCover VARCHAR(100));
     
CREATE TABLE theatre
        ( theatreName CHAR(10) PRIMARY KEY,
          nRow SMALLINT,
	  nCol SMALLINT);

CREATE TABLE movieShowing
        (dateMovieShowing TIMESTAMP,
         theatre CHAR(10),
         price DECIMAL(4,2) NOT NULL,
	 movie CHAR(10) REFERENCES movie(title),

	 PRIMARY KEY (dateMovieShowing, theatre),
	 FOREIGN KEY (theatre) REFERENCES theatre(theatreName),
	 FOREIGN KEY (movie) REFERENCES movie(title));

CREATE TABLE seat
        ( pos CHAR(4),
          theatre CHAR(10),
         
	  PRIMARY KEY (pos,theatre),
	  FOREIGN KEY (theatre) REFERENCES theatre(theatreName));

CREATE TABLE occupiedSeat
        ( pos CHAR(4),
          dateMovieShowing TIMESTAMP,
          theatre CHAR(10),

	  PRIMARY KEY (pos, dateMovieShowing, theatre),
	  FOREIGN KEY (pos, theatre) REFERENCES seat(pos, theatre),
	  FOREIGN KEY (dateMovieShowing) REFERENCES movieShowing(dateMovieShowing));

CREATE TABLE ticket
        (ticketCode CHAR(4) PRIMARY KEY,
         finalPrice DECIMAL (4,2) NOT NULL,
         occupiedSeat CHAR(4) NOT NULL,
	 dateMovieShowing TIMESTAMP,
	 theatre CHAR(10),

         FOREIGN KEY (occupiedSeat, theatre, dateMovieShowing) REFERENCES occupiedSeat(pos, theatre, dateMovieShowing));

