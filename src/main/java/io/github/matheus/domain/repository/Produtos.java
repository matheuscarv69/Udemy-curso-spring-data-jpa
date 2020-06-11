package io.github.matheus.domain.repository;

import io.github.matheus.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Produtos extends JpaRepository<Produto, Integer> {

//    @Query(value = "select * from produto p where p.descricao like '%:descricao%' ", nativeQuery = true)
//    List<Produto> procurarPorDescricao(@Param("descricao") String descricao);


}
