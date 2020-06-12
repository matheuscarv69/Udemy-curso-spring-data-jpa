package io.github.matheus.domain.repository;

import io.github.matheus.domain.entity.Cliente;
import io.github.matheus.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
