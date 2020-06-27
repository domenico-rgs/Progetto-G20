SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS THEATRE;
DROP TABLE IF EXISTS SEATS;
DROP TABLE IF EXISTS AVAILABILITY;
-- DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS MOVIESHOWINGS;
DROP TABLE IF EXISTS MOVIES;

CREATE TABLE MOVIES
        (title CHAR(50) PRIMARY KEY,
         duration SMALLINT NOT NULL,
		plot VARCHAR(1000),
		pathCover VARCHAR(100),
		category CHAR(20));
	
     
CREATE TABLE THEATRE
        (theatreName CHAR(10),
         filePath VARCHAR(100),
         
		PRIMARY KEY (theatreName));

CREATE TABLE MOVIESHOWINGS
        (id CHAR(10) PRIMARY KEY,
        movieTitle CHAR(50) NOT NULL,
		dateShow DATETIME,
         theatre CHAR(10),
         price DECIMAL(4,2) NOT NULL);
         
CREATE TABLE SEATS
        ( pos CHAR(4),
          theatre CHAR(10),
          typeOfSeat CHAR(10),
          addition DECIMAL (4,2),

	  PRIMARY KEY (pos,theatre));
  
CREATE TABLE AVAILABILITY
        ( showingID CHAR(4),
          pos CHAR(10),
          available BOOLEAN,

	  PRIMARY KEY (showingID, pos));

/*
CREATE TABLE ticket
        (ticketCode CHAR(4) PRIMARY KEY,
         totalPrice DECIMAL (4,2) NOT NULL,
         occupiedSeat CHAR(4) NOT NULL,
		 theatre CHAR(10),

         FOREIGN KEY (occupiedSeat, theatre) REFERENCES occupiedSeat(pos, theatre));
*/