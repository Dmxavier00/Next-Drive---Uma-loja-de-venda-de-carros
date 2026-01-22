package com.store.nextdrive.repository;

import com.store.nextdrive.domain.enums.StatusReserva;
import com.store.nextdrive.domain.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findFirstByCarroIdAndStatus(Long carroId, StatusReserva status);

    Page<Reserva> findByStatus(StatusReserva status, Pageable pageable);

    Page<Reserva> findByClienteId(Long clienteId, Pageable pageable);

    Page<Reserva> findByCarroId(Long carroId, Pageable pageable);
}
