CREATE DATABASE login;
CREATE TABLE IF NOT EXISTS usuario (
    usuario VARCHAR(50) NOT NULL,
    senha bytea NOT NULL,
    salt bytea NOT NULL,
    ativo BOOLEAN default FALSE,
    ultimo_acesso TIMESTAMPTZ,
    PRIMARY KEY (usuario)
 );

CREATE USER loginadmin WITH PASSWORD 'root';

/* on database login */
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE usuario TO loginadmin;