package com.store.nextdrive.repository;

import com.store.nextdrive.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
