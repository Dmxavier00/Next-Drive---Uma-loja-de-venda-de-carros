package com.store.nextdrive.domain.model;

import com.store.nextdrive.domain.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id", nullable = false)
    private Carro carro;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    @Column(name = "venda_data", nullable = false)
    private OffsetDateTime vendaData = OffsetDateTime.now();

    @Column(name = "venda_preco", nullable = false, precision = 18, scale = 2)
    private BigDecimal vendaPreco;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false, length = 20)
    private FormaPagamento formaPagamento;


    public Venda(Carro carro, Cliente cliente, Vendedor vendedor, BigDecimal vendaPreco, FormaPagamento formaPagamento) {
        if (carro == null) throw new IllegalArgumentException("Carro obrigatório");
        if (cliente == null) throw new IllegalArgumentException("Cliente obrigatório");
        if (vendedor == null) throw new IllegalArgumentException("Vendedor obrigatório");
        if (vendaPreco == null || vendaPreco.signum() <= 0) throw new IllegalArgumentException("Preço de venda inválido");
        if (formaPagamento == null) throw new IllegalArgumentException("Tipo de pagamento obrigatório");

        this.carro = carro;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.vendaPreco = vendaPreco;
        this.formaPagamento = formaPagamento;
    }


}
