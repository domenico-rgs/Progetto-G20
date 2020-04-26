drop SCHEMA IF EXISTS cinemaManagement;
create SCHEMA cinemaManagement;
use cinemaManagement;

drop TABLE IF EXISTS theatre;
drop TABLE IF EXISTS seat;
drop TABLE IF EXISTS occupiedSeat;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS movieShowing;
DROP TABLE IF EXISTS movie;


CREATE TABLE movieShowing
        (dateMovieShowing TIMESTAMP,
         theatre CHAR(10),
         price DECIMAL(4,2) NOT NULL,
	 movie CHAR(10) REFERENCES movie(title),

	 PRIMARY KEY (dateMovieShowing, theatre),
	 FOREIGN KEY (theatre) REFERENCES theatre(name),
	 FOREIGN KEY (title) REFERENCES movie(title));

CREATE TABLE theatre
        ( name CHAR(10) PRIMARY KEY,
          nRow SMALLINT,
	  nCol SMALLINT);

CREATE TABLE seat
        ( position CHAR(4),
          theatre CHAR(10),
         
	  PRIMARY KEY (position,theatre),
	  FOREIGN KEY (theatre) REFERENCES theatre(name));

CREATE TABLE occupiedSeat
        ( position CHAR(4),
          dateMovieShowing TIMESTAMP,
          theatre CHAR(10),

	  PRIMARY KEY (position, dateMovieShowing),
	  FOREIGN KEY (position) REFERENCES seat(seat),
	  FOREIGN KEY (theatre) REFERENCES theatre(name),
	  FOREIGN KEY (dateMovieShowing) REFERENCES movieShowing(dateMovieShowing));

CREATE TABLE ticket
        (code CHAR(4) PRIMARY KEY,
         finalPrice DECIMAL (4,2) NOT NULL,
         occupiedSeat CHAR(4) NOT NULL,
	 dateMovieShowing TIMESTAMP,
	 theatre CHAR(10),

         FOREIGN KEY (dateMovieShowing) REFERENCES occupiedSeat(dateMovieShowing),
	 FOREIGN KEY (theatre) REFERENCES theatre(name),
         FOREIGN KEY (occupiedSeat) REFERENCES occupiedSeat(position));

CREATE TABLE movie
        (title CHAR(10) PRIMARY KEY,
         duration SMALLINT NOT NULL,
         genere CHAR(10) ,
         description VARCHAR(1000)
	 pathMovieCover VARCHAR(100));

