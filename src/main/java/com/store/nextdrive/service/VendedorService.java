package com.store.nextdrive.service;


import com.store.nextdrive.domain.model.Vendedor;
import com.store.nextdrive.dto.VendedorAtualizarRequisicaoDTO;
import com.store.nextdrive.dto.VendedorCriarRequisicaoDTO;
import com.store.nextdrive.dto.VendedorRespostaDTO;
import com.store.nextdrive.exception.ResourceNotFoundException;
import com.store.nextdrive.repository.VendedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VendedorService {

    private final VendedorRepository repository;

    public VendedorService(VendedorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public VendedorRespostaDTO criar(VendedorCriarRequisicaoDTO request) {
        if (repository.existsByRegistro(request.registro())) {
            throw new IllegalStateException("Já existe vendedor com este registro");
        }

        Vendedor vendedor = new Vendedor(request.nome(), request.registro());
        vendedor = repository.save(vendedor);

        return toResponse(vendedor);
    }

    public VendedorRespostaDTO buscarPorId(Long id) {
        Vendedor vendedor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado: " + id));
        return toResponse(vendedor);
    }

    public Page<VendedorRespostaDTO> listar(String nome, Pageable pageable) {
        if (nome == null || nome.isBlank()) {
            return repository.findAll(pageable).map(this::toResponse);
        }
        return repository.findByNomeContainingIgnoreCase(nome, pageable).map(this::toResponse);
    }

    @Transactional
    public VendedorRespostaDTO atualizar(Long id, VendedorAtualizarRequisicaoDTO request) {
        Vendedor vendedor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado: " + id));

        repository.findByRegistro(request.registro()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new IllegalStateException("Já existe vendedor com este registro");
            }
        });

        vendedor.setNome(request.nome());
        vendedor.setRegistro(request.registro());

        vendedor = repository.save(vendedor);
        return toResponse(vendedor);
    }

    private VendedorRespostaDTO toResponse(Vendedor vendedor) {
        return new VendedorRespostaDTO(vendedor.getId(), vendedor.getNome(), vendedor.getRegistro());
    }
}
