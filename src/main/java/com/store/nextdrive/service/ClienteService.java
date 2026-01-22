package com.store.nextdrive.service;

import com.store.nextdrive.domain.model.Cliente;
import com.store.nextdrive.dto.ClienteAtualizarRequisicaoDTO;
import com.store.nextdrive.dto.ClienteCriarRequisicaoDTO;
import com.store.nextdrive.dto.ClienteRespostaDTO;
import com.store.nextdrive.exception.ResourceNotFoundException;
import com.store.nextdrive.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ClienteRespostaDTO criar(ClienteCriarRequisicaoDTO request) {
        if (repository.existsByCpf(request.cpf())) {
            throw new IllegalStateException("Já existe cliente com este CPF");
        }

        Cliente cliente = new Cliente(
                request.nome(),
                request.cpf(),
                request.telefone(),
                request.email()
        );

        cliente = repository.save(cliente);
        return toResponse(cliente);
    }

    public ClienteRespostaDTO buscarPorId(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + id));
        return toResponse(cliente);
    }

    public Page<ClienteRespostaDTO> listar(String nome, Pageable pageable) {
        if (nome == null || nome.isBlank()) {
            return repository.findAll(pageable).map(this::toResponse);
        }
        return repository.findByNomeContainingIgnoreCase(nome, pageable).map(this::toResponse);
    }

    @Transactional
    public ClienteRespostaDTO atualizar(Long id, ClienteAtualizarRequisicaoDTO request) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + id));

        // Evita trocar CPF para um CPF que já existe em outro cliente
        repository.findByCpf(request.cpf()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new IllegalStateException("Já existe cliente com este CPF");
            }
        });

        cliente.atualizarDados(
                request.nome(),
                request.cpf(),
                request.telefone(),
                request.email()
        );

        cliente = repository.save(cliente);
        return toResponse(cliente);
    }

    private ClienteRespostaDTO toResponse(Cliente cliente) {
        return new ClienteRespostaDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail()
        );
    }
}