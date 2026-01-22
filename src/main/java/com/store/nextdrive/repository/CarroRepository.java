package com.store.nextdrive.repository;


import com.store.nextdrive.domain.model.Carro;
import com.store.nextdrive.domain.enums.StatusCarro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<Carro, Long> {

    Page<Carro> findByStatus(StatusCarro status, Pageable pageable);

    Page<Carro> findByMarcaContainingIgnoreCaseAndModeloContainingIgnoreCase(
            String marca, String modelo, Pageable pageable
    );
}
