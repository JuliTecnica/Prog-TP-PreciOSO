
CREATE TABLE `credential` (

    `id_credential` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `password` varchar(64) NOT NULL,
    `role` enum('ADMIN','EMPLOYEE','MANAGER') DEFAULT NULL,
    `username` varchar(16) NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (

    `id_user` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `dni` varchar(8) NOT NULL,
    `firstname` varchar(50) NOT NULL,
    `lastname` varchar(50) NOT NULL,
    `status` enum('DISABLED','ENABLED') DEFAULT NULL,
    `id_credential` bigint NOT NULL UNIQUE ,

    CONSTRAINT `FK_user_credential` FOREIGN KEY (`id_credential`) REFERENCES `credential` (`id_credential`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `address` (

    `id_address` bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `city` varchar(50) NOT NULL,
    `number` varchar(5) NOT NULL,
    `street` varchar(50) NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `product` (

    `id_product` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `name` varchar(50) NOT NULL,
    `status` enum('DISABLED','ENABLED') DEFAULT NULL

  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `supplier` (
  `id_supplier` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `company_name` varchar(75) NOT NULL,
  `cuit` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `id_address` bigint NOT NULL UNIQUE,

  CONSTRAINT `FK_supplier_address` FOREIGN KEY (`id_address`) REFERENCES `address` (`id_address`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `product_supplier` (
  `id_product_supplier` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `cost` decimal(10,2) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `profit_margin` decimal(10,2) NOT NULL,
  `id_product` bigint DEFAULT NULL,
  `id_supplier` bigint DEFAULT NULL,

  CONSTRAINT `FK_product_supplier_supplier` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`),
  CONSTRAINT `FK_product_supplier_product` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# Tablas de auditoria

CREATE TABLE `revinfo` (

    `rev` int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `date` datetime(6) DEFAULT NULL,
    `id_user` bigint DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `address_audit` (

    `id_address` bigint NOT NULL,
    `revision` int NOT NULL,
    `revision_type` tinyint DEFAULT NULL,
    `city` varchar(50) DEFAULT NULL,
    `number` varchar(5) DEFAULT NULL,
    `street` varchar(50) DEFAULT NULL,

    PRIMARY KEY (`revision`,`id_address`),
    CONSTRAINT `FK_address_audit_revinfo` FOREIGN KEY (`revision`) REFERENCES `revinfo`(`rev`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `credential_audit` (
  `id_credential` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `role` enum('ADMIN','EMPLOYEE','MANAGER') DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_credential`),
  CONSTRAINT `FK_credential_audit_revinfo` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `product_audit` (
  `id_product` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `status` enum('DISABLED','ENABLED') DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_product`),
  CONSTRAINT `FK_product_audit_revinfo` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `product_supplier_audit` (
  `id_product_supplier` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `profit_margin` decimal(10,2) DEFAULT NULL,
  `id_product` bigint DEFAULT NULL,
  `id_supplier` bigint DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_product_supplier`),
  CONSTRAINT `FK_product_supplier_audit_revinfo` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `supplier_audit` (
  `id_supplier` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `company_name` varchar(75) DEFAULT NULL,
  `cuit` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `id_address` bigint DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_supplier`),
  CONSTRAINT `FK_supplier_audit_revinfo` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_audit` (
  `id_user` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `dni` varchar(8) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `status` enum('DISABLED','ENABLED') DEFAULT NULL,
  `id_credential` bigint DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_user`),
  CONSTRAINT `FK_user_audit_revinfo` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;