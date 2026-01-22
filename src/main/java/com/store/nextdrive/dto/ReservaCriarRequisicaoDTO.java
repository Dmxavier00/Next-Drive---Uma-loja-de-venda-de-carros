package com.store.nextdrive.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservaCriarRequisicaoDTO(
        @NotNull Long carroId,
        @NotNull Long clienteId,
        @NotNull LocalDate dataInicio,
        @NotNull LocalDate dataFinal

) { }
