-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Июл 06 2014 г., 11:16
-- Версия сервера: 5.5.24-log
-- Версия PHP: 5.3.13

-- Version db.sql: 1.2.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
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
  `bookingStatus` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_BOOKING_TICKET` (`ticket_id`),
  KEY `FK_BOOKING_CUSTOMER` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `customerSurname` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookingCanceltime` int(11) NOT NULL DEFAULT '30',
  `eventDate` datetime DEFAULT NULL,
  `eventName` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `isDelete` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=9 ;

--
-- Дамп данных таблицы `event`
--

INSERT INTO `event` (`id`, `bookingCanceltime`, `eventDate`, `eventName`, `isDelete`) VALUES
(1, 30, '2014-05-18 15:00:00', 'Черноморец - Карпаты', b'0'),
(2, 30, '2014-05-20 16:00:00', 'Говерла - Черноморец', b'0'),
(3, 30, '2014-05-23 11:00:00', 'Черноморец - Карпаты', b'0'),
(4, 30, '2014-05-23 19:00:00', 'Говерла - Черноморец', b'0'),
(5, 30, '2014-05-16 17:00:00', 'Черноморец - Карпаты', b'0'),
(6, 30, '2014-05-18 14:00:00', 'Шахтер - Волынь', b'0'),
(7, 30, '2014-05-18 12:00:00', 'Металлист - Карпаты', b'0'),
(8, 30, '2014-05-20 14:00:00', 'Говерла - Черноморец', b'0');

-- --------------------------------------------------------

--
-- Структура таблицы `event_sectorprice`
--

CREATE TABLE IF NOT EXISTS `event_sectorprice` (
  `Event_id` int(11) NOT NULL,
  `sectorPriceSet_id` int(11) NOT NULL,
  UNIQUE KEY `sectorPriceSet_id` (`sectorPriceSet_id`),
  KEY `FK_EVENTSECTORPRICE_EVENT` (`Event_id`),
  KEY `FK_EVENTSECTORPRICE_SECTORPRICE` (`sectorPriceSet_id`)
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
  KEY `FK_SEAT_SECTOR` (`sector_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `sector`
--

CREATE TABLE IF NOT EXISTS `sector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `seatsQuantity` int(11) NOT NULL,
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
  `price` double NOT NULL,
  `event_id` int(11) DEFAULT NULL,
  `sector_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FFK_SECTORPRICE_SECTOR` (`sector_id`),
  KEY `FK_SECTORPRICE_EVENT` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticketNumber` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `seat_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_TICKET_SEAT` (`seat_id`),
  KEY `FK_TICKET_EVENT` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 ;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK_BOOKING_CUSTOMER` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `FK_BOOKING_TICKET` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`);
--
-- Ограничения внешнего ключа таблицы `event_sectorprice`
--
ALTER TABLE `event_sectorprice`
  ADD CONSTRAINT `FK_EVENTSECTORPRICE_SECTORPRICE` FOREIGN KEY (`sectorPriceSet_id`) REFERENCES `sectorprice` (`id`),
  ADD CONSTRAINT `FK_EVENTSECTORPRICE_EVENT` FOREIGN KEY (`Event_id`) REFERENCES `event` (`id`);

--
-- Ограничения внешнего ключа таблицы `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `FK_SEAT_SECTOR` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`);
--
-- Ограничения внешнего ключа таблицы `sectorprice`
--
ALTER TABLE `sectorprice`
  ADD CONSTRAINT `FK_SECTORPRICE_EVENT` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `FK_SECTORPRICE_SECTOR` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`);
--
-- Ограничения внешнего ключа таблицы `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `FK_TICKET_SEAT` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`),
  ADD CONSTRAINT `FK_TICKET_EVENT` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
