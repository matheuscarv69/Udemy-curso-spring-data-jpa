package io.github.matheus.domain.repository;

import io.github.matheus.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

}
