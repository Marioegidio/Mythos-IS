-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              10.1.38-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dump della struttura del database mythos_pr_is
CREATE DATABASE IF NOT EXISTS `mythos_pr_is` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mythos_pr_is`;

-- Dump della struttura di tabella mythos_pr_is.client_lists
CREATE TABLE IF NOT EXISTS `client_lists` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `added_date` varchar(50) DEFAULT NULL,
  `entered` tinyint(4) DEFAULT '0',
  `pr` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  KEY `FK_lists_pr` (`pr`),
  CONSTRAINT `FK_lists_pr` FOREIGN KEY (`pr`) REFERENCES `pr` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_pr_is.client_lists: ~20 rows (circa)
DELETE FROM `client_lists`;
/*!40000 ALTER TABLE `client_lists` DISABLE KEYS */;
INSERT INTO `client_lists` (`client_id`, `name`, `surname`, `added_date`, `entered`, `pr`) VALUES
	(23, 'Francesco', 'Abate', '14/01/2020 18:03:13', 0, 'pr1'),
	(24, 'Saverio', 'De Stefano', '14/01/2020 18:03:31', 0, 'pr1'),
	(25, 'Marco', 'Dello Buono', '14/01/2020 18:03:38', 0, 'pr1'),
	(26, 'Vincenzo', 'Pagliaro', '14/01/2020 18:03:46', 0, 'pr1'),
	(27, 'Mario', 'Egidio', '14/01/2020 18:03:52', 0, 'pr1'),
	(28, 'Rosario', 'Gagliardi', '14/01/2020 18:04:01', 0, 'pr1'),
	(29, 'Vincenzo', 'Fabiano', '14/01/2020 18:04:11', 0, 'pr1');
/*!40000 ALTER TABLE `client_lists` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_pr_is.options
CREATE TABLE IF NOT EXISTS `options` (
  `flagList` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_pr_is.options: ~1 rows (circa)
DELETE FROM `options`;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` (`flagList`) VALUES
	(1);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;

-- Dump della struttura di tabella mythos_pr_is.pr
CREATE TABLE IF NOT EXISTS `pr` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella mythos_pr_is.pr: ~2 rows (circa)
DELETE FROM `pr`;
/*!40000 ALTER TABLE `pr` DISABLE KEYS */;
INSERT INTO `pr` (`username`, `password`, `name`, `lastname`) VALUES
	('micheled\'addona', 'm123', 'michele', 'd\'addona'),
	('pr1', '1', 'perre1', 'p1'),
	('pr2', '2', 'perre2', 'p2');
/*!40000 ALTER TABLE `pr` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
