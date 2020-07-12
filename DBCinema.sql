SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS THEATRES;
DROP TABLE IF EXISTS SEATS;
DROP TABLE IF EXISTS AVAILABILITY;
DROP TABLE IF EXISTS TICKETS;
DROP TABLE IF EXISTS MOVIESHOWINGS;
DROP TABLE IF EXISTS MOVIES;

CREATE TABLE MOVIES
        (title CHAR(50) PRIMARY KEY,
         duration SMALLINT NOT NULL,
		plot VARCHAR(1000),
		pathCover VARCHAR(100),
		category CHAR(20));
	
     
CREATE TABLE THEATRES
        (theatreName CHAR(20),
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
          theatre CHAR(20),
          typeOfSeat CHAR(10),
          addition DECIMAL (4,2),

	  PRIMARY KEY (pos,theatre));
  
CREATE TABLE AVAILABILITY
        ( showingID CHAR(4),
          pos CHAR(10),
          available BOOLEAN,

	  PRIMARY KEY (showingID, pos));


CREATE TABLE TICKETS
        (ticketCode CHAR(16) PRIMARY KEY,
         movieTitle CHAR(50) NOT NULL,
         dateShow DATETIME,
         occupiedSeat CHAR(4) NOT NULL,
		 totalPrice DOUBLE NOT NULL);

