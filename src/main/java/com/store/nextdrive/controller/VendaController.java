package com.store.nextdrive.controller;

import com.store.nextdrive.dto.VendaCriarRequisicaoDTO;
import com.store.nextdrive.dto.VendaRespostaDTO;
import com.store.nextdrive.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venda")
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @PostMapping
    public VendaRespostaDTO criar(@Valid @RequestBody VendaCriarRequisicaoDTO request) {
        return service.criar(request);
    }

    @GetMapping("/{id}")
    public VendaRespostaDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public Page<VendaRespostaDTO> listar(
            @RequestParam(required = false) Long carroId,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long vendedorId,
            Pageable pageable
    ) {
        return service.listar(carroId, clienteId, vendedorId, pageable);
    }
}
