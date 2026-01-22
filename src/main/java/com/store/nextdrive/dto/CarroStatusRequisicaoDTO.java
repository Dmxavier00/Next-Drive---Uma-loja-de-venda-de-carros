package com.store.nextdrive.dto;

import com.store.nextdrive.domain.enums.StatusCarro;
import jakarta.validation.constraints.NotNull;

public record CarroStatusRequisicaoDTO(
        @NotNull StatusCarro status
) {}
