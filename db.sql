-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Июн 23 2014 г., 17:36
-- Версия сервера: 5.5.24-log
-- Версия PHP: 5.3.13

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
  `bookingStatus` varchar(255) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6713A0395C0E6255` (`ticket_id`),
  KEY `FK6713A039C37EBE55` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(255) DEFAULT NULL,
  `customerSurname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eventName` varchar(255) DEFAULT NULL,
  `eventDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `sector`
--

CREATE TABLE IF NOT EXISTS `sector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(255) DEFAULT NULL,
  `seatsQuantity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
  KEY `FKC697C163363FCC15` (`sector_id`),
  KEY `FKC697C16325D67FF` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticketNumber` varchar(255) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `seat_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK954D572C25D67FF` (`event_id`),
  KEY `FK954D572CCFBA9E75` (`seat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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



INSERT INTO `event` (`eventName`,  `eventTime`)
VALUES ('Черноморец - Карпаты','2014-05-18 15:00:00'),
('Говерла - Черноморец','2014-05-20 16:00:00'),
('Черноморец - Карпаты','2014-05-23 11:00:00'),
('Говерла - Черноморец','2014-05-23 19:00:00'),
('Черноморец - Карпаты','2014-05-16 17:00:00'),
('Шахтер - Волынь','2014-05-18 14:00:00'),
('Металлист - Карпаты','2014-05-18 12:00:00'),
('Говерла - Черноморец','2014-05-20 14:00:00');