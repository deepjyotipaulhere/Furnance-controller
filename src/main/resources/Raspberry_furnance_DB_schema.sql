CREATE DATABASE IF NOT EXISTS raspberry_furnance;

USE raspberry_furnance;

DROP TABLE IF EXISTS temperature_sensors;

CREATE TABLE IF NOT EXISTS temperature_sensors
(
id int primary key not null auto_increment,
sensor_name varchar(20) unique not null,
address varchar(20) unique not null
);

INSERT INTO temperature_sensors (sensor_name, address) 
VALUES ("piec","23ceff3rcwefcsdfs");
INSERT INTO temperature_sensors (sensor_name, address) 
VALUES ("grzejniki","rtye45ge45e4");
INSERT INTO temperature_sensors (sensor_name, address) 
VALUES ("powrot","45ye45yfe45yrt");
INSERT INTO temperature_sensors (sensor_name, address) 
VALUES ("zewnatrz","e45yrtyfe54yfetry");