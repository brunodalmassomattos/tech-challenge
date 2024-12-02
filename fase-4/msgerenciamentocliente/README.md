# Microsserviço de Gerenciamento de Clientes

Este é o microsserviço responsável pelo gerenciamento de clientes do Sistema de Gerenciamento de Pedidos. O serviço é responsável por todas as operações relacionadas aos clientes, incluindo cadastro, atualização, busca e remoção de clientes e seus endereços.

## 🚀 Features

- Cadastro completo de clientes
- Gestão de endereços (principal e entrega)
- Validações de dados (CPF, email, telefone)
- Documentação Swagger/OpenAPI
- Integração com banco de dados PostgreSQL (Supabase)

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
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
│   │   └── br.com.fiap.msgerenciamentocliente/
│   │       ├── domain/
│   │       │   ├── application/
│   │       │   │   └── service/
│   │       │   ├── core/
│   │       │   │   ├── entity/
│   │       │   │   └── repository/
│   │       │   └── infrastructure/
│   │       │       ├── api/
│   │       │       └── persistence/
│   └── resources/
│       └── application.yml
```

## 🔧 Instalação e Execução

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
```

2. Configure as variáveis de ambiente para o Supabase:
- `password_supa` - Senha do banco de dados

3. Execute o projeto através da sua IDE:
- Abra o projeto no IntelliJ IDEA
- Localize a classe `GerenciamentoClienteApplication`
- Execute-a através do botão "Run"

## 📍 Endpoints

### Clientes
- POST /api/clientes - Criar novo cliente
- GET /api/clientes - Listar todos os clientes
- GET /api/clientes/{id} - Buscar cliente por ID
- GET /api/clientes/cpf/{cpf} - Buscar cliente por CPF
- PUT /api/clientes/{id} - Atualizar cliente
- DELETE /api/clientes/{id} - Remover cliente

### Endereços
- GET /api/enderecos - Listar endereços
- GET /api/enderecos/{id} - Buscar endereço por ID
- PUT /api/enderecos/{id} - Atualizar endereço
- DELETE /api/enderecos/{id} - Remover endereço

## 📚 Documentação

A documentação completa da API está disponível através do Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

## 🔍 Testes

O projeto inclui:
- Testes unitários
- Testes de integração
- Testes dos endpoints da API

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
