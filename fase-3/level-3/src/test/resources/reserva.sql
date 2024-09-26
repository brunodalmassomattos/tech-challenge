INSERT INTO usuarios(id, nome) VALUES('599abd45-3f86-44b6-8837-fc16f130944e','Vitor Joaquim Leandro Lopes');

INSERT INTO restaurantes(id, nome, capacidade)
VALUES('d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3',
       'Boi de Ouro',
       120);
INSERT INTO restaurantes(id, nome, capacidade)
VALUES('e0c65eb4-cb52-4df0-a582-44bc8ab756fb',
       'Bistrô du Soleil',
       100);

INSERT INTO reservas(id, data, hora, num_pessoas, restaurante_id, usuario_id, status_reserva)
VALUES('0888fc78-9d9f-43eb-bfc6-4bba7741a0d0',
       '2024-09-24',
       CURRENT_TIME,
       5,
       'e0c65eb4-cb52-4df0-a582-44bc8ab756fb',
       '599abd45-3f86-44b6-8837-fc16f130944e',
       'CRIADA');