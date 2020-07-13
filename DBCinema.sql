SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS THEATRES;
DROP TABLE IF EXISTS SEATS;
DROP TABLE IF EXISTS AVAILABILITY;
DROP TABLE IF EXISTS TICKETS;
DROP TABLE IF EXISTS MOVIESHOWINGS;
DROP TABLE IF EXISTS MOVIES;
DROP TABLE IF EXISTS DISCOUNTS;

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
         price DECIMAL(4,2) NOT NULL,
         
		FOREIGN KEY (movieTitle) REFERENCES MOVIES(title),
		FOREIGN KEY (theatre) REFERENCES THEATRES(theatreName));

         
CREATE TABLE SEATS
        ( pos CHAR(4),
          theatre CHAR(20),
          typeOfSeat CHAR(10),
          addition DECIMAL (4,2),

	  PRIMARY KEY (pos,theatre),
	FOREIGN KEY (theatre) REFERENCES THEATRES(theatreName));

CREATE TABLE AVAILABILITY
        ( showingID CHAR(10),
          pos CHAR(4),
			typeOfSeat CHAR(10),
          available BOOLEAN,

	PRIMARY KEY (showingID, pos),
	FOREIGN KEY (pos) REFERENCES SEATS(pos),
	FOREIGN KEY (showingID) REFERENCES MOVIESHOWINGS(id));



CREATE TABLE TICKETS
        (ticketCode CHAR(16) PRIMARY KEY,
         movieTitle CHAR(50) NOT NULL,
         dateShow DATETIME,
         occupiedSeat CHAR(4) NOT NULL,
		 totalPrice DOUBLE NOT NULL,
         
	FOREIGN KEY (movieTitle) REFERENCES MOVIES(title),
	FOREIGN KEY (occupiedSeat) REFERENCES SEATS(pos));
    
    CREATE TABLE DISCOUNTS
        (discountCode CHAR(16) PRIMARY KEY,
         percent DOUBLE NOT NULL);

