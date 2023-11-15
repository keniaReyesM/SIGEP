-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema sigep_v1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sigep_v1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sigep_v1` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `sigep_v1`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`cliente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre_pagina` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `razon_social` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `servidor_alojamiento` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
  `empresa_id` INT NOT NULL,
  PRIMARY KEY BTREE (`id`),
  INDEX `fk_cliente_empresa1_idx` (`empresa_id` ASC) VISIBLE,
  CONSTRAINT `fk_cliente_empresa1`
    FOREIGN KEY (`empresa_id`)
    REFERENCES `mydb`.`empresa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`persona` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fecha_nacimiento` DATETIME NOT NULL,
  `nombre` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `primer_apellido` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `segundo_apellido` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
  PRIMARY KEY BTREE (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`correo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`correo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cliente_id` BIGINT(20) NULL DEFAULT NULL,
  `correo` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `persona_id` BIGINT(20) NULL DEFAULT NULL,
  `principal` BIT(1) NOT NULL,
  PRIMARY KEY BTREE (`id`),
  INDEX `FK_ibbsg46ypugrhfheob5su4qry` USING BTREE (`cliente_id`) VISIBLE,
  INDEX `FK_ngfx1pwxq028xkca40wr0j1rd` USING BTREE (`persona_id`) VISIBLE,
  CONSTRAINT `FK_ibbsg46ypugrhfheob5su4qry`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `sigep_v1`.`cliente` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_ngfx1pwxq028xkca40wr0j1rd`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sigep_v1`.`persona` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`direccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`direccion` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `calle` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `cliente_id` BIGINT(20) NULL DEFAULT NULL,
  `colonia` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `estado` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `municipio` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `numero_exterior` VARCHAR(5) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `numero_interior` VARCHAR(5) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `persona_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_6foldly1k0ib1ls5fqx82by66` USING BTREE (`cliente_id`) VISIBLE,
  UNIQUE INDEX `UK_6py1bql24hnawqudq2hh2gcst` USING BTREE (`persona_id`) VISIBLE,
  CONSTRAINT `FK_6foldly1k0ib1ls5fqx82by66`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `sigep_v1`.`cliente` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_6py1bql24hnawqudq2hh2gcst`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sigep_v1`.`persona` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`tipo_telefono`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`tipo_telefono` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(15) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_1r6gnmo3x0piso5du9damav99` USING BTREE (`nombre`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`telefono`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`telefono` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cliente_id` BIGINT(20) NULL DEFAULT NULL,
  `persona_id` BIGINT(20) NULL DEFAULT NULL,
  `principal` BIT(1) NOT NULL,
  `telefono` VARCHAR(15) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `tipo_telefono_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_m1mkusbp2aa7v1h2avs22vry7` USING BTREE (`cliente_id`) VISIBLE,
  INDEX `FK_29pktthbkxi6a17wlqfmeo8u5` USING BTREE (`persona_id`) VISIBLE,
  INDEX `FK_ru1rvht45l196g8fyelcv2aj5` USING BTREE (`tipo_telefono_id`) VISIBLE,
  CONSTRAINT `FK_29pktthbkxi6a17wlqfmeo8u5`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sigep_v1`.`persona` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_m1mkusbp2aa7v1h2avs22vry7`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `sigep_v1`.`cliente` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_ru1rvht45l196g8fyelcv2aj5`
    FOREIGN KEY (`tipo_telefono_id`)
    REFERENCES `sigep_v1`.`tipo_telefono` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `mydb`.`empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`empresa` (
  `id` BIGINT(20) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `razon social` VARCHAR(45) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `fecha_licencia` DATETIME NOT NULL,
  `correo_id` BIGINT(20) NOT NULL,
  `direccion_id` BIGINT(20) NOT NULL,
  `telefono_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_empresa_correo_idx` (`correo_id` ASC) VISIBLE,
  INDEX `fk_empresa_direccion1_idx` (`direccion_id` ASC) VISIBLE,
  INDEX `fk_empresa_telefono1_idx` (`telefono_id` ASC) VISIBLE,
  CONSTRAINT `fk_empresa_correo`
    FOREIGN KEY (`correo_id`)
    REFERENCES `sigep_v1`.`correo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_empresa_direccion1`
    FOREIGN KEY (`direccion_id`)
    REFERENCES `sigep_v1`.`direccion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_empresa_telefono1`
    FOREIGN KEY (`telefono_id`)
    REFERENCES `sigep_v1`.`telefono` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `sigep_v1` ;

-- -----------------------------------------------------
-- Table `sigep_v1`.`area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`area` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `empresa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_qucfcq1o4drbmk77tx9x6vyfp` USING BTREE (`nombre`) VISIBLE,
  INDEX `fk_area_empresa1_idx` (`empresa_id` ASC) VISIBLE,
  CONSTRAINT `fk_area_empresa1`
    FOREIGN KEY (`empresa_id`)
    REFERENCES `mydb`.`empresa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`tipo_estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`tipo_estado` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_1up7k8lmnyx2fvi30wdjglmlt` USING BTREE (`nombre`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`estado` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `tipo_estado_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_qqgq56nv9wsmc666fnp16m13r` USING BTREE (`tipo_estado_id`) VISIBLE,
  CONSTRAINT `FK_qqgq56nv9wsmc666fnp16m13r`
    FOREIGN KEY (`tipo_estado_id`)
    REFERENCES `sigep_v1`.`tipo_estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`usuario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `account_expired` BIT(1) NOT NULL,
  `account_locked` BIT(1) NOT NULL,
  `email` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `enabled` BIT(1) NOT NULL,
  `password` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `password_expired` BIT(1) NOT NULL,
  `username` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_863n1y3x0jalatoir4325ehal` USING BTREE (`username`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`puesto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`puesto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_1ag0mwfdwdgbjs02osdc2sij2` USING BTREE (`nombre`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`empleado` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `estado_id` BIGINT(20) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `persona_id` BIGINT(20) NOT NULL,
  `puesto_id` BIGINT(20) NOT NULL,
  `usuario_id` BIGINT(20) NOT NULL,
  `empresa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_pjv6m14uotxloxlv04buynykk` USING BTREE (`persona_id`) VISIBLE,
  UNIQUE INDEX `UK_6ff36el6hfqwrtnvk0y9jd6sh` USING BTREE (`usuario_id`) VISIBLE,
  INDEX `FK_2itxl5bv1nel16h9srpi757au` USING BTREE (`estado_id`) VISIBLE,
  INDEX `FK_ljsevlk2n94afkg895c86rbu0` USING BTREE (`puesto_id`) VISIBLE,
  INDEX `fk_empleado_empresa1_idx` (`empresa_id` ASC) VISIBLE,
  CONSTRAINT `FK_2itxl5bv1nel16h9srpi757au`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_6ff36el6hfqwrtnvk0y9jd6sh`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `sigep_v1`.`usuario` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_ljsevlk2n94afkg895c86rbu0`
    FOREIGN KEY (`puesto_id`)
    REFERENCES `sigep_v1`.`puesto` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_pjv6m14uotxloxlv04buynykk`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sigep_v1`.`persona` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_empleado_empresa1`
    FOREIGN KEY (`empresa_id`)
    REFERENCES `mydb`.`empresa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`area_empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`area_empleado` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `area_id` BIGINT(20) NOT NULL,
  `empleado_id` BIGINT(20) NOT NULL,
  `empleado_asignacion_id` BIGINT(20) NOT NULL,
  `estado_id` BIGINT(20) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_l7j9rg6jjdgrpvxfxt1m3xp05` USING BTREE (`area_id`) VISIBLE,
  INDEX `FK_3nvs6h93sg69k78vc0id01kuf` USING BTREE (`empleado_id`) VISIBLE,
  INDEX `FK_1jqmnvaa0tk7pqu5h8mlv1y0h` USING BTREE (`empleado_asignacion_id`) VISIBLE,
  INDEX `FK_buyvu9a9rhg4bvcw0b7glkfmh` USING BTREE (`estado_id`) VISIBLE,
  CONSTRAINT `FK_1jqmnvaa0tk7pqu5h8mlv1y0h`
    FOREIGN KEY (`empleado_asignacion_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_3nvs6h93sg69k78vc0id01kuf`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_buyvu9a9rhg4bvcw0b7glkfmh`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_l7j9rg6jjdgrpvxfxt1m3xp05`
    FOREIGN KEY (`area_id`)
    REFERENCES `sigep_v1`.`area` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`tipo_privacidad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`tipo_privacidad` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_rag9hnjuj8y79goxqqb8oxk46` USING BTREE (`nombre`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`categoria` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `nombre` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `empresa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_35t4wyxqrevf09uwx9e9p6o75` USING BTREE (`nombre`) VISIBLE,
  INDEX `fk_categoria_empresa1_idx` (`empresa_id` ASC) VISIBLE,
  CONSTRAINT `fk_categoria_empresa1`
    FOREIGN KEY (`empresa_id`)
    REFERENCES `mydb`.`empresa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`proyecto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `categoria_id` BIGINT(20) NOT NULL,
  `clave` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `descripcion` VARCHAR(200) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `empleado_id` BIGINT(20) NOT NULL,
  `estado_id` BIGINT(20) NOT NULL,
  `fecha_fin` DATETIME NULL DEFAULT NULL,
  `fecha_inicio` DATETIME NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `nombre` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `tipo_privacidad_id` BIGINT(20) NOT NULL,
  `empresa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_18l7fy6wsr9ww8on1ebcujbt9` USING BTREE (`clave`) VISIBLE,
  INDEX `FK_ppso8ystxhm6d5hvpfdq2a4dr` USING BTREE (`categoria_id`) VISIBLE,
  INDEX `FK_ghw4p8ya28x8l42l14sj40mnf` USING BTREE (`empleado_id`) VISIBLE,
  INDEX `FK_rikdhsba4oqhhbxonm6dvhkhe` USING BTREE (`estado_id`) VISIBLE,
  INDEX `FK_4u0957pesgcnp5rk9yhv8cpgs` USING BTREE (`tipo_privacidad_id`) VISIBLE,
  INDEX `fk_proyecto_empresa1_idx` (`empresa_id` ASC) VISIBLE,
  CONSTRAINT `FK_4u0957pesgcnp5rk9yhv8cpgs`
    FOREIGN KEY (`tipo_privacidad_id`)
    REFERENCES `sigep_v1`.`tipo_privacidad` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_ghw4p8ya28x8l42l14sj40mnf`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_ppso8ystxhm6d5hvpfdq2a4dr`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `sigep_v1`.`categoria` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_rikdhsba4oqhhbxonm6dvhkhe`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_proyecto_empresa1`
    FOREIGN KEY (`empresa_id`)
    REFERENCES `mydb`.`empresa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`permiso_proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`permiso_proyecto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(150) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `nombre` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`asignacion_proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`asignacion_proyecto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `empleado_id` BIGINT(20) NOT NULL,
  `empleado_asignacion_id` BIGINT(20) NOT NULL,
  `estado_id` BIGINT(20) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `permiso_proyecto_id` BIGINT(20) NOT NULL,
  `proyecto_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_92eq3ut907wa22pw0oex7n9fy` USING BTREE (`empleado_id`) VISIBLE,
  INDEX `FK_7wbvn0whig1g1me4jt60b68th` USING BTREE (`empleado_asignacion_id`) VISIBLE,
  INDEX `FK_k9cv4m59catue30ast7qvljtc` USING BTREE (`estado_id`) VISIBLE,
  INDEX `FK_jbfelyhc7ql9b5etj7bnsbmpr` USING BTREE (`permiso_proyecto_id`) VISIBLE,
  INDEX `FK_f1sasytmfb3uf9r0uqwbbpchg` USING BTREE (`proyecto_id`) VISIBLE,
  CONSTRAINT `FK_7wbvn0whig1g1me4jt60b68th`
    FOREIGN KEY (`empleado_asignacion_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_92eq3ut907wa22pw0oex7n9fy`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_f1sasytmfb3uf9r0uqwbbpchg`
    FOREIGN KEY (`proyecto_id`)
    REFERENCES `sigep_v1`.`proyecto` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_jbfelyhc7ql9b5etj7bnsbmpr`
    FOREIGN KEY (`permiso_proyecto_id`)
    REFERENCES `sigep_v1`.`permiso_proyecto` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_k9cv4m59catue30ast7qvljtc`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`tipo_tarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`tipo_tarea` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_k3bi3911eflxj54g9dx6a0nms` USING BTREE (`nombre`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`color`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`color` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `nombre` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY BTREE (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`nivel_prioridad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`nivel_prioridad` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `prioridad` INT(11) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_sxbcbheg1ytf2x76bj75rxp0` USING BTREE (`nombre`) VISIBLE,
  UNIQUE INDEX `UK_9kdj6i0joqvb1vtyrfo9n4gip` USING BTREE (`prioridad`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`tarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`tarea` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `clave` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `color_id` BIGINT(20) NOT NULL,
  `descripcion` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `empleado_id` BIGINT(20) NOT NULL,
  `estado_id` BIGINT(20) NOT NULL,
  `fecha_hora_fin` DATETIME NULL DEFAULT NULL,
  `fecha_hora_inicio` DATETIME NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `nivel_prioridad_id` BIGINT(20) NOT NULL,
  `nombre` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `orden` INT(11) NOT NULL,
  `porcentaje_avance` VARCHAR(10) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `proyecto_id` BIGINT(20) NOT NULL,
  `tarea_id` BIGINT(20) NULL DEFAULT NULL,
  `tipo_tarea_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_2ilf98xrr09nfjgeok6v1tna0` USING BTREE (`clave`) VISIBLE,
  INDEX `FK_6dko02o4m2vuwtk6xf5celmx3` USING BTREE (`color_id`) VISIBLE,
  INDEX `FK_tj7cxfhvsunpjhy5fmwo5tsg3` USING BTREE (`empleado_id`) VISIBLE,
  INDEX `FK_64o9bbqdidnhsc74dvrj89exf` USING BTREE (`estado_id`) VISIBLE,
  INDEX `FK_cj1eki5hyoqbc7hg1yo4wruj4` USING BTREE (`nivel_prioridad_id`) VISIBLE,
  INDEX `FK_oo24nvonl6jp192vteijgp62o` USING BTREE (`proyecto_id`) VISIBLE,
  INDEX `FK_fwpsm76047am54ifx3ssbdvvs` USING BTREE (`tarea_id`) VISIBLE,
  INDEX `FK_6bp65i7d92asl8idfei8xb745` USING BTREE (`tipo_tarea_id`) VISIBLE,
  CONSTRAINT `FK_64o9bbqdidnhsc74dvrj89exf`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_6bp65i7d92asl8idfei8xb745`
    FOREIGN KEY (`tipo_tarea_id`)
    REFERENCES `sigep_v1`.`tipo_tarea` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_6dko02o4m2vuwtk6xf5celmx3`
    FOREIGN KEY (`color_id`)
    REFERENCES `sigep_v1`.`color` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_cj1eki5hyoqbc7hg1yo4wruj4`
    FOREIGN KEY (`nivel_prioridad_id`)
    REFERENCES `sigep_v1`.`nivel_prioridad` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_fwpsm76047am54ifx3ssbdvvs`
    FOREIGN KEY (`tarea_id`)
    REFERENCES `sigep_v1`.`tarea` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_oo24nvonl6jp192vteijgp62o`
    FOREIGN KEY (`proyecto_id`)
    REFERENCES `sigep_v1`.`proyecto` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_tj7cxfhvsunpjhy5fmwo5tsg3`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`asignacion_tarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`asignacion_tarea` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `empleado_id` BIGINT(20) NOT NULL,
  `empleado_asignacion_id` BIGINT(20) NOT NULL,
  `estado_id` BIGINT(20) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `tarea_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_b60kedf4ih6rhoeo0l8odgp52` USING BTREE (`empleado_id`) VISIBLE,
  INDEX `FK_186rtkq53x2riyxptiwai2m3` USING BTREE (`empleado_asignacion_id`) VISIBLE,
  INDEX `FK_fxibsw83k7xnmyu7573y5q3l7` USING BTREE (`estado_id`) VISIBLE,
  INDEX `FK_gyrb6wqt19q22hhqra0y5jxxe` USING BTREE (`tarea_id`) VISIBLE,
  CONSTRAINT `FK_186rtkq53x2riyxptiwai2m3`
    FOREIGN KEY (`empleado_asignacion_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_b60kedf4ih6rhoeo0l8odgp52`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_fxibsw83k7xnmyu7573y5q3l7`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_gyrb6wqt19q22hhqra0y5jxxe`
    FOREIGN KEY (`tarea_id`)
    REFERENCES `sigep_v1`.`tarea` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`cambio_estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`cambio_estado` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `empleado_id` BIGINT(20) NOT NULL,
  `estado_anterior_id` BIGINT(20) NOT NULL,
  `estado_nuevo_id` BIGINT(20) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `tarea_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_t7ngfjyqb9429y35o753ort6s` USING BTREE (`empleado_id`) VISIBLE,
  INDEX `FK_h9ictty7i0q36x62q4saomun8` USING BTREE (`estado_anterior_id`) VISIBLE,
  INDEX `FK_1hrwglc7dal733mqetyi42g9y` USING BTREE (`estado_nuevo_id`) VISIBLE,
  INDEX `FK_kp2usvpckkmj7srqwca3gnef0` USING BTREE (`tarea_id`) VISIBLE,
  CONSTRAINT `FK_1hrwglc7dal733mqetyi42g9y`
    FOREIGN KEY (`estado_nuevo_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_h9ictty7i0q36x62q4saomun8`
    FOREIGN KEY (`estado_anterior_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_kp2usvpckkmj7srqwca3gnef0`
    FOREIGN KEY (`tarea_id`)
    REFERENCES `sigep_v1`.`tarea` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_t7ngfjyqb9429y35o753ort6s`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`cambio_porcentaje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`cambio_porcentaje` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `empleado_id` BIGINT(20) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `porcenjate_anterior` VARCHAR(10) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `porcentaje_nuevo` VARCHAR(10) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `tarea_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_a3xyquyv89miox8ieto5qbwe0` USING BTREE (`empleado_id`) VISIBLE,
  INDEX `FK_rsrhikstpvkb2fi67wr1div13` USING BTREE (`tarea_id`) VISIBLE,
  CONSTRAINT `FK_a3xyquyv89miox8ieto5qbwe0`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_rsrhikstpvkb2fi67wr1div13`
    FOREIGN KEY (`tarea_id`)
    REFERENCES `sigep_v1`.`tarea` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`cancelacion_tarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`cancelacion_tarea` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `empleado_id` BIGINT(20) NOT NULL,
  `estado_id` BIGINT(20) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `motivo` VARCHAR(500) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `tarea_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_d970vhr1jf34tld4ddiynraon` USING BTREE (`empleado_id`) VISIBLE,
  INDEX `FK_8r0iy5hr01797mg2rhpnp5g7r` USING BTREE (`estado_id`) VISIBLE,
  INDEX `FK_ekiiy7c7kv95ihxdxixyyn1vr` USING BTREE (`tarea_id`) VISIBLE,
  CONSTRAINT `FK_8r0iy5hr01797mg2rhpnp5g7r`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sigep_v1`.`estado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_d970vhr1jf34tld4ddiynraon`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_ekiiy7c7kv95ihxdxixyyn1vr`
    FOREIGN KEY (`tarea_id`)
    REFERENCES `sigep_v1`.`tarea` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`cliente_foto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`cliente_foto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cliente_id` BIGINT(20) NOT NULL,
  `foto` TINYBLOB NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_l6tijxybeo4uk75y7nsgwhfqy` USING BTREE (`cliente_id`) VISIBLE,
  CONSTRAINT `FK_l6tijxybeo4uk75y7nsgwhfqy`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `sigep_v1`.`cliente` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`cliente_proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`cliente_proyecto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cliente_id` BIGINT(20) NOT NULL,
  `proyecto_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `unique_proyecto_id` USING BTREE (`cliente_id`, `proyecto_id`) VISIBLE,
  INDEX `FK_q1p7xsknj632kojov0ube8j60` USING BTREE (`proyecto_id`) VISIBLE,
  CONSTRAINT `FK_ehq6cv3d1d8h42nvhlcygtsm5`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `sigep_v1`.`cliente` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_q1p7xsknj632kojov0ube8j60`
    FOREIGN KEY (`proyecto_id`)
    REFERENCES `sigep_v1`.`proyecto` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`dia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`dia` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(10) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_ursg31vmjxkpv46or7lb8o74` USING BTREE (`nombre`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`horario_laboral`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`horario_laboral` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `dia_id` BIGINT(20) NOT NULL,
  `empleado_id` BIGINT(20) NOT NULL,
  `hora_fin` DATETIME NOT NULL,
  `hora_inicio` DATETIME NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `FK_sydcpja516lvjkxfbkfwe8lvf` USING BTREE (`dia_id`) VISIBLE,
  INDEX `FK_nxexann76tft0oilxxkbgo4pu` USING BTREE (`empleado_id`) VISIBLE,
  CONSTRAINT `FK_nxexann76tft0oilxxkbgo4pu`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sigep_v1`.`empleado` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_sydcpja516lvjkxfbkfwe8lvf`
    FOREIGN KEY (`dia_id`)
    REFERENCES `sigep_v1`.`dia` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`persona_foto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`persona_foto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `foto` TINYBLOB NOT NULL,
  `persona_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_h7p5fu1bf4caj7ugsuei2jl04` USING BTREE (`persona_id`) VISIBLE,
  CONSTRAINT `FK_h7p5fu1bf4caj7ugsuei2jl04`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sigep_v1`.`persona` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`recurso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`recurso` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `config_attribute` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `http_method` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
  `url` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `unique_url` USING BTREE (`http_method`, `url`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`representante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`representante` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cliente_id` BIGINT(20) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `persona_id` BIGINT(20) NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `unique_persona_id` USING BTREE (`cliente_id`, `persona_id`) VISIBLE,
  INDEX `FK_5670ka4231o31xd0trr47tcs3` USING BTREE (`persona_id`) VISIBLE,
  CONSTRAINT `FK_5670ka4231o31xd0trr47tcs3`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sigep_v1`.`persona` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_7i96omyl3n06buco5ckwd272v`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `sigep_v1`.`cliente` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`rol` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `descripcion` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `UK_pbdeb23es4jm3il5dvs9ec1jb` USING BTREE (`authority`) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `sigep_v1`.`usuario_rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigep_v1`.`usuario_rol` (
  `usuario_id` BIGINT(20) NOT NULL,
  `rol_id` BIGINT(20) NOT NULL,
  PRIMARY KEY BTREE (`usuario_id`, `rol_id`),
  INDEX `FK_5gtipd65p6pda9ltx23lm68ge` USING BTREE (`rol_id`) VISIBLE,
  CONSTRAINT `FK_5gtipd65p6pda9ltx23lm68ge`
    FOREIGN KEY (`rol_id`)
    REFERENCES `sigep_v1`.`rol` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_91qmacuyat735y6p88fsblnx5`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `sigep_v1`.`usuario` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
