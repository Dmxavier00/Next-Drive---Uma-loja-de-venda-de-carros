package com.store.nextdrive.service;

import com.store.nextdrive.domain.enums.StatusCarro;
import com.store.nextdrive.domain.enums.StatusReserva;
import com.store.nextdrive.domain.model.Carro;
import com.store.nextdrive.domain.model.Cliente;
import com.store.nextdrive.domain.model.Venda;
import com.store.nextdrive.domain.model.Vendedor;
import com.store.nextdrive.dto.VendaCriarRequisicaoDTO;
import com.store.nextdrive.dto.VendaRespostaDTO;
import com.store.nextdrive.exception.ResourceNotFoundException;
import com.store.nextdrive.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;
    private final VendedorRepository vendedorRepository;
    private final ReservaRepository reservaRepository;

    public VendaService(
            VendaRepository vendaRepository,
            CarroRepository carroRepository,
            ClienteRepository clienteRepository,
            VendedorRepository vendedorRepository,
            ReservaRepository reservaRepository
    ) {
        this.vendaRepository = vendaRepository;
        this.carroRepository = carroRepository;
        this.clienteRepository = clienteRepository;
        this.vendedorRepository = vendedorRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public VendaRespostaDTO criar(VendaCriarRequisicaoDTO request) {
        Carro carro = carroRepository.findById(request.carroId())
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado: " + request.carroId()));

        if (carro.getStatus() == StatusCarro.VENDIDO) {
            throw new IllegalStateException("Carro já está VENDIDO");
        }

        if (vendaRepository.existsByCarro_Id(carro.getId())) {
            throw new IllegalStateException("Já existe venda registrada para este carro");
        }

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + request.clienteId()));

        Vendedor vendedor = vendedorRepository.findById(request.vendedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado: " + request.vendedorId()));

        // cancela reserva ativa desse carro (se existir)
        reservaRepository.findFirstByCarroIdAndStatus(carro.getId(), StatusReserva.ATIVA)
                .ifPresent(reserva -> {
                    reserva.cancelar();
                    reservaRepository.save(reserva);
                });

        carro.marcarVendido();
        carroRepository.save(carro);

        Venda venda = new Venda(carro, cliente, vendedor, request.vendaPreco(), request.formaPagamento());
        venda = vendaRepository.save(venda);

        return toResponse(venda);
    }

    @Transactional
    public Page<VendaRespostaDTO> listar(Long carroId, Long clienteId, Long vendedorId, Pageable pageable) {
        if (carroId != null) return vendaRepository.findByCarro_Id(carroId, pageable).map(this::toResponse);
        if (clienteId != null) return vendaRepository.findByCliente_Id(clienteId, pageable).map(this::toResponse);
        if (vendedorId != null) return vendaRepository.findByVendedor_Id(vendedorId, pageable).map(this::toResponse);
        return vendaRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public VendaRespostaDTO buscarPorId(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada: " + id));
        return toResponse(venda);
    }

    private VendaRespostaDTO toResponse(Venda v) {
        return new VendaRespostaDTO(
                v.getId(),
                v.getCarro().getId(),
                v.getCliente().getId(),
                v.getVendedor().getId(),
                v.getVendaData(),
                v.getVendaPreco(),
                v.getFormaPagamento()
        );
    }
}
