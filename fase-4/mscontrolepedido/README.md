# Microsserviço Controle de Pedido

Este é o microsserviço responsável pelo gerenciamento dos pedidos. O serviço é responsável por criar e controlar o pedido até a entrega ao usuário.

## 🚀 Features

- Consumir eventos via Kafka o rastreio.
- Enviar confirmações de pagamentos via Kafka.
- Buscar produtos utilizando OpenFeign.
- Documentação Swagger/OpenAPI.
- Integração com banco de dados PostgreSQL (Supabase).

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Spring Cloud
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
│   │   └── br.com.fiap.pedido/
│   │       ├── domain/
│   │       │   ├── application/
│   │       │   │   ├── controller/
│   │       │   │   ├── dto/
│   │       │   ├── domain/
│   │       │   │   ├── models/
│   │       │   │   └── service/
│   │       │   └── persistence/
│   │       │       ├── external.client/
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

### Pedido
- POST /api/pedido - Cria pedido
- POST /api/pedido/atualiza/id/{idPedido} - Atualiza pedido
- POST /api/pedido/atualiza/status/{idNota} - Atualiza status pedido
- GET /api/pedido/busca/usuario/{idUsuario} - Busca pedido por usuário
- GET /api/pedido/busca/status/{status} - Busca pedido por status 
- GET /api/pedido/busca/notafiscal/{idNota} - Busca pedido pela nota fiscal
- DEL /api/pedido/deleta/id/{idPedido} - Deleta pelo id do pedido 
- DEL /api/pedido/deleta/notafiscal/{idNota} - Delata pedido pela nota fiscal

## 📚 Documentação

A documentação completa da API está disponível através do Swagger UI:
```
http://localhost:8082/swagger-ui/index.html#
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
