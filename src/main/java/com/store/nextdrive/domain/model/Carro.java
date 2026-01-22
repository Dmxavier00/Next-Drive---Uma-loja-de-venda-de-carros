package com.store.nextdrive.domain.model;

import com.store.nextdrive.domain.enums.StatusCarro;
import com.store.nextdrive.domain.enums.TipoCambio;
import com.store.nextdrive.domain.enums.TipoGasolina;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "carro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long versao; // optimistic locking p/ evitar 2 vendas/reservas simultâneas

    @Column(nullable = false, length = 60)
    private String marca;

    @Column(nullable = false, length = 80)
    private String modelo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quilometragem;

    @Column(length = 30)
    private String cor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoCambio cambio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoGasolina gasolina;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusCarro status = StatusCarro.DISPONIVEL;

    @Column(nullable = false)
    private OffsetDateTime criado = OffsetDateTime.now();

    @Column(nullable = false)
    private OffsetDateTime atualizado = OffsetDateTime.now();


    public Carro(String marca, String modelo, Integer ano, BigDecimal preco, Integer quilometragem,
               String cor, TipoCambio cambio, TipoGasolina gasolina) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.quilometragem = quilometragem;
        this.cor = cor;
        this.cambio = cambio;
        this.gasolina = gasolina;
        validar();
    }

    public void atualizarDados(String marca,
                               String modelo,
                               Integer ano,
                               BigDecimal preco,
                               Integer quilometragem,
                               String cor,
                               TipoCambio cambio,
                               TipoGasolina gasolina) {

        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.quilometragem = quilometragem;
        this.cor = cor;
        this.cambio = cambio;
        this.gasolina = gasolina;

        validar();
        mexido();
    }


    public void atualizarPreco(BigDecimal novoPreco) {
        if (novoPreco == null || novoPreco.signum() < 0) throw new IllegalArgumentException("Preço inválido");
        this.preco = novoPreco;
        mexido();
    }

    public void marcarReservado() {
        if (this.status != StatusCarro.DISPONIVEL) throw new IllegalStateException("Carro não está disponível para reserva");
        this.status = StatusCarro.RESERVADO;
        mexido();
    }

    public void marcarDisponivel() {
        if (this.status == StatusCarro.VENDIDO) throw new IllegalStateException("Carro vendido não pode voltar a disponível");
        this.status = StatusCarro.DISPONIVEL;
        mexido();
    }

    public void marcarVendido() {
        if (this.status == StatusCarro.VENDIDO) throw new IllegalStateException("Carro já está vendido");
        this.status = StatusCarro.VENDIDO;
        mexido();
    }

    private void validar() {
        if (marca == null || marca.isBlank()) throw new IllegalArgumentException("Marca obrigatória");
        if (modelo == null || modelo.isBlank()) throw new IllegalArgumentException("Modelo obrigatório");
        if (ano == null || ano < 1886) throw new IllegalArgumentException("Ano inválido");
        if (preco == null || preco.signum() < 0) throw new IllegalArgumentException("Preço inválido");
        if (quilometragem == null || quilometragem < 0) throw new IllegalArgumentException("KM inválida");
        if (cambio == null) throw new IllegalArgumentException("Câmbio obrigatório");
        if (gasolina == null) throw new IllegalArgumentException("Combustível obrigatório");
    }

    private void mexido() {
        this.atualizado = OffsetDateTime.now();
    }


}