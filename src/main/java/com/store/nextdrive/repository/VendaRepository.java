package com.store.nextdrive.repository;

import com.store.nextdrive.domain.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    Page<Venda> findByCarro_Id(Long carroId, Pageable pageable);

    Page<Venda> findByCliente_Id(Long clienteId, Pageable pageable);

    Page<Venda> findByVendedor_Id(Long vendedorId, Pageable pageable);

    boolean existsByCarro_Id(Long carroId);
}
