-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              10.4.6-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dump della struttura del database mythos_is
CREATE DATABASE IF NOT EXISTS `mythos_is` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mythos_is`;

-- Dump della struttura di tabella mythos_is.ordered_products
CREATE TABLE IF NOT EXISTS `ordered_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `purchaseUnitPrice` float NOT NULL DEFAULT 0,
  `product` int(11) DEFAULT NULL,
  `orderId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ordered_products_orders` (`orderId`),
  CONSTRAINT `FK_ordered_products_orders` FOREIGN KEY (`orderId`) REFERENCES `orders` (`idOrder`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_is.ordered_products: ~0 rows (circa)
/*!40000 ALTER TABLE `ordered_products` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordered_products` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_is.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `idOrder` int(11) NOT NULL AUTO_INCREMENT,
  `dataOrder` datetime DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0=entered,1=confirmed,2=printed,3=served',
  `paymentMethod` varchar(32) DEFAULT NULL,
  `extraPayments` float unsigned NOT NULL DEFAULT 0,
  `tablesDetails` varchar(1024) DEFAULT NULL,
  `userId` varchar(50) DEFAULT NULL,
  `tableId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOrder`),
  KEY `FK_orders_tables` (`tableId`),
  KEY `FK_orders_users` (`userId`),
  CONSTRAINT `FK_orders_tables` FOREIGN KEY (`tableId`) REFERENCES `tables` (`tableNumber`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_orders_users` FOREIGN KEY (`userId`) REFERENCES `users` (`username`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_is.orders: ~0 rows (circa)
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_is.products
CREATE TABLE IF NOT EXISTS `products` (
  `idProduct` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` float NOT NULL DEFAULT 0,
  `quantityWarehouse` int(8) NOT NULL DEFAULT 0,
  `quantityGalley` int(8) NOT NULL DEFAULT 0,
  `quantityBar` int(8) NOT NULL DEFAULT 0,
  `flagType` int(2) unsigned NOT NULL DEFAULT 0 COMMENT '0 magazzino, 1 bar, 2 cambusa, 3 bar e cambusa, 4 details,',
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`idProduct`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_is.products: ~65 rows (circa)
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`idProduct`, `name`, `description`, `price`, `quantityWarehouse`, `quantityGalley`, `quantityBar`, `flagType`, `deleted`) VALUES
	(1, 'Vodka Skyy', 'liscia-70 cl', 90, 10, 2, 2, 2, 0),
	(2, 'Campari Soda', '10cl', 4, 50, 0, 0, 1, 0),
	(3, 'Vodka Skyy', 'liscia-1 litro', 0, 12, 0, 0, 0, 0),
	(4, 'Vodka Belvedere', '3 litri', 600, 0, 1, 0, 2, 0),
	(5, 'Gin Bombay', '1 litro', 120, 0, 12, 0, 2, 0),
	(6, 'Vodka Keglevich', 'pesca-1l', 5, 18, 0, 0, 0, 0),
	(7, 'Sipro lime cordial', '75cl', 1, 2, 0, 0, 0, 0),
	(10, 'Vodka Belvedere', '6 litri', 1000, 998, 0, 0, 2, 0),
	(11, 'Berlucchi', '75cl', 60, 0, 0, 0, 2, 0),
	(12, 'Champagne Mumm', '3 litri', 450, 0, 0, 0, 0, 0),
	(13, 'Champagne Mumm', 'gold-75cl', 120, 6, 6, 0, 3, 0),
	(14, 'Vodka Skyy', 'fragola-70cl', 80, 0, 6, 0, 2, 0),
	(15, 'Vodka Skyy', 'pesca-70cl', 80, 0, 6, 0, 2, 0),
	(16, 'Vodka Absolut', 'elyx-70cl', 150, 0, 1, 0, 2, 0),
	(17, 'Vodka Absolut', 'liscia-70cl', 120, 0, 12, 0, 2, 0),
	(18, 'Vodka Belvedere', '70cl', 140, 0, 10, 0, 2, 0),
	(19, 'Vodka Belvedere', '175cl', 300, 0, 0, 0, 2, 0),
	(20, 'Prosecco', 'borgo Molino-75cl', 40, 288, 42, 0, 2, 0),
	(22, 'RedBull', 'prodotto dettaglio', 0, 100, 100, 0, 4, 0),
	(23, 'Tonica', 'prodotto dettaglio', 0, 192, 100, 0, 4, 0),
	(24, 'Lemon', 'prodotto dettaglio', 0, 192, 100, 0, 4, 0),
	(25, 'Coca-Cola', 'prodotto dettaglio', 0, 72, 100, 0, 4, 0),
	(26, 'Champagne Mumm', '1,5 litri', 200, 0, 0, 0, 1, 0),
	(28, 'Gin Bombay ', 'sapphire-1litro', 0, 0, 0, 0, 2, 0),
	(29, 'Vodka Absolut', 'liscia-1litro', 0, 18, 0, 0, 1, 0),
	(30, 'Monin liquore triple sec', 'margarita-1 litro', 0, 4, 0, 0, 0, 0),
	(31, 'Rum Bacardi', 'carta blanca', 0, 12, 0, 0, 2, 0),
	(32, 'Vodka Keglevich', 'fragola-1 litro', 0, 18, 0, 0, 0, 0),
	(33, 'Vodka Keglevich', 'menta-1 litro', 0, 13, 0, 0, 0, 0),
	(34, 'Vodka Keglevich', 'melone-1 litro', 0, 6, 0, 0, 0, 0),
	(35, 'Midori', '70cl', 0, 4, 0, 0, 0, 0),
	(36, 'Passori', '1 litro', 0, 2, 0, 0, 0, 0),
	(37, 'Aperol', 'Barbieri-1litro', 0, 4, 0, 0, 1, 0),
	(38, 'Cinzano Vermouth', ' bianco-1litro', 0, 4, 0, 0, 1, 0),
	(39, 'Cinzano Vermouth', 'rosso-1litro', 0, 4, 0, 0, 0, 0),
	(40, 'Whisky Jamesson Irish', '1litro', 0, 2, 0, 0, 0, 0),
	(41, 'Malibu', '1 litro', 0, 2, 0, 0, 3, 0),
	(42, 'Tequila espolon', 'bianco-70cl', 0, 2, 0, 0, 0, 0),
	(43, 'Tequila espolon', 'reposado-70 cl', 0, 1, 0, 0, 0, 0),
	(44, 'Rum Havana Club Anejo', '3 years-1litro', 0, 4, 0, 0, 0, 0),
	(45, 'Rum Havana Club Anejo', '7 years-70cl', 120, 0, 6, 0, 2, 0),
	(46, 'Kit Havana', '3 years partyano', 0, 1, 0, 0, 0, 0),
	(47, 'Monin Sciroppo', 'fragola-1 litro', 0, 3, 0, 0, 2, 0),
	(48, 'Schweppes', 'tonica-1 litro', 0, 45, 0, 3, 3, 0),
	(49, 'Lemonsoda', '1 litro', 0, 48, 0, 0, 0, 0),
	(50, 'Coca-Cola', '1 litro', 0, 30, 0, 0, 1, 0),
	(51, 'RedBull', '25 cl', 0, 0, 240, 0, 0, 0),
	(52, 'RedBull', 'white-25 cl', 0, 12, 0, 0, 0, 0),
	(53, 'Succo', 'Ananas-1,5 lt', 0, 6, 0, 0, 0, 0),
	(54, 'Succo', 'Arancia-1,5 lt', 0, 2, 0, 4, 1, 0),
	(55, 'Succo', 'pera-20cl', 0, 12, 12, 0, 3, 0),
	(56, 'Ginger Beer', '20cl', 0, 24, 0, 0, 0, 0),
	(57, 'Acqua naturale', 'Mood-50cl', 0, 79, 1, 0, 0, 0),
	(58, 'Acqua minerale', 'Mood-50cl', 89, 13, 4, 7, 1, 0),
	(59, 'Heineken', '33cl', 0, 48, 0, 0, 0, 0),
	(60, 'Tennent\'s', 'super-33cl', 0, 48, 0, 0, 0, 0),
	(61, 'Campari bitter', '1litro', 0, 6, 0, 0, 1, 0),
	(62, 'Jagermeister', '1 litro', 0, 0, 0, 2, 2, 0),
	(63, 'Corte De Roberto', '75 cl', 51, 0, 30, 0, 1, 0),
	(64, 'Gin Buldog', '70cl', 130, 0, 6, 0, 2, 0),
	(65, 'Gin hendrick\'s', '1 litro', 150, 0, 6, 0, 2, 0),
	(66, 'Schweppes Tonica', '17,5 cl', 0, 192, 100, 0, 0, 0),
	(67, 'Schweppes Lemon', '17,5 cl', 0, 192, 100, 0, 0, 0),
	(68, 'Coca-Cola', '33cl', 0, 72, 100, 0, 0, 0),
	(69, 'TESTTTT', 'TESTTTT', 0, 0, 97, 0, 2, 0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_is.reservation_options
CREATE TABLE IF NOT EXISTS `reservation_options` (
  `tablePrice` float DEFAULT 0,
  `luxusTablePrice` float DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_is.reservation_options: ~0 rows (circa)
/*!40000 ALTER TABLE `reservation_options` DISABLE KEYS */;
INSERT INTO `reservation_options` (`tablePrice`, `luxusTablePrice`) VALUES
	(25, 30);
/*!40000 ALTER TABLE `reservation_options` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_is.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `idRole` int(1) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) NOT NULL,
  PRIMARY KEY (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_is.roles: ~3 rows (circa)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`idRole`, `roleName`) VALUES
	(1, 'smartphone'),
	(2, 'tablet'),
	(3, 'admin');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_is.tables
CREATE TABLE IF NOT EXISTS `tables` (
  `tableNumber` int(11) NOT NULL,
  `tableName` varchar(64) DEFAULT 'no sponsor',
  `capacity` int(11) NOT NULL DEFAULT 0,
  `isAssigned` tinyint(4) NOT NULL DEFAULT 0,
  `reservationName` varchar(50) DEFAULT NULL,
  `peopleNumber` int(11) DEFAULT NULL,
  `budget` float NOT NULL DEFAULT 0,
  `isLuxus` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`tableNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_is.tables: ~37 rows (circa)
/*!40000 ALTER TABLE `tables` DISABLE KEYS */;
INSERT INTO `tables` (`tableNumber`, `tableName`, `capacity`, `isAssigned`, `reservationName`, `peopleNumber`, `budget`, `isLuxus`) VALUES
	(1, 'I Maschi e le Femmine', 30, 0, 'edited reservation name', 167, 342, 0),
	(2, 'I Maschi e le Femmine', 30, 0, '', 0, 0, 0),
	(3, 'no sponsor', 30, 0, '', 0, 0, 0),
	(4, 'Gammauto', 30, 0, '', 0, 0, 0),
	(5, 'no sponsor', 30, 0, '', 0, 0, 1),
	(6, 'The man salon', 30, 0, '', 0, 0, 1),
	(7, 'no sponsor', 30, 0, '', 0, 0, 1),
	(8, 'Coppe e Premi', 30, 0, '', 0, 0, 1),
	(9, 'no sponsor', 30, 0, '', 0, 0, 1),
	(10, 'TessArredo', 30, 0, '', 0, 0, 1),
	(11, 'no sponsor', 30, 1, 'Pagliaro Vincenza', 33, 825, 0),
	(12, 'no sponsor', 30, 0, '', 3, 75, 0),
	(13, 'no sponsor', 30, 0, 'prenotazioneFinal', 23, 575, 0),
	(14, 'no sponsor', 30, 1, 'Pagliaro Vincenzo', 35, 875, 0),
	(15, 'Miwa Energia', 30, 0, '', 0, 0, 0),
	(16, 'no sponsor', 30, 0, '', 0, 0, 0),
	(17, 'no sponsor', 30, 0, '', 0, 0, 0),
	(18, 'no sponsor', 30, 0, '', 0, 0, 0),
	(19, 'Italfrom', 30, 0, '', 0, 0, 0),
	(20, 'no sponsor', 30, 0, '', 0, 0, 0),
	(21, 'no sponsor', 30, 0, '', 0, 0, 0),
	(22, 'no sponsor', 30, 0, '', 0, 0, 0),
	(23, 'no sponsor', 30, 0, '', 0, 0, 0),
	(24, 'no sponsor', 30, 0, '', 0, 0, 0),
	(25, 'no sponsor', 30, 1, 'Pagliaro Vincenzo', 35, 875, 0),
	(26, 'no sponsor', 30, 0, '', 0, 0, 0),
	(34, 'no sponsor', 30, 0, '', 0, 0, 0),
	(35, 'no sponsor', 30, 0, '', 0, 0, 0),
	(36, 'no sponsor', 30, 0, '', 0, 0, 0),
	(37, 'no sponsor', 30, 0, '', 0, 0, 0),
	(38, 'no sponsor', 30, 0, '', 0, 0, 0),
	(39, 'no sponsor', 30, 0, '', 0, 0, 0),
	(40, 'no sponsor', 30, 0, '', 0, 0, 0),
	(41, 'no sponsor', 30, 0, '', 0, 0, 0),
	(42, 'no sponsor', 30, 0, '', 0, 0, 0),
	(43, 'no sponsor', 30, 0, '', 0, 0, 0),
	(44, 'no sponsor', 30, 0, '', 0, 0, 0);
/*!40000 ALTER TABLE `tables` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_is.tables_details
CREATE TABLE IF NOT EXISTS `tables_details` (
  `idDetail` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`idDetail`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_is.tables_details: ~3 rows (circa)
/*!40000 ALTER TABLE `tables_details` DISABLE KEYS */;
INSERT INTO `tables_details` (`idDetail`, `description`) VALUES
	(1, 'Più bicchieri'),
	(2, 'Pulire tavolo'),
	(3, 'Più Ghiaccio');
/*!40000 ALTER TABLE `tables_details` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_is.users
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `role` int(1) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_users_roles` (`role`),
  CONSTRAINT `FK_users_roles` FOREIGN KEY (`role`) REFERENCES `roles` (`idRole`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_is.users: ~6 rows (circa)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`username`, `password`, `name`, `role`) VALUES
	('admin', 'mythos2019', '', 3),
	('cassiere1', 'cass1', '', 2),
	('cassiere2', 'cass2', '', 2),
	('operatore1', 'op1', '', 1),
	('operatore2', 'op2', '', 1),
	('operatore3', 'op3', '', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
