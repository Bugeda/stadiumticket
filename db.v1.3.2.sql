-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Июл 20 2014 г., 22:28
-- Версия сервера: 5.6.17
-- Версия PHP: 5.5.12
--
-- version db.sql: 1.3.2
--
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `stadiumticketdb`
--

-- --------------------------------------------------------

--
-- Структура таблицы `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookingStatus` varchar(255) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6713A0395C0E6255` (`ticket_id`),
  KEY `FK6713A039C37EBE55` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookingCanceltime` int(11) DEFAULT NULL,
  `eventDate` datetime DEFAULT NULL,
  `eventName` varchar(255) DEFAULT NULL,
  `isDelete` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `event_sectorprice`
--

CREATE TABLE IF NOT EXISTS `event_sectorprice` (
  `Event_id` int(11) NOT NULL,
  `sectorPriceSet_id` int(11) NOT NULL,
  UNIQUE KEY `sectorPriceSet_id` (`sectorPriceSet_id`),
  KEY `FKECC5BB1E25D67FF` (`Event_id`),
  KEY `FKECC5BB1EFD77DBC3` (`sectorPriceSet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `seat`
--

CREATE TABLE IF NOT EXISTS `seat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rowNumber` int(11) NOT NULL,
  `seatNumber` int(11) NOT NULL,
  `sector_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK274225363FCC15` (`sector_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `sector`
--

CREATE TABLE IF NOT EXISTS `sector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `seatsQuantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=28 ;

--
-- Дамп данных таблицы `sector`
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
-- Структура таблицы `sectorprice`
--

CREATE TABLE IF NOT EXISTS `sectorprice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `sector_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC697C163363FCC15` (`sector_id`),
  KEY `FKC697C16325D67FF` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `ticket`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK6713A039C37EBE55` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `FK6713A0395C0E6255` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`);

--
-- Ограничения внешнего ключа таблицы `event_sectorprice`
--
ALTER TABLE `event_sectorprice`
  ADD CONSTRAINT `FKECC5BB1EFD77DBC3` FOREIGN KEY (`sectorPriceSet_id`) REFERENCES `sectorprice` (`id`),
  ADD CONSTRAINT `FKECC5BB1E25D67FF` FOREIGN KEY (`Event_id`) REFERENCES `event` (`id`);

--
-- Ограничения внешнего ключа таблицы `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `FK274225363FCC15` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`);

--
-- Ограничения внешнего ключа таблицы `sectorprice`
--
ALTER TABLE `sectorprice`
  ADD CONSTRAINT `FKC697C16325D67FF` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `FKC697C163363FCC15` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`);

--
-- Ограничения внешнего ключа таблицы `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `FK954D572CCFBA9E75` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`),
  ADD CONSTRAINT `FK954D572C25D67FF` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
