package org.sid.comptesservice.Service;

import org.sid.comptesservice.Entity.Compte;
import org.sid.comptesservice.Entity.Operation;

import java.util.List;

public interface ICompetService {

    public Compte ajouterCompte(Compte c);
    public Operation effectuerVeremant(Long id,double montant);
    public Operation effectuerretirer(Long id,double montant);
    public void verment(Long id1,Long id2,double montan);
    public Compte getCompta(Long id);
    public Compte activerCompte(Long id);
    public Compte suspendreCompte(Long id);
    public List<Operation> getOperation(Long id);

}
