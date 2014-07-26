-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Июл 24 2014 г., 23:52
-- Версия сервера: 5.6.17
-- Версия PHP: 5.5.12
--
-- version db.sql: 1.4.2
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=23 ;

--
-- Дамп данных таблицы `booking`
--

INSERT INTO `booking` (`id`, `bookingStatus`, `customer_id`, `ticket_id`) VALUES
(1, 'Booked', 1, 1),
(2, 'BookingCancelled', 1, 2),
(3, 'Booked', 1, 3),
(4, 'BookingCancelled', 2, 13),
(5, 'BookingCancelled', 2, 14),
(6, 'BookingCancelled', 2, 15),
(7, 'Booked', 2, 16),
(8, 'BookingCancelled', 2, 17),
(9, 'BookingCancelled', 2, 18),
(10, 'Booked', 3, 24),
(11, 'BookingCancelled', 3, 25),
(12, 'Booked', 4, 26),
(13, 'Booked', 4, 27),
(14, 'Booked', 4, 28),
(15, 'Booked', 4, 29),
(16, 'BookingCancelled', 4, 30),
(17, 'BookingCancelled', 4, 31),
(18, 'Booked', 5, 32),
(19, 'BookingCancelled', 5, 33),
(20, 'BookingCancelled', 5, 34),
(21, 'BookingCancelled', 6, 43),
(22, 'Booked', 7, 44);

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Дамп данных таблицы `customer`
--

INSERT INTO `customer` (`id`, `customerName`) VALUES
(1, 'ghdh'),
(2, 'fhdhf'),
(3, 'hgjvjv'),
(4, 'вапы'),
(5, 'wwwwsth'),
(6, 'new'),
(7, 'new');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Дамп данных таблицы `event`
--

INSERT INTO `event` (`id`, `bookingCanceltime`, `eventDate`, `eventName`, `isDelete`) VALUES
(1, 30, '2014-12-21 23:30:00', 'событие', b'0'),
(2, 30, '2014-12-13 21:30:00', 'событие3', b'0');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `event_sectorprice`
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
-- Структура таблицы `seat`
--

CREATE TABLE IF NOT EXISTS `seat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rowNumber` int(11) NOT NULL,
  `seatNumber` int(11) NOT NULL,
  `sector_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK274225363FCC15` (`sector_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=45 ;

--
-- Дамп данных таблицы `seat`
--

INSERT INTO `seat` (`id`, `rowNumber`, `seatNumber`, `sector_id`) VALUES
(1, 8, 18, 20),
(2, 5, 12, 20),
(3, 4, 8, 20),
(4, 5, 8, 20),
(5, 6, 12, 20),
(6, 9, 18, 20),
(7, 6, 19, 5),
(8, 5, 19, 5),
(9, 4, 19, 5),
(10, 3, 19, 5),
(11, 2, 19, 5),
(12, 1, 19, 5),
(13, 1, 20, 5),
(14, 2, 20, 5),
(15, 3, 20, 5),
(16, 4, 20, 5),
(17, 5, 20, 5),
(18, 6, 20, 5),
(19, 9, 17, 22),
(20, 6, 14, 22),
(21, 3, 16, 22),
(22, 5, 21, 22),
(23, 3, 16, 20),
(24, 13, 17, 24),
(25, 11, 13, 24),
(26, 4, 19, 1),
(27, 4, 14, 1),
(28, 6, 11, 1),
(29, 8, 14, 1),
(30, 3, 10, 5),
(31, 4, 9, 5),
(32, 7, 6, 26),
(33, 6, 5, 26),
(34, 2, 4, 26),
(35, 3, 11, 26),
(36, 3, 10, 26),
(37, 4, 11, 27),
(38, 1, 7, 27),
(39, 3, 10, 27),
(40, 6, 23, 1),
(41, 9, 21, 1),
(42, 12, 20, 1),
(43, 5, 12, 20),
(44, 5, 12, 20);

-- --------------------------------------------------------

--
-- Структура таблицы `sector`
--

CREATE TABLE IF NOT EXISTS `sector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `seatsQuantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=28 ;

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
(26, 'VIP A', 200),
(27, 'VIP D', 200);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=55 ;

--
-- Дамп данных таблицы `sectorprice`
--

INSERT INTO `sectorprice` (`id`, `price`, `event_id`, `sector_id`) VALUES
(1, 1, 1, 1),
(2, 2, 1, 2),
(3, 3, 1, 3),
(4, 4, 1, 4),
(5, 5, 1, 5),
(6, 6, 1, 6),
(7, 7, 1, 7),
(8, 8, 1, 8),
(9, 9, 1, 9),
(10, 10, 1, 10),
(11, 11, 1, 11),
(12, 12, 1, 12),
(13, 13, 1, 13),
(14, 14, 1, 14),
(15, 15, 1, 15),
(16, 16, 1, 16),
(17, 17, 1, 17),
(18, 18, 1, 18),
(19, 19, 1, 19),
(20, 20, 1, 20),
(21, 21, 1, 21),
(22, 22, 1, 22),
(23, 23, 1, 23),
(24, 24, 1, 24),
(25, 25, 1, 25),
(26, 50, 1, 26),
(27, 100, 1, 27),
(28, 1, 2, 1),
(29, 1, 2, 2),
(30, 1, 2, 3),
(31, 1, 2, 4),
(32, 1, 2, 5),
(33, 1, 2, 6),
(34, 1, 2, 7),
(35, 1, 2, 8),
(36, 1, 2, 9),
(37, 1, 2, 10),
(38, 1, 2, 11),
(39, 1, 2, 12),
(40, 1, 2, 13),
(41, 1, 2, 14),
(42, 1, 2, 15),
(43, 1, 2, 16),
(44, 1, 2, 17),
(45, 1, 2, 18),
(46, 1, 2, 19),
(47, 1, 2, 20),
(48, 1, 2, 21),
(49, 1, 2, 22),
(50, 1, 2, 23),
(51, 1, 2, 24),
(52, 1, 2, 25),
(53, 1, 2, 26),
(54, 1, 2, 27);

-- --------------------------------------------------------

--
-- Структура таблицы `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seatStatus` varchar(255) DEFAULT NULL,
  `ticketNumber` varchar(255) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `seat_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK954D572C25D67FF` (`event_id`),
  KEY `FK954D572CCFBA9E75` (`seat_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=45 ;

--
-- Дамп данных таблицы `ticket`
--

INSERT INTO `ticket` (`id`, `seatStatus`, `ticketNumber`, `event_id`, `seat_id`) VALUES
(1,'booked', '1352440861', 1, 1),
(2,'vacant', '751432889', 1, 2),
(3,'booked', '1327089473', 1, 3),
(4, 'occupied', '1526886619', 1, 4),
(5, 'occupied', '-1922310474', 1, 5),
(6, 'occupied', '423268277', 1, 6),
(7, 'occupied', '429491998', 1, 7),
(8, 'occupied', '1363524451', 1, 8),
(9, 'occupied', '1209937315', 1, 9),
(10, 'occupied', '878839331', 1, 10),
(11, 'occupied', '1256135975', 1, 11),
(12, 'occupied', '1692968049', 1, 12),
(13,'vacant', '877104040', 1, 13),
(14,'vacant', '-1778820083', 1, 14),
(15,'vacant', '-1927280259', 1, 15),
(16,'booked', '1639507358', 1, 16),
(17,'vacant', '1608390280', 1, 17),
(18,'vacant', '1437047454', 1, 18),
(19, 'occupied', '-2118790854', 1, 19),
(20, 'occupied', '995074664', 1, 20),
(21, 'occupied', '1099731079', 1, 21),
(22, 'occupied', '2042050178', 1, 22),
(23, 'occupied', '2076973147', 1, 23),
(24,'booked', '-1780229286', 1, 24),
(25,'vacant', '2136096581', 1, 25),
(26,'booked', '1825372973', 1, 26),
(27,'booked', '1753179724', 1, 27),
(28,'booked', '1108775809', 1, 28),
(29,'booked', '1334184508', 1, 29),
(30,'vacant', '2030642301', 1, 30),
(31,'vacant', '1783366226', 1, 31),
(32,'booked', '2028230514', 1, 32),
(33,'vacant', '-2013345474', 1, 33),
(34,'vacant', '678396421', 1, 34),
(35, 'occupied', '-1838814084', 1, 35),
(36, 'occupied', '727380956', 1, 36),
(37, 'occupied', '1885009002', 1, 37),
(38, 'occupied', '1891744771', 1, 38),
(39, 'occupied', '1709370581', 1, 39),
(40, 'occupied', '411915981', 1, 40),
(41, 'occupied', '-1845726350', 1, 41),
(42, 'occupied', '1701952730', 1, 42),
(43,'vacant', '1872642826', 1, 43),
(44,'booked', '1070759522', 1, 44);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK6713A0395C0E6255` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`),
  ADD CONSTRAINT `FK6713A039C37EBE55` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);

--
-- Ограничения внешнего ключа таблицы `event_sectorprice`
--
ALTER TABLE `event_sectorprice`
  ADD CONSTRAINT `FKECC5BB1E25D67FF` FOREIGN KEY (`Event_id`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `FKECC5BB1EFD77DBC3` FOREIGN KEY (`sectorPriceSet_id`) REFERENCES `sectorprice` (`id`);

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
  ADD CONSTRAINT `FK954D572C25D67FF` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `FK954D572CCFBA9E75` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
