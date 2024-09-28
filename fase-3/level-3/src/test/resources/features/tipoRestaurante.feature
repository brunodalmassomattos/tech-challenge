Feature: Gerenciamento de Tipos de Restaurante

  Scenario: Buscar um tipo de restaurante por ID
    Given um tipo de restaurante existente com ID "40d4e2bc-b412-415d-8f68-0ccad5a8afad"
    When o sistema busca o tipo de restaurante por este ID
    Then o sistema retorna os detalhes do tipo de restaurante
    And o tipo de restaurante é "Restaurante Italiano"

  Scenario: Alterar um tipo de restaurante existente
    Given um tipo de restaurante existente com ID "0994cf72-3130-4675-b736-2e3c4fc4f7c2"
    When o sistema altera o tipo de restaurante com os dados "Restaurante Japonês"
    Then o sistema confirma que o tipo de restaurante foi alterado

  Scenario: Listar todos os tipos de restaurante
    When o sistema lista todos os tipos de restaurante
    Then o sistema retorna uma lista de tipos de restaurante

  Scenario: Buscar tipos de restaurante pela descrição
    When o sistema busca tipos de restaurante pela descrição "italiano"
    Then o sistema retorna uma lista vazia de tipos de restaurante
