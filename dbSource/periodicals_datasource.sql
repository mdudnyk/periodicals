-- MySQL Script generated by MySQL Workbench
-- Tue Nov  8 21:10:30 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema periodicals_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema periodicals_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `periodicals_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `periodicals_db` ;

-- -----------------------------------------------------
-- Table `periodicals_db`.`locale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`locale` (
  `short_name_id` VARCHAR(3) NOT NULL,
  `lang_name_original` VARCHAR(50) NOT NULL,
  `currency_name` VARCHAR(3) NOT NULL DEFAULT 'UAH',
  `flag_icon_url` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`short_name_id`),
  UNIQUE INDEX `code_UNIQUE` (`short_name_id` ASC) VISIBLE,
  UNIQUE INDEX `lang_name_original_UNIQUE` (`lang_name_original` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `locale_id` VARCHAR(3) NULL,
  `firstname` VARCHAR(50) NOT NULL,
  `lastname` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `role` ENUM('CUSTOMER', 'ADMIN') NULL DEFAULT 'CUSTOMER',
  `balance` INT NULL DEFAULT 0,
  `is_blocked` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_locale_idx` (`locale_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_locale`
    FOREIGN KEY (`locale_id`)
    REFERENCES `periodicals_db`.`locale` (`short_name_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`address` (
  `user_id` INT NOT NULL,
  `index` INT NOT NULL,
  `city` VARCHAR(50) NOT NULL,
  `street` VARCHAR(50) NOT NULL,
  `build` VARCHAR(10) NOT NULL,
  `apartment` VARCHAR(20) NULL DEFAULT 'private house',
  INDEX `fk_addess_user_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_address_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `periodicals_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`topic` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`periodical`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`periodical` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `topic_id` INT NULL,
  `title` VARCHAR(50) NOT NULL,
  `title_img_name` VARCHAR(100) NULL,
  `price` INT NOT NULL,
  `publication_frequency` JSON NOT NULL,
  `is_active` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE,
  INDEX `fk_periodical_topic_idx` (`topic_id` ASC) VISIBLE,
  CONSTRAINT `fk_periodical_topic`
    FOREIGN KEY (`topic_id`)
    REFERENCES `periodicals_db`.`topic` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`periodical_translate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`periodical_translate` (
  `periodical_id` INT NOT NULL AUTO_INCREMENT,
  `locale_id` VARCHAR(3) NOT NULL,
  `publishing_country` VARCHAR(50) NOT NULL,
  `publishing_lang` VARCHAR(50) NOT NULL,
  `description` TEXT(1000) NOT NULL,
  PRIMARY KEY (`periodical_id`, `locale_id`),
  INDEX `fk_periodical_translate_periodical_idx` (`periodical_id` ASC) INVISIBLE,
  INDEX `fk_periodical_translate_locale_idx` (`locale_id` ASC) VISIBLE,
  CONSTRAINT `fk_periodical_translate_periodical`
    FOREIGN KEY (`periodical_id`)
    REFERENCES `periodicals_db`.`periodical` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_periodical_translate_locale`
    FOREIGN KEY (`locale_id`)
    REFERENCES `periodicals_db`.`locale` (`short_name_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`topic_translate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`topic_translate` (
  `topic_id` INT NOT NULL AUTO_INCREMENT,
  `locale_id` VARCHAR(3) NOT NULL,
  `name` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  PRIMARY KEY (`topic_id`, `locale_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_topic_translate_locale_idx` (`locale_id` ASC) VISIBLE,
  CONSTRAINT `fk_topic_translate_topic`
    FOREIGN KEY (`topic_id`)
    REFERENCES `periodicals_db`.`topic` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_topic_translate_locale`
    FOREIGN KEY (`locale_id`)
    REFERENCES `periodicals_db`.`locale` (`short_name_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`subscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`subscription` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `periodical_id` INT NULL,
  `periodical_title` VARCHAR(50) NOT NULL,
  `total_price` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  `expired_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_subscription_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_subscription_periodical_idx` (`periodical_id` ASC) VISIBLE,
  CONSTRAINT `fk_subscription_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `periodicals_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subscription_periodical`
    FOREIGN KEY (`periodical_id`)
    REFERENCES `periodicals_db`.`periodical` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`release_calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`release_calendar` (
  `periodical_id` INT NOT NULL,
  `year` INT NOT NULL,
  `month` JSON NOT NULL,
  PRIMARY KEY (`periodical_id`, `year`),
  INDEX `fk_release_calendar_periodical_idx` (`periodical_id` ASC) INVISIBLE,
  CONSTRAINT `fk_release_calendar_periodical`
    FOREIGN KEY (`periodical_id`)
    REFERENCES `periodicals_db`.`periodical` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`subscription_calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`subscription_calendar` (
  `subscription_id` INT NOT NULL,
  `year` INT NOT NULL,
  `month` JSON NOT NULL,
  PRIMARY KEY (`subscription_id`, `year`),
  INDEX `fk_subscription_calendar_subscription_idx` (`subscription_id` ASC) INVISIBLE,
  CONSTRAINT `fk_subscription_calendar_subscription`
    FOREIGN KEY (`subscription_id`)
    REFERENCES `periodicals_db`.`subscription` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals_db`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_db`.`payment` (
  `id` VARCHAR(25) NOT NULL,
  `user_id` INT NULL,
  `amount` INT NOT NULL,
  `status` ENUM('NOT_COMPLETED', 'SUCCESS', 'FAILURE') NOT NULL DEFAULT 'NOT_COMPLETED',
  `created_at` TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  PRIMARY KEY (`id`),
  INDEX `fk_payment_user_idx` (`user_id` ASC) INVISIBLE,
  CONSTRAINT `fk_payment_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `periodicals_db`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
