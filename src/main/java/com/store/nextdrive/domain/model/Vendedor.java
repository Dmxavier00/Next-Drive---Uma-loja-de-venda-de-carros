package com.store.nextdrive.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vendedor", uniqueConstraints = @UniqueConstraint(name = "uk_vendedor_registro", columnNames = "registro"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 40)
    private String registro;



    public Vendedor(String nome, String registro) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório");
        if (registro == null || registro.isBlank()) throw new IllegalArgumentException("Registro obrigatório");
        this.nome = nome;
        this.registro = registro;
    }

}