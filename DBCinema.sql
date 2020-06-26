SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS theatre;
DROP TABLE IF EXISTS seat;
DROP TABLE IF EXISTS scheduling;
-- DROP TABLE IF EXISTS occupiedSeat;
-- DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS movieShowing;
DROP TABLE IF EXISTS MOVIES;

CREATE TABLE MOVIES
        (title CHAR(50) PRIMARY KEY,
         duration SMALLINT NOT NULL,
		plot VARCHAR(1000),
		pathCover VARCHAR(100),
		category CHAR(20));
	
     
CREATE TABLE theatre
        (theatreName CHAR(10),
         filePath VARCHAR(100),
         
		PRIMARY KEY (theatreName));

CREATE TABLE MOVIESHOWINGS
        (id CHAR(10) PRIMARY KEY,
		dateShow TIMESTAMP,
         theatre CHAR(10),
         price DECIMAL(4,2) NOT NULL,
         
	 FOREIGN KEY (theatre) REFERENCES theatre(theatreName));

CREATE TABLE SEATS
        ( pos CHAR(4),
          theatre CHAR(10),
          typeOfSeat CHAR(10),
          addition DECIMAL (4,2),

	  PRIMARY KEY (pos,theatre),
	  FOREIGN KEY (theatre) REFERENCES theatre(theatreName));

/*	  
CREATE TABLE AVAILABILITY
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
*/