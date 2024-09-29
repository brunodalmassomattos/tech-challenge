Feature: Gerenciamento de Reservas

  Scenario: Listar reservas por restaurante
    Given um restaurante com ID "67208951-4a07-4226-b2b6-c500c78d4ba7"
    When o sistema lista as reservas para este restaurante
    Then o sistema retorna uma lista de reservas
    And o total de pessoas é 7
    And a capacidade do restaurante é 120
    And o restaurante pode aceitar mais reservas

  Scenario: Listar reserva por ID
    Given uma reserva existente com ID "4bcf70fb-e7e4-44da-b369-ca3795adaf2a"
    When o sistema busca a reserva por este ID
    Then o sistema retorna os detalhes da reserva
    And o status da reserva é "CONFIRMADA"

  Scenario: Atualizar o status de uma reserva para confirmada
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "CONFIRMADA"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "CONFIRMADA"

  Scenario: Atualizar o status de uma reserva para cancelada
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "CANCELADA"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "CANCELADA"

  Scenario: Atualizar o status de uma reserva para aguardando confirmação
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "AGUARDANDO_CONFIRMACAO"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "AGUARDANDO_CONFIRMACAO"

  Scenario: Atualizar o status de uma reserva para não compareceu
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "NAO_COMPARECEU"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "NAO_COMPARECEU"

  Scenario: Atualizar o status de uma reserva para em andamento
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "EM_ANDAMENTO"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "EM_ANDAMENTO"

  Scenario: Atualizar o status de uma reserva para concluida
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "CONCLUIDA"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "CONCLUIDA"

  Scenario: Atualizar o status de uma reserva para alterada
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "ALTERADA"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "ALTERADA"

  Scenario: Atualizar o status de uma reserva para expirada
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "EXPIRADA"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "EXPIRADA"

  Scenario: Atualizar o status de uma reserva para em espera
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "ESPERA"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "ESPERA"

  Scenario: Atualizar o status de uma reserva para criada
    Given uma reserva existente com ID "0888fc78-9d9f-43eb-bfc6-4bba7741a0d0"
    When o status da reserva é atualizado para "CRIADA"
    Then o status da reserva é atualizado com sucesso
    And o novo status da reserva é "CRIADA"

  Scenario: Atualizar o status de uma reserva inexistente
    Given uma reserva inexistente com ID "9999aaaa-bbbb-cccc-dddd-eeeeffffffff"
    When o sistema tenta atualizar o status da reserva para "CONFIRMADA"
    Then o sistema retorna uma mensagem indicando que a reserva não foi encontrada

  Scenario: Registrar nova reserva
    When uma nova reserva é enviada
    Then registrar nova reserva
    And reserva registrada com sucesso é retornada