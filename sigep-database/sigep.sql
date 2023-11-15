/*
Navicat MySQL Data Transfer

Source Server         : MySQL localhost
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : sigep

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2023-01-12 19:59:08
*/

SET FOREIGN_KEY_CHECKS=0;


CREATE DATABASE IF NOT EXISTS sigep;

use sigep;
-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empresa_id` bigint NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_nombre` (`empresa_id`,`nombre`),
  CONSTRAINT `FK_3h26ciu2a69k810gedhiyd25y` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for area_empleado
-- ----------------------------
DROP TABLE IF EXISTS `area_empleado`;
CREATE TABLE `area_empleado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_id` bigint NOT NULL,
  `empleado_id` bigint NOT NULL,
  `empleado_asignacion_id` bigint NOT NULL,
  `estado_id` bigint NOT NULL,
  `fecha_registro` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_l7j9rg6jjdgrpvxfxt1m3xp05` (`area_id`),
  KEY `FK_3nvs6h93sg69k78vc0id01kuf` (`empleado_id`),
  KEY `FK_1jqmnvaa0tk7pqu5h8mlv1y0h` (`empleado_asignacion_id`),
  KEY `FK_buyvu9a9rhg4bvcw0b7glkfmh` (`estado_id`),
  CONSTRAINT `FK_1jqmnvaa0tk7pqu5h8mlv1y0h` FOREIGN KEY (`empleado_asignacion_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_3nvs6h93sg69k78vc0id01kuf` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_buyvu9a9rhg4bvcw0b7glkfmh` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`),
  CONSTRAINT `FK_l7j9rg6jjdgrpvxfxt1m3xp05` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for asignacion_proyecto
-- ----------------------------
DROP TABLE IF EXISTS `asignacion_proyecto`;
CREATE TABLE `asignacion_proyecto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empleado_id` bigint NOT NULL,
  `empleado_asignacion_id` bigint NOT NULL,
  `estado_id` bigint NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `permiso_proyecto_id` bigint NOT NULL,
  `proyecto_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_92eq3ut907wa22pw0oex7n9fy` (`empleado_id`),
  KEY `FK_7wbvn0whig1g1me4jt60b68th` (`empleado_asignacion_id`),
  KEY `FK_k9cv4m59catue30ast7qvljtc` (`estado_id`),
  KEY `FK_jbfelyhc7ql9b5etj7bnsbmpr` (`permiso_proyecto_id`),
  KEY `FK_f1sasytmfb3uf9r0uqwbbpchg` (`proyecto_id`),
  CONSTRAINT `FK_7wbvn0whig1g1me4jt60b68th` FOREIGN KEY (`empleado_asignacion_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_92eq3ut907wa22pw0oex7n9fy` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_f1sasytmfb3uf9r0uqwbbpchg` FOREIGN KEY (`proyecto_id`) REFERENCES `proyecto` (`id`),
  CONSTRAINT `FK_jbfelyhc7ql9b5etj7bnsbmpr` FOREIGN KEY (`permiso_proyecto_id`) REFERENCES `permiso_proyecto` (`id`),
  CONSTRAINT `FK_k9cv4m59catue30ast7qvljtc` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for asignacion_tarea
-- ----------------------------
DROP TABLE IF EXISTS `asignacion_tarea`;
CREATE TABLE `asignacion_tarea` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empleado_id` bigint NOT NULL,
  `empleado_asignacion_id` bigint NOT NULL,
  `estado_id` bigint NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `tarea_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b60kedf4ih6rhoeo0l8odgp52` (`empleado_id`),
  KEY `FK_186rtkq53x2riyxptiwai2m3` (`empleado_asignacion_id`),
  KEY `FK_fxibsw83k7xnmyu7573y5q3l7` (`estado_id`),
  KEY `FK_gyrb6wqt19q22hhqra0y5jxxe` (`tarea_id`),
  CONSTRAINT `FK_186rtkq53x2riyxptiwai2m3` FOREIGN KEY (`empleado_asignacion_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_b60kedf4ih6rhoeo0l8odgp52` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_fxibsw83k7xnmyu7573y5q3l7` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`),
  CONSTRAINT `FK_gyrb6wqt19q22hhqra0y5jxxe` FOREIGN KEY (`tarea_id`) REFERENCES `tarea` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cambio_estado
-- ----------------------------
DROP TABLE IF EXISTS `cambio_estado`;
CREATE TABLE `cambio_estado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empleado_id` bigint NOT NULL,
  `estado_anterior_id` bigint NOT NULL,
  `estado_nuevo_id` bigint NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `tarea_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t7ngfjyqb9429y35o753ort6s` (`empleado_id`),
  KEY `FK_h9ictty7i0q36x62q4saomun8` (`estado_anterior_id`),
  KEY `FK_1hrwglc7dal733mqetyi42g9y` (`estado_nuevo_id`),
  KEY `FK_kp2usvpckkmj7srqwca3gnef0` (`tarea_id`),
  CONSTRAINT `FK_1hrwglc7dal733mqetyi42g9y` FOREIGN KEY (`estado_nuevo_id`) REFERENCES `estado` (`id`),
  CONSTRAINT `FK_h9ictty7i0q36x62q4saomun8` FOREIGN KEY (`estado_anterior_id`) REFERENCES `estado` (`id`),
  CONSTRAINT `FK_kp2usvpckkmj7srqwca3gnef0` FOREIGN KEY (`tarea_id`) REFERENCES `tarea` (`id`),
  CONSTRAINT `FK_t7ngfjyqb9429y35o753ort6s` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cancelacion_tarea
-- ----------------------------
DROP TABLE IF EXISTS `cancelacion_tarea`;
CREATE TABLE `cancelacion_tarea` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empleado_id` bigint NOT NULL,
  `estado_id` bigint NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `motivo` varchar(500) NOT NULL,
  `tarea_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_d970vhr1jf34tld4ddiynraon` (`empleado_id`),
  KEY `FK_8r0iy5hr01797mg2rhpnp5g7r` (`estado_id`),
  KEY `FK_ekiiy7c7kv95ihxdxixyyn1vr` (`tarea_id`),
  CONSTRAINT `FK_8r0iy5hr01797mg2rhpnp5g7r` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`),
  CONSTRAINT `FK_d970vhr1jf34tld4ddiynraon` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_ekiiy7c7kv95ihxdxixyyn1vr` FOREIGN KEY (`tarea_id`) REFERENCES `tarea` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for categoria
-- ----------------------------
DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `empresa_id` bigint NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_nombre` (`empresa_id`,`nombre`),
  CONSTRAINT `FK_9o5682svoglm9kfynk9eu5qwr` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cliente
-- ----------------------------
DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empresa_id` bigint NOT NULL,
  `nombre_pagina` varchar(100) NOT NULL,
  `razon_social` varchar(100) NOT NULL,
  `servidor_alojamiento` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qd3ig3hh15s6t3dvfxdfvwusv` (`empresa_id`),
  CONSTRAINT `FK_qd3ig3hh15s6t3dvfxdfvwusv` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cliente_foto
-- ----------------------------
DROP TABLE IF EXISTS `cliente_foto`;
CREATE TABLE `cliente_foto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint NOT NULL,
  `foto` tinyblob NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l6tijxybeo4uk75y7nsgwhfqy` (`cliente_id`),
  CONSTRAINT `FK_l6tijxybeo4uk75y7nsgwhfqy` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cliente_proyecto
-- ----------------------------
DROP TABLE IF EXISTS `cliente_proyecto`;
CREATE TABLE `cliente_proyecto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint NOT NULL,
  `proyecto_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_proyecto_id` (`cliente_id`,`proyecto_id`),
  KEY `FK_q1p7xsknj632kojov0ube8j60` (`proyecto_id`),
  CONSTRAINT `FK_ehq6cv3d1d8h42nvhlcygtsm5` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FK_q1p7xsknj632kojov0ube8j60` FOREIGN KEY (`proyecto_id`) REFERENCES `proyecto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for correo
-- ----------------------------
DROP TABLE IF EXISTS `correo`;
CREATE TABLE `correo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint DEFAULT NULL,
  `correo` varchar(100) NOT NULL,
  `persona_id` bigint DEFAULT NULL,
  `principal` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ibbsg46ypugrhfheob5su4qry` (`cliente_id`),
  KEY `FK_ngfx1pwxq028xkca40wr0j1rd` (`persona_id`),
  CONSTRAINT `FK_ibbsg46ypugrhfheob5su4qry` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FK_ngfx1pwxq028xkca40wr0j1rd` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dia
-- ----------------------------
DROP TABLE IF EXISTS `dia`;
CREATE TABLE `dia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ursg31vmjxkpv46or7lb8o74` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for direccion
-- ----------------------------
DROP TABLE IF EXISTS `direccion`;
CREATE TABLE `direccion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `calle` varchar(100) NOT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `colonia` varchar(100) NOT NULL,
  `estado` varchar(100) NOT NULL,
  `municipio` varchar(100) NOT NULL,
  `numero_exterior` varchar(5) NOT NULL,
  `numero_interior` varchar(5) NOT NULL,
  `persona_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6foldly1k0ib1ls5fqx82by66` (`cliente_id`),
  UNIQUE KEY `UK_6py1bql24hnawqudq2hh2gcst` (`persona_id`),
  CONSTRAINT `FK_6foldly1k0ib1ls5fqx82by66` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FK_6py1bql24hnawqudq2hh2gcst` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for empleado
-- ----------------------------
DROP TABLE IF EXISTS `empleado`;
CREATE TABLE `empleado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empresa_id` bigint NOT NULL,
  `estado_id` bigint NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `persona_id` bigint NOT NULL,
  `puesto_id` bigint NOT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pjv6m14uotxloxlv04buynykk` (`persona_id`),
  UNIQUE KEY `UK_6ff36el6hfqwrtnvk0y9jd6sh` (`usuario_id`),
  KEY `FK_85m9i4om8kfc5qqwfdwvv0yjf` (`empresa_id`),
  KEY `FK_2itxl5bv1nel16h9srpi757au` (`estado_id`),
  KEY `FK_ljsevlk2n94afkg895c86rbu0` (`puesto_id`),
  CONSTRAINT `FK_2itxl5bv1nel16h9srpi757au` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`),
  CONSTRAINT `FK_6ff36el6hfqwrtnvk0y9jd6sh` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK_85m9i4om8kfc5qqwfdwvv0yjf` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`),
  CONSTRAINT `FK_ljsevlk2n94afkg895c86rbu0` FOREIGN KEY (`puesto_id`) REFERENCES `puesto` (`id`),
  CONSTRAINT `FK_pjv6m14uotxloxlv04buynykk` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for empresa
-- ----------------------------
DROP TABLE IF EXISTS `empresa`;
CREATE TABLE `empresa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correo_id` bigint NOT NULL,
  `direccion_id` bigint NOT NULL,
  `fecha_licencia` datetime NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `razon_social` varchar(100) NOT NULL,
  `telefono_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cwrp0jxws4675ladjti3nxcf5` (`correo_id`),
  KEY `FK_1vhvi2p2r4rvju6drbmj3iux3` (`direccion_id`),
  KEY `FK_ba8ox5f30vhcue7e8gt2m1qqv` (`telefono_id`),
  CONSTRAINT `FK_1vhvi2p2r4rvju6drbmj3iux3` FOREIGN KEY (`direccion_id`) REFERENCES `direccion` (`id`),
  CONSTRAINT `FK_ba8ox5f30vhcue7e8gt2m1qqv` FOREIGN KEY (`telefono_id`) REFERENCES `telefono` (`id`),
  CONSTRAINT `FK_cwrp0jxws4675ladjti3nxcf5` FOREIGN KEY (`correo_id`) REFERENCES `correo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for estado
-- ----------------------------
DROP TABLE IF EXISTS `estado`;
CREATE TABLE `estado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color` varchar(20) DEFAULT NULL,
  `nombre` varchar(45) NOT NULL,
  `porcentaje` varchar(10) DEFAULT NULL,
  `tipo_estado_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qqgq56nv9wsmc666fnp16m13r` (`tipo_estado_id`),
  CONSTRAINT `FK_qqgq56nv9wsmc666fnp16m13r` FOREIGN KEY (`tipo_estado_id`) REFERENCES `tipo_estado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for horario_laboral
-- ----------------------------
DROP TABLE IF EXISTS `horario_laboral`;
CREATE TABLE `horario_laboral` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dia_id` bigint NOT NULL,
  `empleado_id` bigint NOT NULL,
  `hora_fin` datetime NOT NULL,
  `hora_inicio` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sydcpja516lvjkxfbkfwe8lvf` (`dia_id`),
  KEY `FK_nxexann76tft0oilxxkbgo4pu` (`empleado_id`),
  CONSTRAINT `FK_nxexann76tft0oilxxkbgo4pu` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_sydcpja516lvjkxfbkfwe8lvf` FOREIGN KEY (`dia_id`) REFERENCES `dia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nivel_prioridad
-- ----------------------------
DROP TABLE IF EXISTS `nivel_prioridad`;
CREATE TABLE `nivel_prioridad` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `prioridad` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sxbcbheg1ytf2x76bj75rxp0` (`nombre`),
  UNIQUE KEY `UK_9kdj6i0joqvb1vtyrfo9n4gip` (`prioridad`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for permiso_proyecto
-- ----------------------------
DROP TABLE IF EXISTS `permiso_proyecto`;
CREATE TABLE `permiso_proyecto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(150) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for persona
-- ----------------------------
DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_nacimiento` datetime NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `primer_apellido` varchar(100) NOT NULL,
  `segundo_apellido` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for persona_foto
-- ----------------------------
DROP TABLE IF EXISTS `persona_foto`;
CREATE TABLE `persona_foto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `foto` tinyblob NOT NULL,
  `persona_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h7p5fu1bf4caj7ugsuei2jl04` (`persona_id`),
  CONSTRAINT `FK_h7p5fu1bf4caj7ugsuei2jl04` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for plataforma_digital
-- ----------------------------
DROP TABLE IF EXISTS `plataforma_digital`;
CREATE TABLE `plataforma_digital` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ll8134lsupcn7t8aubva8yvaq` (`cliente_id`),
  CONSTRAINT `FK_ll8134lsupcn7t8aubva8yvaq` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for proyecto
-- ----------------------------
DROP TABLE IF EXISTS `proyecto`;
CREATE TABLE `proyecto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `categoria_id` bigint NOT NULL,
  `clave` varchar(45) NOT NULL,
  `color` varchar(15) DEFAULT NULL,
  `descripcion` varchar(200) NOT NULL,
  `empleado_id` bigint NOT NULL,
  `empresa_id` bigint NOT NULL,
  `estado_id` bigint NOT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `tipo_privacidad_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_clave` (`empresa_id`,`clave`),
  KEY `FK_ppso8ystxhm6d5hvpfdq2a4dr` (`categoria_id`),
  KEY `FK_ghw4p8ya28x8l42l14sj40mnf` (`empleado_id`),
  KEY `FK_rikdhsba4oqhhbxonm6dvhkhe` (`estado_id`),
  KEY `FK_4u0957pesgcnp5rk9yhv8cpgs` (`tipo_privacidad_id`),
  CONSTRAINT `FK_4u0957pesgcnp5rk9yhv8cpgs` FOREIGN KEY (`tipo_privacidad_id`) REFERENCES `tipo_privacidad` (`id`),
  CONSTRAINT `FK_614en9tqfm3cke4das0gwiba5` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`),
  CONSTRAINT `FK_ghw4p8ya28x8l42l14sj40mnf` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK_ppso8ystxhm6d5hvpfdq2a4dr` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FK_rikdhsba4oqhhbxonm6dvhkhe` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for puesto
-- ----------------------------
DROP TABLE IF EXISTS `puesto`;
CREATE TABLE `puesto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empresa_id` bigint NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_nombre` (`empresa_id`,`nombre`),
  CONSTRAINT `FK_37mxa3asjiji0t47hpj22bfyu` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for recurso
-- ----------------------------
DROP TABLE IF EXISTS `recurso`;
CREATE TABLE `recurso` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `config_attribute` varchar(255) NOT NULL,
  `http_method` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_url` (`http_method`,`url`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for representante
-- ----------------------------
DROP TABLE IF EXISTS `representante`;
CREATE TABLE `representante` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `persona_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_persona_id` (`cliente_id`,`persona_id`),
  KEY `FK_5670ka4231o31xd0trr47tcs3` (`persona_id`),
  CONSTRAINT `FK_5670ka4231o31xd0trr47tcs3` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`),
  CONSTRAINT `FK_7i96omyl3n06buco5ckwd272v` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for rol
-- ----------------------------
DROP TABLE IF EXISTS `rol`;
CREATE TABLE `rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `authority` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pbdeb23es4jm3il5dvs9ec1jb` (`authority`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tarea
-- ----------------------------
DROP TABLE IF EXISTS `tarea`;
CREATE TABLE `tarea` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `clave` varchar(45) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `empleado_id` bigint NOT NULL,
  `estado_id` bigint NOT NULL,
  `fecha_hora_fin` datetime DEFAULT NULL,
  `fecha_hora_inicio` datetime NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `nivel_prioridad_id` bigint NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `proyecto_id` bigint NOT NULL,
  `tarea_id` bigint DEFAULT NULL,
  `tipo_tarea_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2ilf98xrr09nfjgeok6v1tna0` (`clave`),
  KEY `FK_tj7cxfhvsunpjhy5fmwo5tsg3` (`empleado_id`),
  KEY `FK_64o9bbqdidnhsc74dvrj89exf` (`estado_id`),
  KEY `FK_cj1eki5hyoqbc7hg1yo4wruj4` (`nivel_prioridad_id`),
  KEY `FK_oo24nvonl6jp192vteijgp62o` (`proyecto_id`),
  KEY `FK_fwpsm76047am54ifx3ssbdvvs` (`tarea_id`),
  KEY `FK_6bp65i7d92asl8idfei8xb745` (`tipo_tarea_id`),
  CONSTRAINT `FK_64o9bbqdidnhsc74dvrj89exf` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`),
  CONSTRAINT `FK_6bp65i7d92asl8idfei8xb745` FOREIGN KEY (`tipo_tarea_id`) REFERENCES `tipo_tarea` (`id`),
  CONSTRAINT `FK_cj1eki5hyoqbc7hg1yo4wruj4` FOREIGN KEY (`nivel_prioridad_id`) REFERENCES `nivel_prioridad` (`id`),
  CONSTRAINT `FK_fwpsm76047am54ifx3ssbdvvs` FOREIGN KEY (`tarea_id`) REFERENCES `tarea` (`id`),
  CONSTRAINT `FK_oo24nvonl6jp192vteijgp62o` FOREIGN KEY (`proyecto_id`) REFERENCES `proyecto` (`id`),
  CONSTRAINT `FK_tj7cxfhvsunpjhy5fmwo5tsg3` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for telefono
-- ----------------------------
DROP TABLE IF EXISTS `telefono`;
CREATE TABLE `telefono` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint DEFAULT NULL,
  `persona_id` bigint DEFAULT NULL,
  `principal` bit(1) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `tipo_telefono_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m1mkusbp2aa7v1h2avs22vry7` (`cliente_id`),
  KEY `FK_29pktthbkxi6a17wlqfmeo8u5` (`persona_id`),
  KEY `FK_ru1rvht45l196g8fyelcv2aj5` (`tipo_telefono_id`),
  CONSTRAINT `FK_29pktthbkxi6a17wlqfmeo8u5` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`),
  CONSTRAINT `FK_m1mkusbp2aa7v1h2avs22vry7` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FK_ru1rvht45l196g8fyelcv2aj5` FOREIGN KEY (`tipo_telefono_id`) REFERENCES `tipo_telefono` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tipo_estado
-- ----------------------------
DROP TABLE IF EXISTS `tipo_estado`;
CREATE TABLE `tipo_estado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `empresa_id` bigint NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1up7k8lmnyx2fvi30wdjglmlt` (`nombre`),
  KEY `FK_n04sxbfk8vin7lke3yg75citu` (`empresa_id`),
  CONSTRAINT `FK_n04sxbfk8vin7lke3yg75citu` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tipo_plataforma_digital
-- ----------------------------
DROP TABLE IF EXISTS `tipo_plataforma_digital`;
CREATE TABLE `tipo_plataforma_digital` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ilommdqogpoprdmwf9yiushm` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tipo_plataforma_digital_telefono
-- ----------------------------
DROP TABLE IF EXISTS `tipo_plataforma_digital_telefono`;
CREATE TABLE `tipo_plataforma_digital_telefono` (
  `tipo_plataforma_digital_telefonos_id` bigint DEFAULT NULL,
  `telefono_id` bigint DEFAULT NULL,
  KEY `FK_ksaye5rppg5ic310xy5pmvvqp` (`telefono_id`),
  KEY `FK_i53rd5030ukyyauwqfgbpgy3f` (`tipo_plataforma_digital_telefonos_id`),
  CONSTRAINT `FK_i53rd5030ukyyauwqfgbpgy3f` FOREIGN KEY (`tipo_plataforma_digital_telefonos_id`) REFERENCES `tipo_plataforma_digital` (`id`),
  CONSTRAINT `FK_ksaye5rppg5ic310xy5pmvvqp` FOREIGN KEY (`telefono_id`) REFERENCES `telefono` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tipo_privacidad
-- ----------------------------
DROP TABLE IF EXISTS `tipo_privacidad`;
CREATE TABLE `tipo_privacidad` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rag9hnjuj8y79goxqqb8oxk46` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tipo_tarea
-- ----------------------------
DROP TABLE IF EXISTS `tipo_tarea`;
CREATE TABLE `tipo_tarea` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k3bi3911eflxj54g9dx6a0nms` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tipo_telefono
-- ----------------------------
DROP TABLE IF EXISTS `tipo_telefono`;
CREATE TABLE `tipo_telefono` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1r6gnmo3x0piso5du9damav99` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for usuario
-- ----------------------------
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_expired` bit(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_863n1y3x0jalatoir4325ehal` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for usuario_rol
-- ----------------------------
DROP TABLE IF EXISTS `usuario_rol`;
CREATE TABLE `usuario_rol` (
  `usuario_id` bigint NOT NULL,
  `rol_id` bigint NOT NULL,
  PRIMARY KEY (`usuario_id`,`rol_id`),
  KEY `FK_5gtipd65p6pda9ltx23lm68ge` (`rol_id`),
  CONSTRAINT `FK_5gtipd65p6pda9ltx23lm68ge` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`),
  CONSTRAINT `FK_91qmacuyat735y6p88fsblnx5` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
