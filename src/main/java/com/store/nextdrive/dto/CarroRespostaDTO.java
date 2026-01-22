package com.store.nextdrive.dto;

import com.store.nextdrive.domain.enums.StatusCarro;
import com.store.nextdrive.domain.enums.TipoCambio;
import com.store.nextdrive.domain.enums.TipoGasolina;

import java.math.BigDecimal;

public record CarroRespostaDTO(
        Long id,
        String marca,
        String modelo,
        Integer ano,
        BigDecimal preco,
        Integer quilometragem,
        String cor,
        TipoCambio cambio,
        TipoGasolina gasolina,
        StatusCarro status
) {}
