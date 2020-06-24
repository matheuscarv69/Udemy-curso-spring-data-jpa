package io.github.matheus.rest.controller;

import io.github.matheus.domain.entity.ItemPedido;
import io.github.matheus.domain.entity.Pedido;
import io.github.matheus.rest.dto.InformacaoItemPedidoDTO;
import io.github.matheus.rest.dto.InformacoesPedidoDTO;
import io.github.matheus.rest.dto.PedidoDTO;
import io.github.matheus.service.PedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return service.obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens
                .stream()
                .map(item -> InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
                ).collect(Collectors.toList());

    }
}
