-- Tabla de tecnologías
CREATE TABLE IF NOT EXISTS users
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(50) NOT NULL UNIQUE,
    email   VARCHAR(50) NOT NULL,
    status TINYINT(1) NOT NULL DEFAULT 0
);
