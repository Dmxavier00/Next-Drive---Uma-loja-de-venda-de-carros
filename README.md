# NextDrive
API REST para uma Loja de Carros (estoque, clientes, reservas e vendas) construída com **Java + Spring Boot** e **SQL Server**, com **migrations via Flyway**, validações, paginação e regras de negócio para status do veículo.

---

## Visão Geral
O **NextDrive** simula o backend de uma loja de veículos com fluxo completo:

- **Cadastro de carros**
- **Cadastro de clientes**
- **Cadastro de vendedores**
- **Reserva de veículo**
- **Venda de veículo**
- **Regras de status** (DISPONÍVEL → RESERVADO → VENDIDO)
- **Paginação e filtros** em listagens

> Objetivo: servir como projeto de estudo/portfólio com práticas reais de backend.

---

## Stack
- **Java 21**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA (Hibernate)
  - Bean Validation
- **SQL Server**
- **Flyway** (controle de schema)
- **Lombok**
- **Maven**

---

## Regras de Negócio
- Um carro só pode ser **reservado** se estiver `DISPONIVEL`.
- Ao reservar, o status do carro muda para `RESERVADO`.
- Ao cancelar reserva, a reserva vira `CANCELADA` e o carro volta para `DISPONIVEL` (se não estiver vendido).
- Um carro só pode ser **vendido** se **não** estiver `VENDIDO`.
- Ao vender, o carro muda para `VENDIDO`.
- Uma venda **não pode** ser registrada duas vezes para o mesmo carro.
- Se houver uma reserva ativa para o carro, ela é **cancelada** ao registrar a venda (regra do projeto).

---

## Modelagem (Entidades principais)
- **Carro**
- **Cliente**
- **Vendedor**
- **Reserva**
- **Venda**

Enums:
- `StatusCarro` → DISPONIVEL, RESERVADO, VENDIDO
- `StatusReserva` → ATIVA, CANCELADA, EXPIRADA
- `FormaPagamento` → PIX, CARTAO, DINHEIRO, FINANCIAMENTO
- `TipoCambio`, `TipoGasolina` (exemplos)

---

## Como Rodar o Projeto

### Pré-requisitos
- **Java 21**
- **Maven**
- **SQL Server** instalado e rodando localmente (porta padrão: 1433)
