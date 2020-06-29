package io.github.matheus.service;

import io.github.matheus.domain.entity.Pedido;
import io.github.matheus.domain.enums.StatusPedido;
import io.github.matheus.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido status);
}
