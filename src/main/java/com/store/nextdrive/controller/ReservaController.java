package com.store.nextdrive.controller;


import com.store.nextdrive.domain.enums.StatusReserva;
import com.store.nextdrive.dto.ReservaCriarRequisicaoDTO;
import com.store.nextdrive.dto.ReservaRespostaDTO;
import com.store.nextdrive.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @PostMapping
    public ReservaRespostaDTO criar(@Valid @RequestBody ReservaCriarRequisicaoDTO request) {
        return service.criar(request);
    }

    @GetMapping("/{id}")
    public ReservaRespostaDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public Page<ReservaRespostaDTO> listar(
            @RequestParam(required = false) StatusReserva status,
            @RequestParam(required = false) Long carroId,
            @RequestParam(required = false) Long clienteId,
            Pageable pageable
    ) {
        return service.listar(status, carroId, clienteId, pageable);
    }

    @PostMapping("/{id}/cancelar")
    public ReservaRespostaDTO cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}
