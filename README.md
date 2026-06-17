# Sistema de Pedidos com Arquitetura de Microservices

## 📋 Sobre o Projeto

Este projeto foi desenvolvido com Arquitetura de Microservices utilizando Java e Spring Boot.

O objetivo foi construir um sistema completo de gestão de compras, aplicando conceitos modernos de sistemas distribuídos, comunicação assíncrona, mensageria, geração de relatórios e armazenamento de arquivos.

A solução foi dividida em múltiplos microservices independentes, seguindo boas práticas de mercado para escalabilidade, desacoplamento e manutenção.

---

## 🏗️ Arquitetura

O sistema é composto por microservices independentes responsáveis por diferentes domínios de negócio:

* Clientes
* Produtos
* Pedidos
* Faturamento
* Logística

Cada serviço possui:

* Banco de dados próprio
* APIs REST independentes
* Comunicação assíncrona através de eventos
* Responsabilidade única dentro da arquitetura

---

## 🚀 Tecnologias Utilizadas

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Spring Validation
* OpenFeign
* Lombok

### Mensageria

* Apache Kafka

  * Producers
  * Consumers
  * Tópicos
  * Particionamento
  * Comunicação assíncrona

### Banco de Dados

* PostgreSQL
* Bancos independentes por microservice

### Containers

* Docker
* Docker Compose

### Armazenamento de Arquivos

* MinIO (compatível com Amazon S3)

### Relatórios

* Jasper Reports

### Integrações

* Webhooks
* APIs REST

---

## 📡 Comunicação entre Serviços

A comunicação foi implementada utilizando dois modelos:

### Comunicação Síncrona

* APIs REST
* OpenFeign

### Comunicação Assíncrona

* Apache Kafka
* Publicação de eventos
* Consumo de eventos
* Event Driven Architecture (EDA)

---

## 📂 Principais Conceitos Aplicados

* Microservices Architecture
* Event Driven Architecture (EDA)
* Domain Separation
* Database per Service
* Comunicação Assíncrona
* Integração por Eventos
* Webhooks
* Armazenamento Distribuído
* Relatórios Dinâmicos
* Containers com Docker

---

## 🔄 Fluxo de Negócio

Exemplo simplificado do fluxo de pedidos:

1. Cliente realiza um pedido.
2. Serviço de Pedidos valida cliente e produtos.
3. Pedido é persistido.
4. Evento é publicado no Kafka.
5. Serviço de Faturamento processa o pedido.
6. Serviço de Logística recebe o evento.
7. Relatórios podem ser gerados através do Jasper Reports.
8. Arquivos e documentos podem ser armazenados no MinIO.

---

## 🎯 Objetivos de Aprendizado

Durante o desenvolvimento deste projeto foram praticados:

* Desenvolvimento de microservices com Spring Boot
* Comunicação entre serviços
* Apache Kafka na prática
* Event Driven Architecture
* Docker e containers
* Integrações REST
* Webhooks
* Geração de relatórios
* Armazenamento de arquivos com MinIO
* Boas práticas de arquitetura distribuída

---

## 📝 Observações do Projeto

Durante o desenvolvimento deste foram utlizados: 

### Docker Compose

Foi criado um ambiente completo utilizando Docker Compose para provisionamento dos serviços necessários ao sistema, incluindo:

* Bancos de dados independentes para cada microservice;
* Apache Kafka para mensageria;
* MinIO para armazenamento de arquivos;
* Demais dependências necessárias para execução local do projeto.

Essa abordagem permite que todo o ambiente seja inicializado rapidamente e de forma padronizada.

### JasperSoft Studio

Os relatórios foram desenvolvidos utilizando o JasperSoft Studio, permitindo a criação de documentos dinâmicos integrados aos microservices através do Jasper Reports.

Foram aplicados recursos como:

* Consultas em banco de dados;
* Parâmetros de entrada;
* Campos dinâmicos;
* Geração de relatórios em PDF.

### pgAdmin

O pgAdmin foi utilizado para administração e consulta dos bancos PostgreSQL de cada microservice, auxiliando no acompanhamento dos dados e validação das regras de negócio durante o desenvolvimento.

### Postman

O Postman foi utilizado para validação e testes das APIs REST, incluindo:

* Operações CRUD;
* Testes de integração entre microservices;
* Validação de payloads;
* Simulação de cenários de negócio;
* Testes dos endpoints responsáveis pela comunicação entre serviços.

### Ambiente de Desenvolvimento

O projeto foi desenvolvido seguindo uma arquitetura distribuída baseada em eventos, utilizando:

* Java
* Spring Boot
* Apache Kafka
* PostgreSQL
* Docker
* MinIO
* Jasper Reports
* OpenFeign
* Webhooks
* Event Driven Architecture (EDA)

com foco em escalabilidade, desacoplamento e aplicação de boas práticas utilizadas em ambientes corporativos.
