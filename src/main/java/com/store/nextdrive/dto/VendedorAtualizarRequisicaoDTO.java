package com.store.nextdrive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VendedorAtualizarRequisicaoDTO(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Size(max = 40) String registro
) {
}
