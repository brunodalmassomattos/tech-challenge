# FASE 3

----

## Tech Challenge

Tech Challenge é o projeto que englobará os conhecimentos obtidos em
todas as disciplinas da fase. Esta é uma atividade que, em princípio, deve ser
desenvolvida em grupo. Importante atentar-se ao prazo de entrega, pois trata-se
de uma atividade obrigatória que vale nota em todas as disciplinas da fase.

### O problema

Criar um sistema de Reserva e Avaliação de Restaurantes.

### &nbsp;&nbsp;&nbsp; Funcionalidades Básicas:
1. Cadastro de Restaurantes: Os restaurantes podem se cadastrar no
   sistema, fornecendo informações como nome, localização, tipo de
   cozinha, horários de funcionamento e capacidade.
2. Reserva de Mesas: Os usuários podem fazer reservas para datas e
   horários específicos.
3. Avaliações e Comentários: Após a visita, os usuários podem avaliar
   o restaurante e deixar comentários sobre sua experiência.
4. Busca de Restaurantes: Os usuários podem buscar restaurantes por
   nome, localização ou tipo de cozinha.
5. Gerenciamento de Reservas: Os restaurantes podem gerenciar as
   reservas, visualizando e atualizando o status das mesas.

### O que será avaliado
### &nbsp;&nbsp;&nbsp; Clean Architecture:
1. Design Arquitetônico: Estruturar o backend de acordo com a Clean
   Architecture, definindo entidades, regras de negócio, adaptadores de
   interface e frameworks.
2. Clean Code em Testes: Implementar práticas de código limpo em
   todas as fases do desenvolvimento, com foco especial em testes.
   Qualidade de Software:
1. Testes Unitários (TDD com JUnit por exemplo): Desenvolver o
   backend utilizando TDD, focando em cobrir todos os aspectos críticos
   da aplicação.
2. Testes Integrados e Inspeção de Código: Realizar testes integrados
   para garantir que todas as partes do sistema funcionem juntas, e usar
   ferramentas de inspeção de código para manter a qualidade.
3. Testes de Integração no Controller, CI e BDD: Testar os controllers
   para garantir que a lógica de negócios esteja correta. Integrar os testes
   no pipeline de CI e adotar BDD para melhorar a comunicação entre a
   equipe.
4. Testes Não Funcionais: Incluir testes de carga e desempenho para
   garantir que o sistema possa lidar com um grande número de usuários
   e reservas.
5. Medir cobertura de teste: Usar o coverage para metrificar a
   porcentagem de testes realizados.

### Deploy:
   Ambientes Diversos de deploy: Realizar deploy do sistema em diferentes
   ambientes, incluindo local, AWS ECS, Azure e uma plataforma de nuvem gratuita
   como Heroku, Render, entre outros.

Boa sorte!

---

# Entendendo a Estrutura com o Pacote application

A estrutura que propomos se baseia na divisão em camadas, com cada camada tendo responsabilidades bem definidas e interagindo de forma desacoplada. 

## Camadas e suas Responsabilidades

### Camada de Apresentação (Presentation Layer)

&nbsp;&nbsp; **Controllers:** Recebem requisições HTTP e delegam o processamento para a camada de serviço. <br>
&nbsp;&nbsp; **DTOs (Data Transfer Objects):** Representam os dados que serão transmitidos entre as camadas, garantindo o desacoplamento. <br>

### Camada de Aplicação (Application Layer)

&nbsp;&nbsp; **Services:** Contêm a lógica de negócio da aplicação, coordenando as operações e utilizando os repositórios para acessar os dados. <br>

### Camada de Domínio (Domain Layer)

&nbsp;&nbsp; **Entities:** Representam os conceitos do domínio, como Restaurante, Usuario, Reserva, etc. <br>
&nbsp;&nbsp; **Repositories:** Definem os contratos para acesso aos dados, mas não implementam a lógica de persistência. <br>

### Camada de Infraestrutura (Infrastructure Layer)
&nbsp;&nbsp; **Persistence:** Implementa os repositórios, utilizando um ORM como Hibernate para interagir com o banco de dados.

### Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Docker**
- **Render** (para hospedagem)
- **AWS** (para hospedagem)
- **Supabase** (PostgreSQL como banco de dados)

## Estrutura de Pacotes Detalhada

````
|src/main/java/com/restaurante/
|--application/
|----controller/
|----service/
|--domain/
|----dominio/
|------application/
|------core/
|--------model/
|--------ports/
|----------incoming/
|----------outcoming/
|------infrastuture/
|--infrastructure/
|----persistence/
````
## Princípios SOLID
S - Single Responsibility Principle: Cada classe deve ter uma única responsabilidade.<br>
O - Open-Closed Principle: As classes devem estar abertas para extensão, mas fechadas para modificação.<br>
L - Liskov Substitution Principle: Subclasses devem ser substituíveis por suas superclasses.<br>
I - Interface Segregation Principle: Interfaces devem ser específicas e não forçar clientes a depender de métodos que não usam.<br>
D - Dependency Inversion Principle: Dependências devem apontar para abstrações, não para implementações concretas.<br>

## Benefícios dessa Estrutura
- Alta Coesão: Cada camada tem responsabilidades bem definidas. <br>
- Baixo Acoplamento: As camadas são desacopladas, facilitando a manutenção e testes. <br>
- Reusabilidade: A lógica de negócio está isolada na camada de domínio. <br>
- Testabilidade: É fácil escrever testes unitários para cada camada. <br>
- Extensibilidade: É fácil adicionar novas funcionalidades sem impactar outras partes do sistema.

## Construção do Projeto
Certifique-se de que todas as dependências Maven estão instaladas:
   ````
      mvn clean install
   ````

### Executando com Docker
Construa a imagem Docker e inicie o container:
   ````
         docker build level3 .
         docker run --name level3-container -p 8080:8080 -e password_supa='grupo29@@2024' -it level3
   ````

### Acessando o SWAGGER
Após iniciar o container, acesse a interface Swagger para interagir com a API através do navegador: 
[Documentação do Swagger](http://localhost:8080/swagger-ui/index.html#/)
