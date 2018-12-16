--Create a Schema
 CREATE SCHEMA pes DEFAULT CHARACTER SET utf8;

 --Use database
 USE pes;

 --Create Table called attendence
 CREATE TABLE attendence (date date NOT NULL, 
 	epfNo varchar(10) NOT NULL, 
 	timeIn time, 
 	timeOut time, 
 	PRIMARY KEY (date, epfNo)) 
 ENGINE = InnoDB 
 CHARACTER SET = utf8;

 --Create Table called setteams (one to one relationship with attendence)
 CREATE TABLE productionsetteams (date date NOT NULL,
 	epfNo varchar(10) NOT NULL,
 	team varchar(2),
 	operation varchar(100),
 	PRIMARY KEY (date, epfNo),
 	CONSTRAINT fk_setteams FOREIGN KEY (date, epfNo) REFERENCES attendence(date, epfNo) ON UPDATE CASCADE)
 ENGINE = InnoDB
 CHARACTER SET = utf8;

--Create cuttingdepartmentemp one to one with attendance
 CREATE TABLE cuttingdepartmentemp(date date NOT NULL,
 	epfNo varchar(10) NOT NULL,
 	PRIMARY KEY (date, epfNo),
 	CONSTRAINT fk_cuttingdepemp FOREIGN KEY (date, epfNo) REFERENCES attendence (date, epfNo) ON UPDATE CASCADE)
 ENGINE = InnoDB
 CHARACTER SET = utf8;

 --Delete all records from table setteams
 DELETE TABLE setteams;

 --Create table called teamdata (one to many relationship with setteams)
 CREATE TABLE productionteamdata (date date NOT NULL,
 	team varchar(2) NOT NULL,
 	supervisor varchar(50),
 	PRIMARY KEY (date, team),
 	CONSTRAINT fk_teamdata FOREIGN KEY (date, supervisor) REFERENCES attendence(date, epfNo) ON UPDATE CASCADE)
 ENGINE = InnoDB
 CHARACTER SET = utf8;

--Create table called users to give access for users
CREATE TABLE users (epfNo varchar(10) NOT NULL,
	username varchar(30) NOT NULL UNIQUE,
	password varchar(8) NOT NULL,
	PRIMARY KEY (epfNo))
ENGINE = InnoDB
CHARACTER SET = utf8;

--Create userdata table (one to one realationship with users)
CREATE TABLE userdata(epfNo varchar(10) NOT NULL,
	name varchar(50),
	nameUse varchar(20),
	gender varchar(6),
	num varchar(6),
	street varchar(20),
	city1 varchar(20),
	city2 varchar(20),
	dob date,
	nic varchar(10) UNIQUE,
	religion varchar(20),
	nationality varchar(20),
	maritalStatus varchar(20),
	phone varchar(10),
	email varchar(50),
	department varchar(20),
	designation varchar(20) NOT NULL,
	joinDate date,
	barcode varchar(11),
	PRIMARY KEY (epfNo),
	CONSTRAINT fk_users FOREIGN KEY (epfNo) REFERENCES users(epfNo) ON UPDATE CASCADE)
ENGINE = InnoDB
CHARACTER SET = utf8;