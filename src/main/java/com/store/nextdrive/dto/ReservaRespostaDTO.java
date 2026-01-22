package com.store.nextdrive.dto;

import com.store.nextdrive.domain.enums.StatusReserva;
import java.time.LocalDate;

public record ReservaRespostaDTO(
        Long id,
        Long carroId,
        Long clienteId,
        LocalDate dataInicio,
        LocalDate dataFinal,
        StatusReserva status
) { }
