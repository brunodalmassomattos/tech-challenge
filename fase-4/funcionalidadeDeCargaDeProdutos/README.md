# Carga de Produtos Microserviço

Este repositório contém um microserviço desenvolvido em **Spring Boot** que utiliza **Spring Batch** para realizar a importação de produtos a partir de um arquivo CSV para um banco de dados **PostgreSQL** hospedado no **Supabase**. O microserviço permite agendar a importação automaticamente ou iniciá-la manualmente através de um endpoint REST.

---

## Descrição do Projeto

O microserviço foi desenvolvido para automatizar a carga de produtos em um banco de dados PostgreSQL. Ele lê dados de um arquivo CSV, processa e valida as informações, e as insere no banco de dados. Além disso, o microserviço permite o agendamento da importação através de uma expressão cron configurável e também oferece um endpoint REST para iniciar o processo manualmente.

---

## Funcionalidades

- **Importação de Produtos**: Lê um arquivo CSV contendo informações de produtos e os insere no banco de dados.
- **Agendamento**: Permite agendar a importação usando uma expressão cron configurável.
- **Execução Manual**: Disponibiliza um endpoint REST para iniciar a importação manualmente.
- **Validações**: Realiza validações dos dados, como preço e quantidade em estoque não negativos.
- **Logs**: Registra logs detalhados para facilitar o monitoramento e depuração.
- **Testes**: Inclui testes unitários e de integração para garantir a qualidade do código.

---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.0**
- **Spring Batch**
- **Spring Data JPA**
- **PostgreSQL (Supabase)**
- **Lombok**
- **JUnit 5**
- **Mockito**
- **H2 Database (para testes)**

---

## Pré-requisitos

- **Java 17** instalado
- **Maven 3.6+** instalado
- Conta no **Supabase** com um banco de dados PostgreSQL configurado
- **Git** instalado (para clonar o repositório)

---

## Configuração do Ambiente

Antes de executar a aplicação, é necessário configurar o ambiente adequadamente.

### 1. Clonar o Repositório

```bash
git clone https://github.com/brunodalmassomattos/tech-challenge/tree/feature/funcionalidadeDeCargaDeProdutos
```

2. Configure as variáveis de ambiente para o Supabase:
- `password_supa` - Senha do banco de dados

3. Execute o projeto através da sua IDE:
- Abra o projeto no IntelliJ IDEA
- Localize a classe `FuncionalidadeDeCargaDeProdutosApplication`
- Execute-a através do botão "Run"

4. Preparar o Arquivo CSV
Coloque o arquivo produtos.csv no diretório src/main/resources/input/ seguindo o padrão abaixo:

```bash
nome,descricao,preco,qtdEstoque,categoriaDescricao
Smartphone ABC,Um smartphone avançado,2999.99,50,Eletrônicos
Notebook ABC,Notebook potente para trabalho,4999.99,30,Informática
```

5. Executar o Job Manualmente
Para iniciar o job de importação manualmente, faça uma requisição POST para o endpoint /jobs/import. Utilize o Postman ou Insomnia para enviar uma requisição POST para o endpoint.

```bash
curl -X POST http://localhost:8080/jobs/import
```

---

## Testes

O projeto inclui:
- Testes unitários
- Testes de integração
- Testes dos endpoints da API

Para executar os testes via IDE:
1. Navegue até a pasta de testes
2. Execute os testes usando a funcionalidade da IDE

## Banco de Dados

O projeto utiliza PostgreSQL através do Supabase como banco de dados principal.

## Contribuição

1. Faça o fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Faça commit das mudanças (`git commit -m 'Adiciona nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request
