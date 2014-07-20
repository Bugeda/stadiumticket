п»ї-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- РҐРѕСЃС‚: 127.0.0.1
-- Р’СЂРµРјСЏ СЃРѕР·РґР°РЅРёСЏ: Р�СЋР» 20 2014 Рі., 18:00
-- Р’РµСЂСЃРёСЏ СЃРµСЂРІРµСЂР°: 5.6.17
-- Р’РµСЂСЃРёСЏ PHP: 5.5.12

-- Version db.sql: 1.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Р‘Р°Р·Р° РґР°РЅРЅС‹С…: `stadiumticketdb`
--

-- --------------------------------------------------------

--
-- РЎС‚СЂСѓРєС‚СѓСЂР° С‚Р°Р±Р»РёС†С‹ `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookingStatus` varchar(255) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6713A0395C0E6255` (`ticket_id`),
  KEY `FK6713A039C37EBE55` (`customer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Р”Р°РјРї РґР°РЅРЅС‹С… С‚Р°Р±Р»РёС†С‹ `booking`
--

INSERT INTO `booking` (`id`, `bookingStatus`, `customer_id`, `ticket_id`) VALUES
(1, 'Booked', 1, 1),
(2, 'Booked', 1, 2),
(3, 'Booked', 1, 3),
(4, 'Booked', 1, 4);

-- --------------------------------------------------------

--
-- РЎС‚СЂСѓРєС‚СѓСЂР° С‚Р°Р±Р»РёС†С‹ `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Р”Р°РјРї РґР°РЅРЅС‹С… С‚Р°Р±Р»РёС†С‹ `customer`
--

INSERT INTO `customer` (`id`, `customerName`) VALUES
(1, 'РІРїС‹Р°Рї');

-- --------------------------------------------------------

--
-- РЎС‚СЂСѓРєС‚СѓСЂР° С‚Р°Р±Р»РёС†С‹ `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookingCanceltime` int(11) DEFAULT NULL,
  `eventDate` datetime DEFAULT NULL,
  `eventName` varchar(255) DEFAULT NULL,
  `isDelete` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Р”Р°РјРї РґР°РЅРЅС‹С… С‚Р°Р±Р»РёС†С‹ `event`
--

INSERT INTO `event` (`id`, `bookingCanceltime`, `eventDate`, `eventName`, `isDelete`) VALUES
(1, 30, '2015-02-21 01:15:00', 'СЃРѕР±С‹С‚РёРµ', b'0'),
(2, 30, '2014-12-21 01:00:00', 'СЃРѕР±С‹С‚РёРµ2', b'0');

-- --------------------------------------------------------

--
-- РЎС‚СЂСѓРєС‚СѓСЂР° С‚Р°Р±Р»РёС†С‹ `event_sectorprice`
--

CREATE TABLE IF NOT EXISTS `event_sectorprice` (
  `Event_id` int(11) NOT NULL,
  `sectorPriceSet_id` int(11) NOT NULL,
  UNIQUE KEY `sectorPriceSet_id` (`sectorPriceSet_id`),
  KEY `FKECC5BB1E25D67FF` (`Event_id`),
  KEY `FKECC5BB1EFD77DBC3` (`sectorPriceSet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Р”Р°РјРї РґР°РЅРЅС‹С… С‚Р°Р±Р»РёС†С‹ `event_sectorprice`
--

INSERT INTO `event_sectorprice` (`Event_id`, `sectorPriceSet_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 21),
(1, 22),
(1, 23),
(1, 24),
(1, 25),
(1, 26),
(1, 27),
(2, 28),
(2, 29),
(2, 30),
(2, 31),
(2, 32),
(2, 33),
(2, 34),
(2, 35),
(2, 36),
(2, 37),
(2, 38),
(2, 39),
(2, 40),
(2, 41),
(2, 42),
(2, 43),
(2, 44),
(2, 45),
(2, 46),
(2, 47),
(2, 48),
(2, 49),
(2, 50),
(2, 51),
(2, 52),
(2, 53),
(2, 54);

-- --------------------------------------------------------

--
-- РЎС‚СЂСѓРєС‚СѓСЂР° С‚Р°Р±Р»РёС†С‹ `seat`
--

CREATE TABLE IF NOT EXISTS `seat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rowNumber` int(11) NOT NULL,
  `seatNumber` int(11) NOT NULL,
  `sector_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK274225363FCC15` (`sector_id`),
  KEY `FK2742255C0E6255` (`ticket_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Р”Р°РјРї РґР°РЅРЅС‹С… С‚Р°Р±Р»РёС†С‹ `seat`
--

INSERT INTO `seat` (`id`, `rowNumber`, `seatNumber`, `sector_id`, `ticket_id`) VALUES
(1, 4, 15, 21, 1),
(2, 2, 11, 21, 2),
(3, 2, 21, 18, 3),
(4, 3, 15, 18, 4);

-- --------------------------------------------------------

--
-- РЎС‚СЂСѓРєС‚СѓСЂР° С‚Р°Р±Р»РёС†С‹ `sector`
--

CREATE TABLE IF NOT EXISTS `sector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `seatsQuantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=28 ;

--
-- Р”Р°РјРї РґР°РЅРЅС‹С… С‚Р°Р±Р»РёС†С‹ `sector`
--

INSERT INTO `sector` (`id`, `name`, `seatsQuantity`) VALUES
(1, '1', 1000),
(2, '2', 1000),
(3, '3', 1000),
(4, '4', 1000),
(5, '5', 1000),
(6, '6', 1000),
(7, '7', 1000),
(8, '8', 1000),
(9, '9', 1000),
(10, '10', 1000),
(11, '11', 1000),
(12, '12', 1000),
(13, '13', 1000),
(14, '14', 1000),
(15, '15', 1000),
(16, '16', 1000),
(17, '17', 1000),
(18, '18', 1000),
(19, '19', 1000),
(20, '20', 1000),
(21, '21', 1000),
(22, '22', 1000),
(23, '23', 1000),
(24, '24', 1000),
(25, '25', 1000),
(26, 'VIPA', 200),
(27, 'VIPD', 200);

-- --------------------------------------------------------

--
-- РЎС‚СЂСѓРєС‚СѓСЂР° С‚Р°Р±Р»РёС†С‹ `sectorprice`
--

CREATE TABLE IF NOT EXISTS `sectorprice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `sector_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC697C163363FCC15` (`sector_id`),
  KEY `FKC697C16325D67FF` (`event_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=55 ;

--
-- Р”Р°РјРї РґР°РЅРЅС‹С… С‚Р°Р±Р»РёС†С‹ `sectorprice`
--

INSERT INTO `sectorprice` (`id`, `price`, `event_id`, `sector_id`) VALUES
(1, 1, 1, 1),
(2, 1, 1, 2),
(3, 1, 1, 3),
(4, 1, 1, 4),
(5, 1, 1, 5),
(6, 1, 1, 6),
(7, 1, 1, 7),
(8, 1, 1, 8),
(9, 1, 1, 9),
(10, 1, 1, 10),
(11, 1, 1, 11),
(12, 1, 1, 12),
(13, 1, 1, 13),
(14, 1, 1, 14),
(15, 1, 1, 15),
(16, 1, 1, 16),
(17, 1, 1, 17),
(18, 1, 1, 18),
(19, 1, 1, 19),
(20, 1, 1, 20),
(21, 1, 1, 21),
(22, 1, 1, 22),
(23, 1, 1, 23),
(24, 1, 1, 24),
(25, 1, 1, 25),
(26, 1, 1, 26),
(27, 1, 1, 27),
(28, 1, 2, 1),
(29, 2, 2, 2),
(30, 3, 2, 3),
(31, 4, 2, 4),
(32, 5, 2, 5),
(33, 6, 2, 6),
(34, 7, 2, 7),
(35, 8, 2, 8),
(36, 9, 2, 9),
(37, 10, 2, 10),
(38, 11, 2, 11),
(39, 12, 2, 12),
(40, 13, 2, 13),
(41, 14, 2, 14),
(42, 15, 2, 15),
(43, 16, 2, 16),
(44, 17, 2, 17),
(45, 18, 2, 18),
(46, 19, 2, 19),
(47, 20, 2, 20),
(48, 21, 2, 21),
(49, 22, 2, 22),
(50, 23, 2, 23),
(51, 24, 2, 24),
(52, 25, 2, 25),
(53, 30, 2, 26),
(54, 50, 2, 27);

-- --------------------------------------------------------

--
-- РЎС‚СЂСѓРєС‚СѓСЂР° С‚Р°Р±Р»РёС†С‹ `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seatStatus` int(11) DEFAULT NULL,
  `ticketNumber` varchar(255) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `seat_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK954D572C25D67FF` (`event_id`),
  KEY `FK954D572CCFBA9E75` (`seat_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Р”Р°РјРї РґР°РЅРЅС‹С… С‚Р°Р±Р»РёС†С‹ `ticket`
--

INSERT INTO `ticket` (`id`, `seatStatus`, `ticketNumber`, `event_id`, `seat_id`) VALUES
(1, 1, '1015524410', 1, NULL),
(2, 1, '1448577697', 1, NULL),
(3, 1, '-1866006301', 1, NULL),
(4, 1, '-1944934280', 1, NULL);

--
-- РћРіСЂР°РЅРёС‡РµРЅРёСЏ РІРЅРµС€РЅРµРіРѕ РєР»СЋС‡Р° СЃРѕС…СЂР°РЅРµРЅРЅС‹С… С‚Р°Р±Р»РёС†
--

--
-- РћРіСЂР°РЅРёС‡РµРЅРёСЏ РІРЅРµС€РЅРµРіРѕ РєР»СЋС‡Р° С‚Р°Р±Р»РёС†С‹ `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK6713A039C37EBE55` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `FK6713A0395C0E6255` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`);

--
-- РћРіСЂР°РЅРёС‡РµРЅРёСЏ РІРЅРµС€РЅРµРіРѕ РєР»СЋС‡Р° С‚Р°Р±Р»РёС†С‹ `event_sectorprice`
--
ALTER TABLE `event_sectorprice`
  ADD CONSTRAINT `FKECC5BB1EFD77DBC3` FOREIGN KEY (`sectorPriceSet_id`) REFERENCES `sectorprice` (`id`),
  ADD CONSTRAINT `FKECC5BB1E25D67FF` FOREIGN KEY (`Event_id`) REFERENCES `event` (`id`);

--
-- РћРіСЂР°РЅРёС‡РµРЅРёСЏ РІРЅРµС€РЅРµРіРѕ РєР»СЋС‡Р° С‚Р°Р±Р»РёС†С‹ `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `FK2742255C0E6255` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`),
  ADD CONSTRAINT `FK274225363FCC15` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`);

--
-- РћРіСЂР°РЅРёС‡РµРЅРёСЏ РІРЅРµС€РЅРµРіРѕ РєР»СЋС‡Р° С‚Р°Р±Р»РёС†С‹ `sectorprice`
--
ALTER TABLE `sectorprice`
  ADD CONSTRAINT `FKC697C16325D67FF` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `FKC697C163363FCC15` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`);

--
-- РћРіСЂР°РЅРёС‡РµРЅРёСЏ РІРЅРµС€РЅРµРіРѕ РєР»СЋС‡Р° С‚Р°Р±Р»РёС†С‹ `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `FK954D572CCFBA9E75` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`),
  ADD CONSTRAINT `FK954D572C25D67FF` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
