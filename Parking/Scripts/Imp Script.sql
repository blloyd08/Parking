DROP DATABASE PARKING

CREATE DATABASE PARKING;
USE PARKING;

CREATE TABLE ParkingLot(
	lotName VARCHAR(100) PRIMARY KEY,
	location VARCHAR(100) NOT NULL,
	capacity INT NOT NULL,
	floors FLOAT
);

CREATE TABLE SpaceType (
	`name` VARCHAR(15) PRIMARY KEY
);

CREATE TABLE ParkingSpace (
	spaceID INT AUTO_INCREMENT PRIMARY KEY,
	lotName VARCHAR(100) NOT NULL REFERENCES ParkingLot(lotName),
	spaceType VARCHAR(15)  NOT NULL REFERENCES SpaceType(`name`)
);

CREATE TABLE Staff (
	staffID INT AUTO_INCREMENT PRIMARY KEY,
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	telephone CHAR(13) NOT NULL,
	extention VARCHAR(6),
	licenseNumber VARCHAR(8)
);

CREATE TABLE StaffReservation (
	spaceID INT REFERENCES ParkingSpace(spaceID),
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	staffID INT NOT NULL REFERENCES Staff(staffID),
	rate DECIMAL(5,2) NOT NULL,
	PRIMARY KEY (spaceID,startDate)
);
CREATE TABLE VisitorReservation (
	spaceID INT NOT NULL,
	reservedDay DATE NOT NULL,
	staffID INT  NOT NULL REFERENCES Staff(staffID),
	licenseNumber VARCHAR(8) NOT NULL,
	PRIMARY KEY (spaceID, reservedDay)
);

-- Views -----------------------------------------------------------------------------------------------------------------
-- Current Reservations
CREATE VIEW  CurrentStaffReservation AS (
SELECT *
FROM StaffReservation
WHERE endDate > CURDATE());

-- Staff Reservation --> Staff Drop Down
-- Staff with no current reservations
CREATE VIEW StaffWithoutReservation AS (
SELECT *
FROM Staff S
WHERE S.staffID NOT IN (SELECT staffID FROM CurrentStaffReservation));


SELECT *
FROM CurrentStaffReservation
-- Parking Space -> Lot Drop Down
-- Parking Lots that aren't at capacity (Can create more spaces)
CREATE VIEW UnfilledLots AS (
SELECT L.lotName
FROM ParkingLot L
LEFT JOIN LotCounts S
ON L.lotName = S.lotName
WHERE capacity > Spaces OR Spaces IS NULL);

-- Visitor Spots (Not checking for reservations)
CREATE VIEW VisitorSpaces AS (
SELECT spaceID
FROM ParkingSpace
WHERE spaceType = 'Visitor');

-- Current number of spaces created for a parking lot
CREATE VIEW LotCounts AS (
SELECT lotName, COUNT(spaceID) 'Spaces'
FROM ParkingSpace);

-- INSERT STATEMENTS ----------------------------------------------------------------------------------------------------------------
/*
DEPENDENCE:
ParkingLot -> ParkingSpace
SpaceType -> ParkingSpace

Staff -> VisitorReservation
ParkingSpace -> Visitor Reservation

Staff -> StaffReservation
ParkingSpace -> StaffReservation
*/

INSERT INTO SpaceType VALUES
("Covered"),
("Free"),
("Visitor");

-- Without Floors
INSERT INTO ParkingLot(lotName, location, capacity) VALUES
("A", "North Building 3", 40),
("D","North Building 1", 45);

-- With Floors
INSERT INTO ParkingLot VALUES("B", "East Building 3", 80, 2),
("C", "North Building 2", 100, 2);

INSERT INTO Staff (firstName, lastName, telephone, extention, licenseNumber) VALUES
('Barry', 'White', '(999)999-9999','1234','abc-134'),
('Ron', 'Burgendy', '(999)999-9998', '1235', 'as2-as3'),
('Tim', 'Burton','(999)999-9999', '1236', 'abc-122'),
('John', 'Smith','(999)999-9999','1237',NULL);

INSERT INTO ParkingSpace (lotName, spaceType) VALUES
('A',"Free"),
('A',"Free"),
('A','Covered'),
('A','Covered'),
('A','Visitor'),
('A','Visitor');

-- Spaces that aren't reserved 
-- Staff Reservation -> Space Drop Down
SELECT *
FROM ParkingSpace
WHERE spaceID NOT IN (
	-- Reservations that haven't ended
	SELECT spaceID
	FROM CurrentStaffReservation
)
AND spaceType = "Covered";

SELECT spaceID
FROM CurrentStaffReservation


SELECT *
FROM ParkingSpace
WHERE spaceID NOT IN (
	SELECT spaceID
	FROM StaffReservation
	WHERE startDate < CURDATE())
AND spaceType = "Covered";

-- Currently Reserved Spaces
SELECT *
FROM StaffReservation
WHERE endDate < CURDATE()

-- Lot spaces
SELECT lotName, COUNT(spaceID) 'Spaces'
FROM ParkingSpace