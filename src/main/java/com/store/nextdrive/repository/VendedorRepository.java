package com.store.nextdrive.repository;

import com.store.nextdrive.domain.model.Vendedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    boolean existsByRegistro(String registro);

    Optional<Vendedor> findByRegistro(String registro);

    Page<Vendedor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
