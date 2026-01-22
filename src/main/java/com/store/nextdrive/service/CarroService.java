package com.store.nextdrive.service;


import com.store.nextdrive.domain.enums.StatusCarro;
import com.store.nextdrive.domain.model.Carro;
import com.store.nextdrive.dto.CarroAtualizarRequisicaoDTO;
import com.store.nextdrive.dto.CarroCriarRequisicaoDTO;
import com.store.nextdrive.dto.CarroRespostaDTO;
import com.store.nextdrive.exception.ResourceNotFoundException;
import com.store.nextdrive.repository.CarroRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarroService {

    private final CarroRepository repository;

    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CarroRespostaDTO criar(CarroCriarRequisicaoDTO request) {
        Carro carro = new Carro(
                request.marca(),
                request.modelo(),
                request.ano(),
                request.preco(),
                request.quilometragem(),
                request.cor(),
                request.cambio(),
                request.gasolina()
        );

        carro = repository.save(carro);
        return toResponse(carro);
    }

    public CarroRespostaDTO buscarPorId(Long id) {
        Carro carro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado: " + id));
        return toResponse(carro);
    }

    public Page<CarroRespostaDTO> listar(StatusCarro status, String marca, String modelo, Pageable pageable) {
        if (status != null) {
            return repository.findByStatus(status, pageable).map(this::toResponse);
        }

        String m = (marca == null) ? "" : marca;
        String md = (modelo == null) ? "" : modelo;

        return repository
                .findByMarcaContainingIgnoreCaseAndModeloContainingIgnoreCase(m, md, pageable)
                .map(this::toResponse);
    }

    @Transactional
    public CarroRespostaDTO atualizar(Long id, CarroAtualizarRequisicaoDTO request) {
        Carro carro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado: " + id));

        // atualização "na unha" (simples e explícito)
        // você pode criar setters ou um método update(...) na entity
        // aqui vou assumir setters existem; se não existem, eu te ajusto a entity.
        carro = aplicarAtualizacao(carro, request);

        return toResponse(repository.save(carro));
    }

    @Transactional
    public CarroRespostaDTO atualizarStatus(Long id, StatusCarro novoStatus) {
        Carro carro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado: " + id));

        // regra simples de status
        if (novoStatus == StatusCarro.DISPONIVEL) carro.marcarDisponivel();
        if (novoStatus == StatusCarro.RESERVADO) carro.marcarReservado();
        if (novoStatus == StatusCarro.VENDIDO) carro.marcarVendido();

        return toResponse(repository.save(carro));
    }

    private Carro aplicarAtualizacao(Carro carro, CarroAtualizarRequisicaoDTO req) {
        carro.atualizarDados(
                req.marca(),
                req.modelo(),
                req.ano(),
                req.preco(),
                req.quilometragem(),
                req.cor(),
                req.cambio(),
                req.gasolina()
        );
        return carro;
    }


    private CarroRespostaDTO toResponse(Carro carro) {
        return new CarroRespostaDTO(
                carro.getId(),
                carro.getMarca(),
                carro.getModelo(),
                carro.getAno(),
                carro.getPreco(),
                carro.getQuilometragem(),
                carro.getCor(),
                carro.getCambio(),
                carro.getGasolina(),
                carro.getStatus()
        );
    }
}
