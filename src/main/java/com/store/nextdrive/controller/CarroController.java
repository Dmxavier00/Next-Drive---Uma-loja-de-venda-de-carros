package com.store.nextdrive.controller;

import com.store.nextdrive.domain.enums.StatusCarro;
import com.store.nextdrive.dto.CarroAtualizarRequisicaoDTO;
import com.store.nextdrive.dto.CarroCriarRequisicaoDTO;
import com.store.nextdrive.dto.CarroRespostaDTO;
import com.store.nextdrive.dto.CarroStatusRequisicaoDTO;
import com.store.nextdrive.service.CarroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carro")
public class CarroController {

    private final CarroService service;

    public CarroController(CarroService service) {
        this.service = service;
    }

    @PostMapping
    public CarroRespostaDTO criar(@Valid @RequestBody CarroCriarRequisicaoDTO requisicao) {
        return service.criar(requisicao);
    }

    @GetMapping("/{id}")
    public CarroRespostaDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public Page<CarroRespostaDTO> listar(
            @RequestParam(required = false) StatusCarro status,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            Pageable pageable
    ) {
        return service.listar(status, marca, modelo, pageable);
    }

    @PutMapping("/{id}")
    public CarroRespostaDTO atualizar(@PathVariable Long id, @Valid @RequestBody CarroAtualizarRequisicaoDTO requisicao) {
        return service.atualizar(id, requisicao);
    }

    @PatchMapping("/{id}/status")
    public CarroRespostaDTO atualizarStatus(@PathVariable Long id, @Valid @RequestBody CarroStatusRequisicaoDTO requisicao) {
        return service.atualizarStatus(id, requisicao.status());
    }
}