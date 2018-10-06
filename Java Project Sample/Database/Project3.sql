-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 22, 2016 at 10:40 AM
-- Server version: 5.5.35
-- PHP Version: 5.3.10-1ubuntu3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Project3`
--

-- --------------------------------------------------------

--
-- Table structure for table `AcademicStanding`
--

CREATE TABLE IF NOT EXISTS `AcademicStanding` (
  `userId` int(11) NOT NULL,
  `seniority` int(11) NOT NULL,
  `gpa` double NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AcademicStanding`
--

INSERT INTO `AcademicStanding` (`userId`, `seniority`, `gpa`) VALUES
(1, 4, 3),
(2, 2, 1),
(3, 5, 3.5),
(4, 4, 1),
(5, 7, 2.5),
(6, 3, 3.5),
(7, 3, 1),
(8, 1, 3),
(9, 2, 1.5),
(10, 5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `CourseAssignment`
--

CREATE TABLE IF NOT EXISTS `CourseAssignment` (
  `userId` int(11) NOT NULL,
  `courseId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  UNIQUE KEY `DUPLICATE_ENTRY` (`userId`,`courseId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CourseAssignment`
--

INSERT INTO `CourseAssignment` (`userId`, `courseId`, `roleId`) VALUES
(11, 9, 1),
(11, 15, 1),
(11, 16, 1),
(12, 1, 1),
(12, 9, 1),
(12, 15, 1),
(12, 16, 1),
(13, 7, 1),
(13, 11, 1),
(13, 14, 1),
(13, 15, 1),
(13, 16, 1),
(14, 8, 1),
(14, 9, 1),
(14, 11, 1),
(14, 13, 1),
(14, 14, 1),
(14, 15, 1),
(14, 16, 1),
(15, 9, 1),
(15, 10, 1),
(15, 11, 1),
(15, 12, 1),
(15, 14, 1),
(15, 15, 1),
(15, 16, 1);

-- --------------------------------------------------------

--
-- Table structure for table `CourseAvailability`
--

CREATE TABLE IF NOT EXISTS `CourseAvailability` (
  `courseId` int(11) NOT NULL,
  `termId` int(11) NOT NULL,
  UNIQUE KEY `DUPLICATE_ENTRY` (`courseId`,`termId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CourseAvailability`
--

INSERT INTO `CourseAvailability` (`courseId`, `termId`) VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3),
(4, 1),
(4, 2),
(4, 3),
(5, 2),
(6, 1),
(6, 2),
(6, 3),
(7, 1),
(8, 1),
(8, 2),
(8, 3),
(9, 1),
(9, 2),
(9, 3),
(10, 2),
(11, 1),
(12, 1),
(12, 2),
(12, 3),
(13, 1),
(13, 2),
(13, 3),
(14, 2),
(15, 1),
(16, 2),
(17, 1),
(18, 2);

-- --------------------------------------------------------

--
-- Table structure for table `CourseCapacity`
--

CREATE TABLE IF NOT EXISTS `CourseCapacity` (
  `courseId` int(11) NOT NULL,
  `capacity` int(10) unsigned NOT NULL,
  `dirty` tinyint(1) NOT NULL,
  PRIMARY KEY (`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=ascii;

--
-- Dumping data for table `CourseCapacity`
--

INSERT INTO `CourseCapacity` (`courseId`, `capacity`, `dirty`) VALUES
(1, 100, 0),
(5, 20, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Courses`
--

CREATE TABLE IF NOT EXISTS `Courses` (
  `courseId` int(11) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(250) CHARACTER SET ascii NOT NULL,
  `courseCode` varchar(10) CHARACTER SET ascii NOT NULL,
  PRIMARY KEY (`courseId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `Courses`
--

INSERT INTO `Courses` (`courseId`, `courseName`, `courseCode`) VALUES
(1, 'Advanced Operating Systems', '6210'),
(2, 'Computer Networks', '6250'),
(3, 'Software Development Process', '6300'),
(4, 'Machine Learning', '7641'),
(5, 'High Performance Computer Architecture', '6290'),
(6, 'Software Architecture and Design', '6310'),
(7, 'Intro to Health Informatics', '6440'),
(8, 'Computability, Complexity and Algorithms', '6505'),
(9, 'Knowledge-Based Artificial Intelligence, Cognitive Systems', '7637'),
(10, 'Computer Vision', '4495'),
(11, 'Computational Photography', '6475'),
(12, 'Introduction to Operating Systems', '8803-002'),
(13, 'Artificial Intelligence for Robotics', '8803-001'),
(14, 'Introduction to Information Security', '6035'),
(15, 'High-Performance Computing', '6220'),
(16, 'Machine Learning for Trading', '7646'),
(17, 'Special Topics: Reinforcement Learning', '8803'),
(18, 'Special Topics: Big Data', '8803');

-- --------------------------------------------------------

--
-- Table structure for table `Demand`
--

CREATE TABLE IF NOT EXISTS `Demand` (
  `courseId` int(11) NOT NULL,
  `demand` varchar(250) NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `DemandHistory`
--

CREATE TABLE IF NOT EXISTS `DemandHistory` (
  `courseId` int(11) NOT NULL,
  `demand` varchar(250) NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Levels`
--

CREATE TABLE IF NOT EXISTS `Levels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=ascii AUTO_INCREMENT=8 ;

--
-- Dumping data for table `Levels`
--

INSERT INTO `Levels` (`id`, `name`) VALUES
(1, 'Freshman'),
(2, 'Sophomore'),
(3, 'Junior'),
(4, 'Senior'),
(5, 'Masters'),
(6, 'Ph.D.'),
(7, 'Post-Doc');

-- --------------------------------------------------------

--
-- Table structure for table `LoginHistory`
--

CREATE TABLE IF NOT EXISTS `LoginHistory` (
  `userId` int(11) NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `PreferredSchedule`
--

CREATE TABLE IF NOT EXISTS `PreferredSchedule` (
  `userId` int(11) NOT NULL,
  `preferredSchedule` varchar(250) CHARACTER SET ascii NOT NULL,
  `timestamp` datetime NOT NULL,
  `dirty` tinyint(1) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PreferredSchedule`
--

INSERT INTO `PreferredSchedule` (`userId`, `preferredSchedule`, `timestamp`, `dirty`) VALUES
(1, '2,5,3,1', '2016-04-20 10:46:49', 0),
(2, '3,6,3,2,7,8', '2016-04-20 10:46:49', 1);

-- --------------------------------------------------------

--
-- Table structure for table `PreferredScheduleHistory`
--

CREATE TABLE IF NOT EXISTS `PreferredScheduleHistory` (
  `userId` int(11) NOT NULL,
  `PreferredSchedule` varchar(250) CHARACTER SET ascii NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Recommendations`
--

CREATE TABLE IF NOT EXISTS `Recommendations` (
  `userId` int(11) NOT NULL,
  `courseList` varchar(250) CHARACTER SET ascii NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Requisites`
--

CREATE TABLE IF NOT EXISTS `Requisites` (
  `courseId` int(10) NOT NULL,
  `requires` int(10) NOT NULL,
  `type` int(10) NOT NULL,
  UNIQUE KEY `DUPLICATE_ENTRY` (`courseId`,`requires`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Requisites`
--

INSERT INTO `Requisites` (`courseId`, `requires`, `type`) VALUES
(1, 12, 1),
(7, 3, 1),
(13, 9, 1),
(16, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `RequisiteTypes`
--

CREATE TABLE IF NOT EXISTS `RequisiteTypes` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=ascii AUTO_INCREMENT=3 ;

--
-- Dumping data for table `RequisiteTypes`
--

INSERT INTO `RequisiteTypes` (`id`, `type`) VALUES
(1, 'Prerequisite'),
(2, 'Corequisite');

-- --------------------------------------------------------

--
-- Table structure for table `Roles`
--

CREATE TABLE IF NOT EXISTS `Roles` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB  DEFAULT CHARSET=ascii AUTO_INCREMENT=10 ;

--
-- Dumping data for table `Roles`
--

INSERT INTO `Roles` (`roleId`, `name`) VALUES
(1, 'TA'),
(3, 'Professor'),
(5, 'Student'),
(9, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `Semesters`
--

CREATE TABLE IF NOT EXISTS `Semesters` (
  `semesterId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `termId` int(11) NOT NULL,
  PRIMARY KEY (`semesterId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Semesters`
--

INSERT INTO `Semesters` (`semesterId`, `startDate`, `endDate`, `termId`) VALUES
(1, '2015-08-01', '2015-12-01', 1),
(2, '2016-02-01', '2016-06-01', 2),
(3, '2016-06-01', '2016-08-01', 3),
(4, '2016-08-01', '2016-12-01', 1),
(5, '2017-02-01', '2017-06-01', 2),
(6, '2017-06-01', '2017-08-01', 3),
(7, '2017-08-01', '2017-12-01', 1),
(8, '2018-02-01', '2018-06-01', 2),
(9, '2018-06-01', '2018-08-01', 3),
(10, '2018-08-01', '2018-12-01', 1),
(11, '2019-02-01', '2019-06-01', 2),
(12, '2019-06-01', '2019-08-01', 3);

-- --------------------------------------------------------

--
-- Table structure for table `TeachingCompetences`
--

CREATE TABLE IF NOT EXISTS `TeachingCompetences` (
  `userId` int(11) NOT NULL,
  `courseId` int(11) NOT NULL,
  UNIQUE KEY `DUPLICATE_ENTRY` (`userId`,`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Terms`
--

CREATE TABLE IF NOT EXISTS `Terms` (
  `termId` int(15) NOT NULL,
  `termName` varchar(12) CHARACTER SET ascii NOT NULL,
  PRIMARY KEY (`termId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Terms`
--

INSERT INTO `Terms` (`termId`, `termName`) VALUES
(1, 'Fall Term'),
(2, 'Spring Term'),
(3, 'Summer Term');

-- --------------------------------------------------------

--
-- Table structure for table `UserRoles`
--

CREATE TABLE IF NOT EXISTS `UserRoles` (
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  UNIQUE KEY `DUPLICATE_ENTRY` (`userId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `UserRoles`
--

INSERT INTO `UserRoles` (`userId`, `roleId`) VALUES
(1, 3),
(1, 5),
(2, 5),
(3, 5),
(4, 5),
(5, 5),
(6, 5),
(7, 5),
(8, 5),
(9, 5),
(10, 5),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `pwHash` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB  DEFAULT CHARSET=ascii AUTO_INCREMENT=16 ;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`userId`, `firstName`, `lastName`, `username`, `pwHash`) VALUES
(1, 'AUDREY', 'BERGER', 'ABERGE1', NULL),
(2, 'ROBERT', 'ORTIZ', 'RORTIZ1', NULL),
(3, 'CLARENCE', 'MILLS', 'CMILLS1', NULL),
(4, 'CYNTHIA', 'BARKER', 'CBARKE1', NULL),
(5, 'GERTRUDE', 'KING', 'GKING1', NULL),
(6, 'ROBERT', 'CALDWELL', 'RCALDW1', NULL),
(7, 'WILLIAM', 'JOHNSON', 'WJOHNS1', NULL),
(8, 'CATHY', 'SMITH', 'CSMITH1', NULL),
(9, 'KENNETH', 'DUNCAN', 'KDUNCA1', NULL),
(10, 'KATHERINE', 'ARMSTRONG', 'KARMST1', NULL),
(11, 'ANDREW', 'BAILEY', 'ABAILE1', NULL),
(12, 'DENISE', 'WILSON', 'DWILSO1', NULL),
(13, 'LAURA', 'GREEN', 'LGREEN1', NULL),
(14, 'PAMELA', 'BOOTH', 'PBOOTH1', NULL),
(15, 'JUDITH', 'HEBERT', 'JHEBER1', NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `AcademicStanding`
--
ALTER TABLE `AcademicStanding`
  ADD CONSTRAINT `AcademicStanding_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `Users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `CourseCapacity`
--
ALTER TABLE `CourseCapacity`
  ADD CONSTRAINT `CourseCapacity_ibfk_1` FOREIGN KEY (`courseId`) REFERENCES `Courses` (`courseId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Demand`
--
ALTER TABLE `Demand`
  ADD CONSTRAINT `Demand_ibfk_1` FOREIGN KEY (`courseId`) REFERENCES `Courses` (`courseId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `PreferredSchedule`
--
ALTER TABLE `PreferredSchedule`
  ADD CONSTRAINT `PreferredSchedule_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `Users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
