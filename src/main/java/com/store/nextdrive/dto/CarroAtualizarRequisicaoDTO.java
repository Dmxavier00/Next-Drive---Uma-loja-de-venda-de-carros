package com.store.nextdrive.dto;

import com.store.nextdrive.domain.enums.TipoCambio;
import com.store.nextdrive.domain.enums.TipoGasolina;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CarroAtualizarRequisicaoDTO(
        @NotBlank @Size(max = 60) String marca,
        @NotBlank @Size(max = 80) String modelo,
        @NotNull @Min(1886) Integer ano,
        @NotNull @DecimalMin("0.00") BigDecimal preco,
        @NotNull @Min(0) Integer quilometragem,
        @Size(max = 30) String cor,
        @NotNull TipoCambio cambio,
        @NotNull TipoGasolina gasolina
) {}
