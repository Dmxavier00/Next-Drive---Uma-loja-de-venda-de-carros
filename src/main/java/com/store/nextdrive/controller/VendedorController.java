package com.store.nextdrive.controller;


import com.store.nextdrive.dto.VendedorAtualizarRequisicaoDTO;
import com.store.nextdrive.dto.VendedorCriarRequisicaoDTO;
import com.store.nextdrive.dto.VendedorRespostaDTO;
import com.store.nextdrive.service.VendedorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    private final VendedorService service;

    public VendedorController(VendedorService service) {
        this.service = service;
    }

    @PostMapping
    public VendedorRespostaDTO criar(@Valid @RequestBody VendedorCriarRequisicaoDTO request) {
        return service.criar(request);
    }

    @GetMapping("/{id}")
    public VendedorRespostaDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public Page<VendedorRespostaDTO> listar(
            @RequestParam(required = false) String nome,
            Pageable pageable
    ) {
        return service.listar(nome, pageable);
    }

    @PutMapping("/{id}")
    public VendedorRespostaDTO atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VendedorAtualizarRequisicaoDTO request
    ) {
        return service.atualizar(id, request);
    }
}
