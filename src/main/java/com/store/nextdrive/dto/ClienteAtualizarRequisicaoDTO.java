package com.store.nextdrive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteAtualizarRequisicaoDTO(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Size(max = 14) String cpf,
        @Size(max = 30) String telefone,
        @Size(max = 120) String email
) { }
