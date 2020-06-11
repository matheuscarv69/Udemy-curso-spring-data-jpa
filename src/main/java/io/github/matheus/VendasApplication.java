package io.github.matheus;

import io.github.matheus.domain.entity.Cliente;
import io.github.matheus.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {

            System.out.println("Salvando Clientes");
            clientes.save(new Cliente("Matheus Carvalho"));
            clientes.save(new Cliente("Teuzin dus vrauuu"));

            List<Cliente> result = clientes.encontrarPorNome("Matheus Carvalho");
            result.forEach(System.out::println);



        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
