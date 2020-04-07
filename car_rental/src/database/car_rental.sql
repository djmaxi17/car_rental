-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2020 at 10:32 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `car_rental`
--

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `carPlateNum` varchar(10) NOT NULL,
  `carMake` varchar(15) NOT NULL,
  `carModel` varchar(15) NOT NULL,
  `carType` varchar(15) NOT NULL,
  `carYear` year(4) NOT NULL,
  `carTransmission` varchar(9) NOT NULL,
  `carFuelType` varchar(15) DEFAULT NULL,
  `carImage` longblob NOT NULL,
  `carNumOfSeat` int(11) NOT NULL,
  `carPricePerDay` double NOT NULL,
  `carPenaltyPrice` double NOT NULL,
  `carTrunkSize` double DEFAULT NULL,
  `carEngineSize` int(11) DEFAULT NULL,
  `carRating` int(11) NOT NULL,
  `registeredByEmp` int(11) NOT NULL,
  `carAvailability` tinyint(1) NOT NULL DEFAULT 1
) ;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`carPlateNum`, `carMake`, `carModel`, `carType`, `carYear`, `carTransmission`, `carFuelType`, `carImage`, `carNumOfSeat`, `carPricePerDay`, `carPenaltyPrice`, `carTrunkSize`, `carEngineSize`, `carRating`, `registeredByEmp`, `carAvailability`) VALUES
('jjj', 'ddd', 'dd', 'df', 2019, 'manual', 'petrol', 0x433a55736572734461766973656e4465736b746f7044657369676e00345f34315f3133395f70736974746163756c615f6563686f5f6d2e6a7067, 3, 200, 21, 23, 23, 0, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customerId` bigint(20) NOT NULL,
  `customerFirstName` varchar(15) NOT NULL,
  `customerLastName` varchar(15) NOT NULL,
  `customerEmail` varchar(30) NOT NULL,
  `customerDob` date NOT NULL,
  `customerPhone` int(11) NOT NULL,
  `customerAddress` text NOT NULL,
  `cusLicenseNum` varchar(30) NOT NULL,
  `customerGender` varchar(6) NOT NULL,
  `registeredByEmp` int(11) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `employeeId` int(11) NOT NULL,
  `employeeFirstName` varchar(20) NOT NULL,
  `employeeLastName` varchar(20) NOT NULL,
  `employeeEmail` varchar(60) NOT NULL,
  `employeePassword` text NOT NULL,
  `employeeRole` varchar(10) NOT NULL,
  `registeredByEmp` int(11) DEFAULT NULL
) ;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`employeeId`, `employeeFirstName`, `employeeLastName`, `employeeEmail`, `employeePassword`, `employeeRole`, `registeredByEmp`) VALUES
(1, 'admin', 'admin', 'admin', 'admin', 'technician', NULL),
(63, 'm', 'h', 'm.h@maxiautomobile.com', 'maxi8m', 'clerk', 1),
(64, 's', 's', 's.s@maxiautomobile.com', 'maxi9s', 'technician', 1),
(65, 'keshav', 'idk', 'keshav.idk@maxiautomobile.com', 'maxi0keshav', 'manager', 1),
(66, 'uy', 'bn', 'uy.bn@maxiautomobile.com', 'maxi5uy', 'technician', 1),
(67, 'bn', 'jk', 'bn.jk@maxiautomobile.com', 'maxi3bn', 'technician', 1),
(68, 'd', 'd', 'd.d@maxiautomobile.com', 'maxi4d', 'manager', 1),
(69, 'm', 'n', 'm.n@maxiautomobile.com', 'maxi6m', 'manager', 1),
(70, 's', 'q', 's.q@maxiautomobile.com', 'maxi0s', 'manager', 1),
(72, 's', 's', 's.s@maxiautomobile.com', 'maxi5s', 'clerk', 1),
(76, 'facebook', 'facebook', 'facebook.facebook@maxiautomobile.com', 'maxi8facebook', 'manager', 1),
(77, 'davisen', 'permall', 'davisen.permall@maxiautomobile.com', 'maxi7davisen', 'clerk', 1),
(78, 'd', 'd', 'd.d@maxiautomobile.com', 'maxi6d', 'clerk', 1),
(79, 'e', 'd', 'e.d@maxiautomobile.com', 'maxi2e', 'manager', 1);

-- --------------------------------------------------------

--
-- Stand-in structure for view `managers`
-- (See below for the actual view)
--
CREATE TABLE `managers` (
`employeeId` int(11)
,`employeeFirstName` varchar(20)
,`employeeLastName` varchar(20)
,`employeeEmail` varchar(60)
,`employeePassword` text
,`employeeRole` varchar(10)
,`registeredByEmp` int(11)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `managersclerks`
-- (See below for the actual view)
--
CREATE TABLE `managersclerks` (
`employeeId` int(11)
,`employeeFirstName` varchar(20)
,`employeeLastName` varchar(20)
,`employeeEmail` varchar(60)
,`employeePassword` text
,`employeeRole` varchar(10)
,`registeredByEmp` int(11)
);

-- --------------------------------------------------------

--
-- Table structure for table `rents`
--

CREATE TABLE `rents` (
  `rentId` bigint(20) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `carPlateNum` varchar(10) NOT NULL,
  `registeredByEmp` int(11) NOT NULL,
  `dateRented` date NOT NULL DEFAULT current_timestamp(),
  `dateDue` date NOT NULL,
  `numOfDaysDefault` int(11) NOT NULL,
  `rentCost` double NOT NULL,
  `customerPoints` int(11) NOT NULL,
  `dateReturned` date DEFAULT NULL,
  `rentPenalty` double DEFAULT 0,
  `numOfDaysTaken` int(11) DEFAULT NULL,
  `rentStatus` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Stand-in structure for view `technicians`
-- (See below for the actual view)
--
CREATE TABLE `technicians` (
`employeeId` int(11)
,`employeeFirstName` varchar(20)
,`employeeLastName` varchar(20)
,`employeeEmail` varchar(60)
,`employeePassword` text
,`employeeRole` varchar(10)
,`registeredByEmp` int(11)
);

-- --------------------------------------------------------

--
-- Structure for view `managers`
--
DROP TABLE IF EXISTS `managers`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `managers`  AS  select `employees`.`employeeId` AS `employeeId`,`employees`.`employeeFirstName` AS `employeeFirstName`,`employees`.`employeeLastName` AS `employeeLastName`,`employees`.`employeeEmail` AS `employeeEmail`,`employees`.`employeePassword` AS `employeePassword`,`employees`.`employeeRole` AS `employeeRole`,`employees`.`registeredByEmp` AS `registeredByEmp` from `employees` where `employees`.`employeeRole` = 'manager' WITH CASCADED CHECK OPTION ;

-- --------------------------------------------------------

--
-- Structure for view `managersclerks`
--
DROP TABLE IF EXISTS `managersclerks`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `managersclerks`  AS  select `employees`.`employeeId` AS `employeeId`,`employees`.`employeeFirstName` AS `employeeFirstName`,`employees`.`employeeLastName` AS `employeeLastName`,`employees`.`employeeEmail` AS `employeeEmail`,`employees`.`employeePassword` AS `employeePassword`,`employees`.`employeeRole` AS `employeeRole`,`employees`.`registeredByEmp` AS `registeredByEmp` from `employees` where `employees`.`employeeRole` = 'manager' or `employees`.`employeeRole` = 'clerk' WITH CASCADED CHECK OPTION ;

-- --------------------------------------------------------

--
-- Structure for view `technicians`
--
DROP TABLE IF EXISTS `technicians`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `technicians`  AS  select `employees`.`employeeId` AS `employeeId`,`employees`.`employeeFirstName` AS `employeeFirstName`,`employees`.`employeeLastName` AS `employeeLastName`,`employees`.`employeeEmail` AS `employeeEmail`,`employees`.`employeePassword` AS `employeePassword`,`employees`.`employeeRole` AS `employeeRole`,`employees`.`registeredByEmp` AS `registeredByEmp` from `employees` where `employees`.`employeeRole` = 'technician' WITH CASCADED CHECK OPTION ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`carPlateNum`),
  ADD KEY `FK_cars_man` (`registeredByEmp`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customerId`),
  ADD KEY `FK_custs_mc` (`registeredByEmp`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`employeeId`),
  ADD KEY `FK_emp_tec` (`registeredByEmp`);

--
-- Indexes for table `rents`
--
ALTER TABLE `rents`
  ADD PRIMARY KEY (`rentId`),
  ADD KEY `FK_rents_mc` (`registeredByEmp`),
  ADD KEY `FK_rents_car` (`carPlateNum`),
  ADD KEY `FK_rents_cust` (`customerId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customerId` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `employeeId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rents`
--
ALTER TABLE `rents`
  MODIFY `rentId` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cars`
--
ALTER TABLE `cars`
  ADD CONSTRAINT `FK_cars_man` FOREIGN KEY (`registeredByEmp`) REFERENCES `managers` (`employeeId`);

--
-- Constraints for table `customers`
--
ALTER TABLE `customers`
  ADD CONSTRAINT `FK_custs_mc` FOREIGN KEY (`registeredByEmp`) REFERENCES `managersclerks` (`employeeId`);

--
-- Constraints for table `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `FK_emp_tec` FOREIGN KEY (`registeredByEmp`) REFERENCES `technicians` (`employeeId`);

--
-- Constraints for table `rents`
--
ALTER TABLE `rents`
  ADD CONSTRAINT `FK_rents_car` FOREIGN KEY (`carPlateNum`) REFERENCES `cars` (`carPlateNum`),
  ADD CONSTRAINT `FK_rents_cust` FOREIGN KEY (`customerId`) REFERENCES `customers` (`customerId`),
  ADD CONSTRAINT `FK_rents_mc` FOREIGN KEY (`registeredByEmp`) REFERENCES `managersclerks` (`employeeId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
