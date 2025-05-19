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
    nome VARCHAR(50) NOT NULL UNIQUE,
    moeda_id BIGINT NOT NULL,
    CONSTRAINT fk_reino_moeda FOREIGN KEY (moeda_id) REFERENCES moeda(id)
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
    produto_id BIGINT NOT NULL,
    valor_inicial DECIMAL(10, 2) NOT NULL,
    valor_final DECIMAL(10, 2) NOT NULL,
    taxa DECIMAL(10, 4) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    reino_id BIGINT NOT NULL,
    moeda_origem_id BIGINT NOT NULL,
    moeda_destino_id BIGINT NOT NULL,
    CONSTRAINT fk_transacao_produto FOREIGN KEY (produto_id) REFERENCES PRODUTO(id),
    CONSTRAINT fk_transacao_reino FOREIGN KEY (reino_id) REFERENCES REINO(id),
    CONSTRAINT fk_transacao_moeda_origem FOREIGN KEY (moeda_origem_id) REFERENCES MOEDA(id),
    CONSTRAINT fk_transacao_moeda_destino FOREIGN KEY (moeda_destino_id) REFERENCES MOEDA(id)
);

INSERT INTO moeda (nome) VALUES ('OURO_REAL');
INSERT INTO moeda (nome) VALUES ('TIBAR');
INSERT INTO moeda (nome) VALUES ('DRACMAS');
INSERT INTO moeda (nome) VALUES ('FLORIM');
INSERT INTO moeda (nome) VALUES ('DENARIO');

INSERT INTO produto (nome) VALUES ('PELES');
INSERT INTO produto (nome) VALUES ('MADEIRA');
INSERT INTO produto (nome) VALUES ('HIDROMEL');
INSERT INTO produto (nome) VALUES ('GRAOS');
INSERT INTO produto (nome) VALUES ('SAL');
INSERT INTO produto (nome) VALUES ('VINHO');

-- Reinos (agora com a moeda associada)
INSERT INTO reino (nome, moeda_id) VALUES ('SRM', 1);       -- OURO_REAL
INSERT INTO reino (nome, moeda_id) VALUES ('ANOES', 2);     -- TIBAR
INSERT INTO reino (nome, moeda_id) VALUES ('NORDUR', 3);    -- DRACMAS
INSERT INTO reino (nome, moeda_id) VALUES ('AURELIA', 4);   -- FLORIM
INSERT INTO reino (nome, moeda_id) VALUES ('VALAR', 5);     -- DENARIO

-- SRM (moeda: OURO_REAL -> outras moedas)
INSERT INTO taxa_cambio(moeda_origem_id, moeda_destino_id, produto_id, taxa, data_atualizacao)
VALUES 
(1, 3, 4, 1.2, CURRENT_DATE), -- GRAOS
(1, 3, 5, 2.5, CURRENT_DATE), -- SAL
(1, 3, 6, 0.8, CURRENT_DATE); -- VINHO

INSERT INTO taxa_cambio(moeda_origem_id, moeda_destino_id, produto_id, taxa, data_atualizacao)
VALUES (1, 2, 1, 2.5, CURRENT_DATE),
       (1, 2, 2, 2.5, CURRENT_DATE),
       (1, 2, 3, 2.5, CURRENT_DATE);

-- NORDUR (moeda: DRACMAS -> outras)
INSERT INTO taxa_cambio(moeda_origem_id, moeda_destino_id, produto_id, taxa, data_atualizacao)
VALUES 
(3, 4, 1, 3.1, CURRENT_DATE), -- PELES
(3, 4, 2, 1.9, CURRENT_DATE); -- MADEIRA

-- AURELIA (moeda: FLORIM -> outras)
INSERT INTO taxa_cambio(moeda_origem_id, moeda_destino_id, produto_id, taxa, data_atualizacao)
VALUES 
(4, 5, 1, 1.4, CURRENT_DATE), -- PELES
(4, 5, 2, 1.7, CURRENT_DATE), -- MADEIRA
(4, 5, 3, 1.1, CURRENT_DATE); -- HIDROMEL

-- Transações para SRM
INSERT INTO transacao(produto_id, valor_inicial, valor_final, taxa, data_hora, reino_id, moeda_origem_id, moeda_destino_id)
VALUES 
(1, 100.00, 150.00, 1.5, CURRENT_TIMESTAMP, 1, 1, 2), -- PELES de OURO_REAL -> TIBAR
(2, 200.00, 400.00, 2.0, CURRENT_TIMESTAMP, 1, 1, 2), -- MADEIRA
(3, 80.00, 60.00, 0.75, CURRENT_TIMESTAMP, 1, 1, 2); -- HIDROMEL

-- Transações para NORDUR
INSERT INTO transacao(produto_id, valor_inicial, valor_final, taxa, data_hora, reino_id, moeda_origem_id, moeda_destino_id)
VALUES 
(1, 100.00, 310.00, 3.1, CURRENT_TIMESTAMP, 2, 3, 4), -- PELES de DRACMAS -> FLORIM
(2, 150.00, 285.00, 1.9, CURRENT_TIMESTAMP, 2, 3, 4); -- MADEIRA

-- Transações para AURELIA
INSERT INTO transacao(produto_id, valor_inicial, valor_final, taxa, data_hora, reino_id, moeda_origem_id, moeda_destino_id)
VALUES 
(1, 50.00, 70.00, 1.4, CURRENT_TIMESTAMP, 3, 4, 5),
(3, 60.00, 66.00, 1.1, CURRENT_TIMESTAMP, 3, 4, 5);
