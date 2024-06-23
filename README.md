# TECH-CHALLENGE

Seja bem-vindo ao repositorio Tech Challenge FIAP!

O objetivo deste respo é organizar os projetos dos desefios da fiap para o curso de Pos-Tech. Onde esta divido por fase e projeto.

```console
curl --location 'http://localhost:8080/user' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Renato Gabriel Barros",
    "cpfCnpj": "871.109.390-06",
    "dataNascimento": "1991-01-02",
    "telefone": "(82) 99401-8514",
    "idFormaDePagamento": "2c7d9ead-401d-4771-a485-3a8a631d475d",
    "endereco": {
        "logradouro": "Rua E-3",
        "numero": "853",
        "complemento": "",
        "bairro": "Benedito Bentes",
        "cidade": "Maceió",
        "estado": "AL",
        "cep": "79116-060"
    },
    "veiculos": [
        {
            "fabricante": "CBT Jipe",
            "modelo": "Javali 3.0 4x4 Diesel",
            "placa": "MVE-7244",
            "cor": "Branco",
            "ano": "1988"
        },
        {
            "fabricante": "Renault",
            "modelo": "Clio Authentique Hi-Flex 1.6 16V 3p",
            "placa": "KNK-1789",
            "cor": "Vermelho",
            "ano": "2005"
        }
    ]

}'
```
