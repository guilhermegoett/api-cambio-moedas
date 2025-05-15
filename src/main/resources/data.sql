CREATE TABLE moeda (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE reino (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE taxa_cambio (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    moeda_origem_id BIGINT NOT NULL,
    moeda_destino_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    taxa DECIMAL(10, 4) NOT NULL,
    data_atualizacao DATE NOT NULL,
    CONSTRAINT fk_taxa_moeda_origem FOREIGN KEY (moeda_origem_id) REFERENCES MOEDA(id),
    CONSTRAINT fk_taxa_moeda_destino FOREIGN KEY (moeda_destino_id) REFERENCES MOEDA(id),
    CONSTRAINT fk_taxa_produto FOREIGN KEY (produto_id) REFERENCES PRODUTO(id)
);

CREATE TABLE transacao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    produto VARCHAR(50) NOT NULL,
    valor_inicial DECIMAL(10, 2) NOT NULL,
    valor_final DECIMAL(10, 2) NOT NULL,
    taxa DECIMAL(10, 4) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    reino VARCHAR(50) NOT NULL,
    moeda_origem VARCHAR(50) NOT NULL,
    moeda_destino VARCHAR(50) NOT NULL
);

INSERT INTO moeda (id, nome) VALUES (1, 'OURO_REAL');
INSERT INTO moeda (id, nome) VALUES (2, 'TIBAR');

INSERT INTO produto (id, nome) VALUES (1, 'PELES');
INSERT INTO produto (id, nome) VALUES (2, 'MADEIRA');
INSERT INTO produto (id, nome) VALUES (3, 'HIDROMEL');

INSERT INTO reino (id, nome) VALUES (1, 'SRM');

INSERT INTO taxa_cambio(moeda_origem_id, moeda_destino_id, produto_id, taxa, data_atualizacao)
VALUES (1, 2, 1, 1.5, CURRENT_DATE),
       (1, 2, 2, 2.0, CURRENT_DATE),
       (1, 2, 3, 0.75, CURRENT_DATE);
