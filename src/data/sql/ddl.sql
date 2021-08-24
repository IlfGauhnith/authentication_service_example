CREATE TABLE IF NOT EXISTS usuario (
    usuario VARCHAR(50) NOT NULL,
    senha bytea NOT NULL,
    salt bytea NOT NULL,
    ativo BOOLEAN default FALSE,
    ultimo_acesso TIMESTAMPTZ,
    PRIMARY KEY (usuario)
 );
