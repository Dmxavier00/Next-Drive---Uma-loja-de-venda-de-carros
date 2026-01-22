package com.store.nextdrive.service;

import com.store.nextdrive.domain.enums.StatusCarro;
import com.store.nextdrive.domain.enums.StatusReserva;
import com.store.nextdrive.domain.model.Carro;
import com.store.nextdrive.domain.model.Cliente;
import com.store.nextdrive.domain.model.Reserva;
import com.store.nextdrive.dto.ReservaCriarRequisicaoDTO;
import com.store.nextdrive.dto.ReservaRespostaDTO;
import com.store.nextdrive.exception.ResourceNotFoundException;
import com.store.nextdrive.repository.CarroRepository;
import com.store.nextdrive.repository.ClienteRepository;
import com.store.nextdrive.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            CarroRepository carroRepository,
            ClienteRepository clienteRepository
    ) {
        this.reservaRepository = reservaRepository;
        this.carroRepository = carroRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ReservaRespostaDTO criar(ReservaCriarRequisicaoDTO request) {
        Carro carro = carroRepository.findById(request.carroId())
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado: " + request.carroId()));

        if (carro.getStatus() != StatusCarro.DISPONIVEL) {
            throw new IllegalStateException("Carro não está DISPONÍVEL para reserva");
        }

        // evita duplicidade lógica (opcional, mas útil)
        reservaRepository.findFirstByCarroIdAndStatus(carro.getId(), StatusReserva.ATIVA)
                .ifPresent(r -> { throw new IllegalStateException("Já existe reserva ATIVA para este carro"); });

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + request.clienteId()));

        // muda status do carro
        carro.marcarReservado();
        carroRepository.save(carro);

        // cria reserva
        Reserva reserva = new Reserva(carro, cliente, request.dataInicio(), request.dataFinal());
        reserva = reservaRepository.save(reserva);

        return toResponse(reserva);
    }

    public ReservaRespostaDTO buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada: " + id));
        return toResponse(reserva);
    }

    public Page<ReservaRespostaDTO> listar(StatusReserva status, Long carroId, Long clienteId, Pageable pageable) {
        if (status != null) {
            return reservaRepository.findByStatus(status, pageable).map(this::toResponse);
        }
        if (carroId != null) {
            return reservaRepository.findByCarroId(carroId, pageable).map(this::toResponse);
        }
        if (clienteId != null) {
            return reservaRepository.findByClienteId(clienteId, pageable).map(this::toResponse);
        }
        return reservaRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public ReservaRespostaDTO cancelar(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada: " + reservaId));

        reserva.cancelar();
        reservaRepository.save(reserva);

        // volta carro pra DISPONIVEL (se não estiver vendido)
        Carro carro = reserva.getCarro();
        if (carro.getStatus() != StatusCarro.VENDIDO) {
            carro.marcarDisponivel();
            carroRepository.save(carro);
        }

        return toResponse(reserva);
    }

    private ReservaRespostaDTO toResponse(Reserva r) {
        return new ReservaRespostaDTO(
                r.getId(),
                r.getCarro().getId(),
                r.getCliente().getId(),
                r.getDataInicio(),
                r.getDataFinal(),
                r.getStatus()
        );
    }
}
