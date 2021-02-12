package org.sid.comptesservice.Service;

import org.sid.comptesservice.Entity.Client;
import org.sid.comptesservice.Entity.Compte;
import org.sid.comptesservice.Entity.Operation;
import org.sid.comptesservice.Repositorys.CompteRepository;
import org.sid.comptesservice.Repositorys.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ICompteServiceImp implements ICompetService{

    @Autowired
    CompteRepository compteRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    ClientServiceClient clientServiceClient;


    @Autowired
    KafkaTemplate<Integer,Operation> kafkaTemplate;

    public String topic="test3";


    @Override
    public Compte ajouterCompte(Compte c) {
        return compteRepository.save(c);
    }

    @Override
    public Operation effectuerVeremant(Long id, double montant) {
        Compte c=compteRepository.getOne(id);
        Operation o=new Operation();
        o.setCompte(c);
        o.setMontant(montant);
        o.setType("virement");
        c.setSold(c.getSold()+montant);
        compteRepository.save(c);
        o=operationRepository.save(o);
        kafkaTemplate.send(topic,o);
        return o;
    }

    @Override
    public Operation effectuerretirer(Long id, double montant) {
        Compte c=compteRepository.getOne(id);
        Operation o=new Operation();
        o.setCompte(c);
        o.setMontant(montant);
        o.setType("retirer");
        c.setSold(c.getSold()-montant);
        compteRepository.save(c);
        o=operationRepository.save(o);
        kafkaTemplate.send(topic,o);
        return o;
    }

    @Override
    @Transactional
    public void verment(Long id1, Long id2 ,double montant) {
        effectuerretirer(id1,montant);
        effectuerVeremant(id2,montant);
    }

    @Override
    public Compte getCompta(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setClient(clientServiceClient.findClientById(c.getClient_ID()));
        return c;
    }

    @Override
    public Compte activerCompte(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setEtat("Activer");
        compteRepository.save(c);
        return c;
    }

    @Override
    public Compte suspendreCompte(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setEtat("suspendre");
        compteRepository.save(c);
        return c;
    }

    @Override
    public List<Operation> getOperation(Long id) {
        Compte c=compteRepository.getOne(id);
        return c.getOperations();
    }
}
