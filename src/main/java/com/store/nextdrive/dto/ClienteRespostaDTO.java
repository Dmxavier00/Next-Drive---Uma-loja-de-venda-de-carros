package com.store.nextdrive.dto;

public record ClienteRespostaDTO(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email
) { }
