package com.store.nextdrive.dto;

import com.store.nextdrive.domain.enums.FormaPagamento;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record VendaRespostaDTO(
        Long id,
        Long carroId,
        Long clienteId,
        Long vendedorId,
        OffsetDateTime vendaData,
        BigDecimal vendaPreco,
        FormaPagamento formaPagamento
) {
}
