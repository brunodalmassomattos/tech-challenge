# Microsserviço de Logística de Entregas

Este é o microsserviço responsável pela Logística de Entrega do Sistema de Gerenciamento de Pedidos. O serviço é responsável por separar pedidos em lotes de entrega de acordo com o Cep do endereço de entrega do usuário.

## 🚀 Features

- Consumir eventos via Kafka de pedidos pagos para criar entrega
- Enviar código de rastreio utilizando Kafka
- Buscar cliente via Spring Integration
- Documentação Swagger/OpenAPI
- Integração com banco de dados PostgreSQL (Supabase)

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Integration
- Spring Kafka
- PostgreSQL (Supabase)
- Swagger/OpenAPI
- Maven

## 📋 Pré-requisitos

- Java 17
- Maven 3.6+
- IDE (recomendado: IntelliJ IDEA)
- Variáveis de ambiente configuradas para o Supabase

## ⚙️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br.com.fiap.mslogistica/
│   │       ├── domain/
│   │       │   ├── application/
│   │       │   │   ├── controller/
│   │       │   │   ├── dto/
│   │       │   │   ├── enumerator/
│   │       │   │   ├── event/
│   │       │   │   ├── exception/
│   │       │   │   ├── gateway/
│   │       │   │   └── service/
│   │       │   ├── domain/
│   │       │   │   ├── entity/
│   │       │   │   ├── repository/
│   │       │   │   └── service/
│   │       │   └── persistence/
│   │       │       ├── data/
│   │       │       └── repository/
│   └── resources/
│       └── application.properties
```

## 🔧 Instalação e Execução

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
```

2. Configure as variáveis de ambiente para o Supabase:
- `password_supa` - Senha do banco de dados

3. Execute o projeto através da sua IDE

## 📍 Endpoints

### Clientes
- PUT /entrega/atualizar-localizacao/{id} - Atualizar localização da entrega
- PUT /entrega/atualizar-localizacao-lote/{loteId} - Atualizar localização do lote de entregas
- PUT /entrega/atribuir-transportadora/{loteId} - Atribuir transportadora ao lote de entregas
- PUT /entrega/atribuir-entregador/{id} - Atribuir entregador à entrega
- PUT /entrega/alterar-situacao/{id} - Alterar situação da entrega
- PUT /entrega/alterar-situacao-lote/{loteId} - Alterar situação do lote de entregas
- GET /entrega/entregas-lote/{loteId} - Buscar entregas de lote
- GET /entrega/entrega-codigoRastreio/{codigoRastreio} - Buscar entrega por código de rastreio

## 📚 Documentação

A documentação completa da API está disponível através do Swagger UI:
```
http://localhost:8089/swagger-ui/index.html#
```

## 🔍 Testes

O projeto inclui:
- Testes unitários

Para executar os testes via IDE:
1. Navegue até a pasta de testes
2. Execute os testes usando a funcionalidade da IDE

## 🗃️ Banco de Dados

O projeto utiliza PostgreSQL através do Supabase como banco de dados principal.

## 🤝 Contribuição

1. Faça o fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Faça commit das mudanças (`git commit -m 'Adiciona nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT.
