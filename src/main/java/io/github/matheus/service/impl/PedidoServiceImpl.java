package io.github.matheus.service.impl;

import io.github.matheus.domain.entity.Cliente;
import io.github.matheus.domain.entity.ItemPedido;
import io.github.matheus.domain.entity.Pedido;
import io.github.matheus.domain.entity.Produto;
import io.github.matheus.domain.repository.Clientes;
import io.github.matheus.domain.repository.ItensPedido;
import io.github.matheus.domain.repository.Pedidos;
import io.github.matheus.domain.repository.Produtos;
import io.github.matheus.exception.RegraNegocioException;
import io.github.matheus.rest.dto.ItemPedidoDTO;
import io.github.matheus.rest.dto.PedidoDTO;
import io.github.matheus.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidosRepository;
    private final Clientes clientesRespository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRespository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemPedido = converterItens(pedido, dto.getItens());

        pedidosRepository.save(pedido);
        itensPedidoRepository.saveAll(itemPedido);
        pedido.setItens(itemPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
        if (itens.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }

        return itens
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository.findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido:" + idProduto));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());

    }

}
