# Desafio API Banco - Cooperativa de Crédito

API RESTful desenvolvida com Spring Boot para gerenciamento de correntistas, contas bancárias (corrente e poupança) e transações financeiras.

## Tecnologias utilizadas

- Java 8
- Spring Boot 2.7.18
- Spring Data JPA / Hibernate
- H2 Database (em memória, para desenvolvimento/testes)
- Maven

## Como rodar o projeto localmente

### Pré-requisitos
- JDK 8 instalado
- Maven (ou usar o Maven embutido do VS Code/IDE)

### Passos

1. Clone o repositório:

   ```
   git clone https://github.com/joaovittormonteiro/desafio-api-banco-spring.git
   ```

2. Entre na pasta do projeto:

   ```
   cd desafio-api-banco-spring
   ```

3. Rode a aplicação:

   ```
   mvn spring-boot:run
   ```

4. A API sobe em `http://localhost:8080`

O banco H2 é criado automaticamente em memória ao subir a aplicação — não precisa rodar nenhum script SQL manualmente. As tabelas são geradas pelo Hibernate a partir das entidades JPA (`spring.jpa.hibernate.ddl-auto=update`).

Para trocar para MySQL, basta ajustar as propriedades de conexão em `src/main/resources/application.properties`.

## Modelo de dados

- **Correntista**: nome, documento, email, telefone — pode ter várias contas
- **Conta** (abstrata): número, saldo — especializada em:
  - **ContaCorrente**: possui limite; saque permitido até saldo + limite
  - **ContaPoupanca**: saque permitido apenas até o saldo
- **Transacao**: tipo (DEPOSITO/SAQUE), valor, data, conta de origem

## Endpoints disponíveis

### Correntistas

**Cadastrar correntista**

```
POST /correntistas
Content-Type: application/json

{
    "nome": "João Vittor",
    "documento": "12345678900",
    "email": "joao@teste.com",
    "telefone": "83999999999"
}
```

**Listar todos os correntistas**

```
GET /correntistas
```

**Buscar correntista por id**

```
GET /correntistas/{id}
```

### Contas

**Abrir conta** (corrente ou poupança)

```
POST /contas?correntistaId={id}
Content-Type: application/json

{
    "numero": "0001-1",
    "tipo": "CORRENTE",
    "limite": 500.00
}
```

Para conta poupança, use `"tipo": "POUPANCA"` (sem o campo `limite`).

**Buscar conta por id**

```
GET /contas/{id}
```

**Depositar**

```
POST /contas/{id}/depositar
Content-Type: application/json

{
    "valor": 1000.00
}
```

**Sacar**

```
POST /contas/{id}/sacar
Content-Type: application/json

{
    "valor": 200.00
}
```

**Extrato (listagem de transações)**

```
GET /contas/{id}/extrato
```

## Tratamento de erros

A API retorna erros padronizados em JSON, com status HTTP apropriado:

```json
{
    "timestamp": "2026-09-21T23:11:48.968",
    "status": 400,
    "erro": "Bad Request",
    "mensagem": "Saldo insuficiente para saque."
}
```

- `404` — recurso não encontrado (correntista ou conta inexistente)
- `400` — regra de negócio violada (ex: saque maior que saldo + limite permitido)

## O que foi feito

- Cadastro e consulta de correntistas
- Abertura e consulta de contas (com herança Conta → ContaCorrente/ContaPoupanca)
- Depósito e saque, com validação de regras de negócio por tipo de conta
- Extrato de transações por conta
- Tratamento de erros padronizado

## O que ficou de fora (e como seria feito)

- **Rendimento mensal na poupança / juros sobre saldo negativo na corrente**: seriam implementados como endpoints adicionais no `ContaController`, recebendo a taxa como parâmetro, aplicando o cálculo sobre o saldo da conta e registrando uma `Transacao` do tipo `RENDIMENTO`/`JUROS` (o enum já contempla esses tipos).
- **Testes unitários**: seriam escritos com JUnit + Mockito, cobrindo principalmente a lógica de `podeSacar()` de cada subtipo de conta e os métodos do `ContaService`.
- **Swagger/OpenAPI**: seria adicionado via dependência `springdoc-openapi-ui`, documentando automaticamente os endpoints existentes.
