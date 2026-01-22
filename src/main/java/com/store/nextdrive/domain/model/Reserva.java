package com.store.nextdrive.domain.model;

import com.store.nextdrive.domain.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Carro carro;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFinal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusReserva status = StatusReserva.ATIVA;


    public Reserva(Carro carro, Cliente cliente, LocalDate dataInicio, LocalDate dataFinal) {
        this.carro = carro;
        this.cliente = cliente;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        validar();
    }

    public void cancelar() {
        if (status != StatusReserva.ATIVA) {
            throw new IllegalStateException("Reserva não está ATIVA para cancelar");
        }
        status = StatusReserva.CANCELADA;
    }

    private void validar() {
        if (carro == null) throw new IllegalArgumentException("Carro obrigatório");
        if (cliente == null) throw new IllegalArgumentException("Cliente obrigatório");
        if (dataInicio == null || dataFinal == null) throw new IllegalArgumentException("Datas obrigatórias");
        if (dataFinal.isBefore(dataInicio)) throw new IllegalArgumentException("Período inválido");
    }

}