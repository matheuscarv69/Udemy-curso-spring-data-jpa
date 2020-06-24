package io.github.matheus.rest.controller;

import io.github.matheus.domain.entity.Produto;
import io.github.matheus.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable Integer id) {
        return produtos
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody Produto produto) {
        return produtos.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        produtos.findById(id)
                .map(produto -> {
                    produtos.delete(produto);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Produto produto) {
        produtos.findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    return produtos.save(produto);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return produtos.findAll(example);
    }
}
