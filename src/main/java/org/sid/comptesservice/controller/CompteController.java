package org.sid.comptesservice.controller;


import org.sid.comptesservice.Entity.Client;
import org.sid.comptesservice.Entity.Compte;
import org.sid.comptesservice.Entity.Operation;
import org.sid.comptesservice.Repositorys.CompteRepository;
import org.sid.comptesservice.Service.ClientServiceClient;
import org.sid.comptesservice.Service.ICompetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompteController {

    @Autowired
    CompteRepository compteRepository;
    @Autowired
    ICompetService iCompetService;

    @Autowired
    ClientServiceClient clientServiceClient;

    @GetMapping("Comptes")
    public List<Compte> getCompte() {
        List<Compte> compts = compteRepository.findAll();
        compts.forEach(compte -> {
            compte.setClient(clientServiceClient.findClientById(compte.getClient_ID()));
        });
        return compts;
    }

    @PostMapping("Comptes")
    public Compte addCompte(@RequestBody Compte compte) {
        return iCompetService.ajouterCompte(compte);
    }

    @PostMapping("compte/verment")
    public Operation verment(@RequestParam("id") Long id, @RequestParam("m") double m) {
        return iCompetService.effectuerVeremant(id, m);
    }

    @PostMapping("compte/retrait")
    public Operation retrait(@RequestParam("id") Long id, @RequestParam("m") double m) {
        return iCompetService.effectuerretirer(id, m);
    }

    @GetMapping("compte/{id}")
    public Compte getCompte(@PathVariable("id") Long id) {
        return iCompetService.getCompta(id);
    }

    @PostMapping("compte/activer")
    public Compte activerCompte(@RequestParam("id") Long id) {
        return iCompetService.activerCompte(id);
    }

    @PostMapping("compte/suspendre")
    public Compte suspendreCompte(@RequestParam("id") Long id) {
        return iCompetService.suspendreCompte(id);
    }


    @GetMapping("compte/operation/{id}")
    public List<Operation> getOperation(@PathVariable("id") Long id) {
        return iCompetService.getOperation(id);
    }
}
