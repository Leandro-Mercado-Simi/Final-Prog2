/*
SQLyog Ultimate v9.02 
MySQL - 8.0.18 : Database - final_prog2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`final_prog2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `final_prog2`;

/*Table structure for table `alumno` */

DROP TABLE IF EXISTS `alumno`;

CREATE TABLE `alumno` (
  `alu_dni` bigint(20) NOT NULL,
  `alu_nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `alu_apellido` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `alu_fec_nac` date NOT NULL,
  `alu_domicilio` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `alu_telefono` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `alu_insc_cod` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`alu_dni`),
  KEY `FK_alu` (`alu_insc_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `alumno` */

insert  into `alumno`(`alu_dni`,`alu_nombre`,`alu_apellido`,`alu_fec_nac`,`alu_domicilio`,`alu_telefono`,`alu_insc_cod`) values (39237216,'Gabriel','Bermudez','1995-10-05','Ayohuma 1395','2616145079',0),(39380032,'Gonzalo','Ramirez','1995-09-30','Calle fake 321','2612435941',0);

/*Table structure for table `carrera` */

DROP TABLE IF EXISTS `carrera`;

CREATE TABLE `carrera` (
  `car_cod` bigint(20) NOT NULL,
  `car_nombre` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `car_duracion` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`car_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `carrera` */

insert  into `carrera`(`car_cod`,`car_nombre`,`car_duracion`) values (12,'Programación','3 años'),(25,'Medicina','6 años'),(18481,'Derecho','4 años');

/*Table structure for table `cursado` */

DROP TABLE IF EXISTS `cursado`;

CREATE TABLE `cursado` (
  `cur_alu_dni` bigint(20) NOT NULL,
  `cur_mat_cod` bigint(20) NOT NULL,
  `cur_nota` double NOT NULL,
  PRIMARY KEY (`cur_alu_dni`,`cur_mat_cod`),
  KEY `FK_cursado_materia` (`cur_mat_cod`),
  CONSTRAINT `FK_cursado_alu` FOREIGN KEY (`cur_alu_dni`) REFERENCES `alumno` (`alu_dni`),
  CONSTRAINT `FK_cursado_materia` FOREIGN KEY (`cur_mat_cod`) REFERENCES `materia` (`mat_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `cursado` */

insert  into `cursado`(`cur_alu_dni`,`cur_mat_cod`,`cur_nota`) values (39237216,1205,9);

/*Table structure for table `inscripcion` */

DROP TABLE IF EXISTS `inscripcion`;

CREATE TABLE `inscripcion` (
  `insc_cod` bigint(20) NOT NULL AUTO_INCREMENT,
  `insc_nombre` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ins_fecha` date NOT NULL,
  `ins_car_cod` bigint(20) NOT NULL,
  PRIMARY KEY (`insc_cod`),
  KEY `FK_inscripcion_carrera` (`ins_car_cod`),
  CONSTRAINT `FK_inscripcion_alumno` FOREIGN KEY (`insc_cod`) REFERENCES `alumno` (`alu_insc_cod`),
  CONSTRAINT `FK_inscripcion_carrera` FOREIGN KEY (`ins_car_cod`) REFERENCES `carrera` (`car_cod`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `inscripcion` */

insert  into `inscripcion`(`insc_cod`,`insc_nombre`,`ins_fecha`,`ins_car_cod`) values (1,'39237216','2020-06-20',15),(2,'39237216','2020-06-22',2),(3,'39237216','2020-06-22',3),(4,'39237216','2020-06-22',4),(5,'39237216','2020-06-22',5),(6,'39237216','2020-06-22',6),(7,'39237216','2020-06-22',7),(9,'39237216','2020-06-22',12);

/*Table structure for table `materia` */

DROP TABLE IF EXISTS `materia`;

CREATE TABLE `materia` (
  `mat_cod` bigint(20) NOT NULL,
  `mat_nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `mat_prof_dni` bigint(20) NOT NULL,
  PRIMARY KEY (`mat_cod`),
  KEY `FK_mat` (`mat_prof_dni`),
  CONSTRAINT `FK_mat` FOREIGN KEY (`mat_prof_dni`) REFERENCES `profesor` (`prof_dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `materia` */

insert  into `materia`(`mat_cod`,`mat_nombre`,`mat_prof_dni`) values (15,'Laboratorio',20114056),(555,'Arquitectura',20114056),(1205,'Programación 1',39842421);

/*Table structure for table `profesor` */

DROP TABLE IF EXISTS `profesor`;

CREATE TABLE `profesor` (
  `prof_dni` bigint(20) NOT NULL,
  `prof_nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `prof_apellido` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `prof_fec_nac` date NOT NULL,
  `prof_domicilio` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `prof_telefono` bigint(20) NOT NULL,
  PRIMARY KEY (`prof_dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `profesor` */

insert  into `profesor`(`prof_dni`,`prof_nombre`,`prof_apellido`,`prof_fec_nac`,`prof_domicilio`,`prof_telefono`) values (20114056,'Javier','Mercado','2020-06-03','mi casa',2615594664),(39842421,'Leandro','Mercado','1996-08-14','Bandera de los Andes 10174',2616767667);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
