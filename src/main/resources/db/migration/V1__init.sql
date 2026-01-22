CREATE TABLE carro (
    id              BIGINT IDENTITY(1,1) NOT NULL,
    versao         BIGINT NOT NULL CONSTRAINT DF_car_version DEFAULT (0),

    marca           NVARCHAR(60)  NOT NULL,
    modelo          NVARCHAR(80)  NOT NULL,
    ano             INT           NOT NULL,
    preco           DECIMAL(18,2) NOT NULL,
    quilometragem   INT           NOT NULL,
    cor             NVARCHAR(30)  NULL,

    cambio          NVARCHAR(20)  NOT NULL,
    gasolina        NVARCHAR(20)  NOT NULL,
    status          NVARCHAR(20)  NOT NULL CONSTRAINT DF_car_status DEFAULT ('DISPONIVEL'),

    criado      DATETIMEOFFSET NOT NULL CONSTRAINT DF_carro_criado DEFAULT (SYSDATETIMEOFFSET()),
    atualizado  DATETIMEOFFSET NOT NULL CONSTRAINT DF_carro_atualizado DEFAULT (SYSDATETIMEOFFSET()),

    CONSTRAINT PK_carro PRIMARY KEY (id),

    CONSTRAINT CK_carro_ano CHECK ([ano] >= 1886),
    CONSTRAINT CK_carro_preco CHECK (preco >= 0),
    CONSTRAINT CK_carro_quilometragem CHECK (quilometragem >= 0),

    CONSTRAINT CK_carro_cambio CHECK (cambio IN ('MANUAL','AUTOMATICO')),
    CONSTRAINT CK_carro_gasolina CHECK (gasolina IN ('GASOLINA','ETANOL','FLEX','DIESEL','ELETRICO','HIBRIDO')),
    CONSTRAINT CK_carro_status CHECK (status IN ('DISPONIVEL','RESERVADO','VENDIDO'))
);
GO

CREATE INDEX IX_carro_status ON carro(status);
GO

CREATE INDEX IX_carro_marca_modelo ON carro(marca, modelo);
GO




CREATE TABLE cliente (
    id          BIGINT IDENTITY(1,1) NOT NULL,
    nome        NVARCHAR(120) NOT NULL,
    cpf         NVARCHAR(14)  NOT NULL,
    telefone    NVARCHAR(30)  NULL,
    email       NVARCHAR(120) NULL,

    CONSTRAINT PK_cliente PRIMARY KEY (id),
    CONSTRAINT UK_cliente_cpf UNIQUE (cpf)
);
GO




CREATE TABLE vendedor (
    id            BIGINT IDENTITY(1,1) NOT NULL,
    nome          NVARCHAR(120) NOT NULL,
    registro      NVARCHAR(40)  NOT NULL,

    CONSTRAINT PK_vendedor PRIMARY KEY (id),
    CONSTRAINT UK_vendedor_registro UNIQUE (registro)
);
GO




CREATE TABLE reserva (
    id            BIGINT IDENTITY(1,1) NOT NULL,

    carro_id      BIGINT NOT NULL,
    cliente_id    BIGINT NOT NULL,

    data_inicio   DATE NOT NULL,
    data_final    DATE NOT NULL,

    status        NVARCHAR(20) NOT NULL CONSTRAINT DF_reserva_status DEFAULT ('ATIVA'),

    CONSTRAINT PK_reserva PRIMARY KEY (id),

    CONSTRAINT CK_reserva_periodo CHECK (data_final >= data_inicio),
    CONSTRAINT CK_reserva_status CHECK (status IN ('ATIVA','CANCELADA','EXPIRADA')),

    CONSTRAINT FK_reserva_carro
        FOREIGN KEY (carro_id) REFERENCES carro(id),

    CONSTRAINT FK_reserva_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);
GO

CREATE INDEX IX_reserva_carro_status ON reserva(carro_id, status);
GO

CREATE INDEX IX_reserva_cliente ON reserva(cliente_id);
GO




CREATE TABLE venda (
    id           BIGINT IDENTITY(1,1) NOT NULL,

    carro_id     BIGINT NOT NULL,
    cliente_id   BIGINT NOT NULL,
    vendedor_id  BIGINT NULL,

    venda_data   DATETIMEOFFSET NOT NULL CONSTRAINT DF_venda_venda_data DEFAULT (SYSDATETIMEOFFSET()),
    venda_preco  DECIMAL(18,2) NOT NULL,
    forma_pagamento NVARCHAR(20) NOT NULL,

    CONSTRAINT PK_venda PRIMARY KEY (id),

    CONSTRAINT CK_venda_preco CHECK (venda_preco > 0),
    CONSTRAINT CK_venda_forma_pagamento CHECK (forma_pagamento IN ('PIX','CARTAO','DINHEIRO','FINANCIAMENTO')),

    CONSTRAINT FK_venda_carro
        FOREIGN KEY (carro_id) REFERENCES carro(id),

    CONSTRAINT FK_venda_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente(id),

    CONSTRAINT FK_venda_vendedor
        FOREIGN KEY (vendedor_id) REFERENCES vendedor(id)
);
GO

CREATE INDEX IX_venda_venda_data ON venda(venda_data);
GO

CREATE INDEX IX_venda_carro ON venda(carro_id);
GO

