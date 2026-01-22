package com.store.nextdrive.dto;

import com.store.nextdrive.domain.enums.FormaPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record VendaCriarRequisicaoDTO(
        @NotNull Long carroId,
        @NotNull Long clienteId,
        @NotNull Long vendedorId,
        @NotNull FormaPagamento formaPagamento,
        @NotNull @DecimalMin("0.01") BigDecimal vendaPreco
) {
}
