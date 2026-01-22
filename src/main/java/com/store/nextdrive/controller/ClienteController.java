package com.store.nextdrive.controller;

import com.store.nextdrive.dto.ClienteAtualizarRequisicaoDTO;
import com.store.nextdrive.dto.ClienteCriarRequisicaoDTO;
import com.store.nextdrive.dto.ClienteRespostaDTO;
import com.store.nextdrive.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ClienteRespostaDTO criar(@Valid @RequestBody ClienteCriarRequisicaoDTO requisicao) {
        return service.criar(requisicao);
    }

    @GetMapping("/{id}")
    public ClienteRespostaDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public Page<ClienteRespostaDTO> listar(
            @RequestParam(required = false) String nome,
            Pageable pageable
    ) {
        return service.listar(nome, pageable);
    }

    @PutMapping("/{id}")
    public ClienteRespostaDTO atualizar(@PathVariable Long id, @Valid @RequestBody ClienteAtualizarRequisicaoDTO requisicao) {
        return service.atualizar(id, requisicao);
    }
}
