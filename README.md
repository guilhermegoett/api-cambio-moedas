# Sistema de C�mbio de Moedas

Este projeto � uma API REST para gerenciamento de produtos, moedas, reinos e transa��es de c�mbio. Ele segue os princ�pios de **Clean Architecture**, com **arquitetura hexagonal**, e foi desenvolvido com o conceito **API First**, utilizando o **OpenAPI Generator** para gera��o autom�tica de modelos e interfaces.

---

## Sum�rio

1. [Tecnologias Utilizadas](#tecnologias-utilizadas)
2. [Estrutura do Projeto](#estrutura-do-projeto)
3. [Padr�es de Projeto](#padr�es-de-projeto-utilizados)
4. [Como Executar o Projeto](#como-executar-o-projeto)
5. [Como Testar](#como-testar)
7. [Documenta��o](#documenta��o)
8. [Monitoramento](#monitoramento)
9. [Funcionalidades](#funcionalidades)
10. [Script Sql](#script-sql)
11. [Licen�a](#licen�a)
12. [Autor](#autor)

---

## Tecnologias Utilizadas

* Java 17+
* Spring Boot 3.x
* Spring Web
* Spring Data JPA
* H2 Database (em mem�ria)
* OpenAPI Generator
* Lombok
* MapStruct
* Maven
* Swagger UI
* Agendador com `@Scheduled` (opcional)

---

## Estrutura do Projeto

O projeto segue o padr�o de arquitetura hexagonal, com pacotes organizados por camadas:

```
??? application
?   ??? services         # L�gica de neg�cio
??? domain
?   ??? model            # Entidades e modelos de dom�nio
??? adapter
?   ??? controller       # Implementa��o das interfaces geradas pela OpenAPI
?   ??? repository       # Interfaces JPA para persist�ncia
?   ??? mapper           # Convers�o entre entidades e DTOs
??? config               # Configura��es gerais (mappers, agendadores, etc)
??? openapi              # Interfaces e modelos gerados automaticamente
```

---

## Padr�es de Projeto Utilizados

1. DTO (Data Transfer Object)
Finalidade: Transferir dados entre camadas (ex: entre controller e service) sem expor entidades JPA diretamente.

Exemplo: ReinoDTO, MoedaDTO, ProdutoDTO, TransacaoDTO.

2. Mapper (com MapStruct)
Finalidade: Convers�o autom�tica entre DTOs e entidades/modelos.

Exemplo: ReinoMapper, MoedaMapper, etc.

Padr�o associado: Mapper Pattern

3. Repository (Spring Data JPA)
Finalidade: Abstra��o do acesso a dados, com opera��es CRUD prontas.

Exemplo: MoedaRepository, ReinoRepository, TaxaCambioRepository.

Padr�o associado: Repository Pattern

4. Service Layer
Finalidade: Isola a l�gica de neg�cio das controllers e dos reposit�rios.

Exemplo: CambioService, MoedaService, ProdutoService, etc.

Padr�o associado: Service Layer Pattern

5. Controller (Adapter Layer via API First)
Finalidade: Controladores implementam interfaces geradas pelo contrato OpenAPI.

Exemplo: MoedaController, ReinoController, CambioController.

Padr�o associado: Adapter Pattern e Controller Pattern

6. Scheduled Task (Scheduler com Spring)
Finalidade: Automatiza a atualiza��o peri�dica das taxas de c�mbio.

Padr�o associado: Scheduler Pattern

7. Conditional Configuration
Finalidade: Habilita ou desabilita agendamentos com base em uma propriedade (scheduling.enabled).

Padr�o associado: Conditional Activation Pattern

8. API First (OpenAPI Generator)
Finalidade: Define a estrutura da API via contrato YAML antes da implementa��o.

Benef�cio: Garante alinhamento entre frontend e backend.

---

## Como Executar o Projeto

### Pr�-requisitos

* Java 17+
* Maven 3.x

### Passos para rodar:

1. Clone o reposit�rio:

   ```bash
   git clone https://github.com/seu-usuario/nome-do-repositorio.git
   cd nome-do-repositorio
   ```

2. Compile o projeto e gere as classes a partir do OpenAPI:

   ```bash
   mvn clean install
   ```

3. Execute a aplica��o:

   ```bash
   mvn spring-boot:run
   ```

4. Acesse o Swagger UI:

   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

## Agendamento de Atualiza��o de Taxas

A aplica��o possui uma rotina agendada para atualiza��o autom�tica das taxas de c�mbio a cada 5 minutos.

Para ativar ou desativar, utilize a seguinte propriedade no `application.properties`:

```properties
scheduling.enabled=true   # ou false
```

---

## Como Testar

Voc� pode testar a API de duas formas:

### Via Swagger UI

Acesse [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) e interaja com os endpoints REST.

### Via Testes Unit�rios e de Integra��o

Execute os testes com:

```bash
mvn test
```

---

## Documenta��o

A documenta��o da API est? dispon�vel atrav�s do Swagger e pode ser acessada diretamente atrav�s da URL:

http://localhost:8080/swagger-ui/index.html

---

## Monitoramento

A aplica��o utiliza o Spring Boot Actuator para expor endpoints que permitem o monitoramento da sa�de da aplica��o, m�tricas de uso de recursos e informa��es do sistema. Isso facilita a observabilidade e diagn�stico durante o desenvolvimento e tamb�m em ambientes de produ��o.

http://localhost:8080/actuator/health

## Funcionalidades

* Cadastro e listagem de Produtos
* Cadastro de Moedas
* Cria��o de Reinos e defini��o de moeda local
* Transa��es de c�mbio com c�lculo de taxas
* Atualiza��o autom�tica das taxas de c�mbio (simula��o de mercado)

---

## Script Sql

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

-- Transa��es para SRM
INSERT INTO transacao(produto_id, valor_inicial, valor_final, taxa, data_hora, reino_id, moeda_origem_id, moeda_destino_id)
VALUES 
(1, 100.00, 150.00, 1.5, CURRENT_TIMESTAMP, 1, 1, 2), -- PELES de OURO_REAL -> TIBAR
(2, 200.00, 400.00, 2.0, CURRENT_TIMESTAMP, 1, 1, 2), -- MADEIRA
(3, 80.00, 60.00, 0.75, CURRENT_TIMESTAMP, 1, 1, 2); -- HIDROMEL

-- Transa��es para NORDUR
INSERT INTO transacao(produto_id, valor_inicial, valor_final, taxa, data_hora, reino_id, moeda_origem_id, moeda_destino_id)
VALUES 
(1, 100.00, 310.00, 3.1, CURRENT_TIMESTAMP, 2, 3, 4), -- PELES de DRACMAS -> FLORIM
(2, 150.00, 285.00, 1.9, CURRENT_TIMESTAMP, 2, 3, 4); -- MADEIRA

-- Transa��es para AURELIA
INSERT INTO transacao(produto_id, valor_inicial, valor_final, taxa, data_hora, reino_id, moeda_origem_id, moeda_destino_id)
VALUES 
(1, 50.00, 70.00, 1.4, CURRENT_TIMESTAMP, 3, 4, 5),
(3, 60.00, 66.00, 1.1, CURRENT_TIMESTAMP, 3, 4, 5);

---

## Observa��es

* O banco de dados H2 � vol�til e reinicia a cada execu��o.
* As entidades est�o configuradas com `@Data` e `@Entity` usando o Lombok.
* O mapeamento entre DTOs e entidades � feito com MapStruct.
* Os agendadores usam `@Scheduled` com controle condicional por propriedade (`scheduling.enabled`).

---

## Licen�a

Este projeto � distribu�do sob a licen�a MIT. Veja `LICENSE` para mais informa��es.

---

## Autor

Desenvolvido por Guilherme Augusto Goettnauer
Contato: [LinkedIn](https://www.linkedin.com/in/guilherme-goettnauer-4422a226/) | [Email](mailto:guilhermegoet@gmail.com)

