INSERT INTO usuarios(id, nome) VALUES('599abd45-3f86-44b6-8837-fc16f130944e','Vitor Joaquim Leandro Lopes');
INSERT INTO usuarios(id, nome) VALUES('da09571d-9409-418a-8f49-d354552a6acb','Allana Aparecida Bruna Campos');

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
       CURRENT_DATE,
       CURRENT_TIME,
       5,
       'd5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3',
       '599abd45-3f86-44b6-8837-fc16f130944e',
       'CRIADA');

INSERT INTO reservas(id, data, hora, num_pessoas, restaurante_id, usuario_id, status_reserva)
VALUES('4bcf70fb-e7e4-44da-b369-ca3795adaf2a',
       CURRENT_DATE,
       CURRENT_TIME,
       97,
       'e0c65eb4-cb52-4df0-a582-44bc8ab756fb',
       'da09571d-9409-418a-8f49-d354552a6acb',
       'CRIADA');
