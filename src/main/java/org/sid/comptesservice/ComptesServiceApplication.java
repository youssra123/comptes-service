package org.sid.comptesservice;

import org.sid.comptesservice.Entity.Compte;
import org.sid.comptesservice.Entity.Operation;
import org.sid.comptesservice.Repositorys.CompteRepository;
import org.sid.comptesservice.Repositorys.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ComptesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComptesServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(CompteRepository compteRepository, OperationRepository operationRepository) {
        return args -> {
            Compte compte = compteRepository.save(new Compte(null, "2000", 12.00, new Date(), "COURANT", "ACTIVE", null, 1l,null));
            List<Operation> operations = new ArrayList<>();
            operations.add(operationRepository.save(new Operation(null, 20.00, "vers", compte)));
            compte.setOperations(operations);
            operations.forEach(o -> {
                operationRepository.save(o);
            });
        };
    }
}