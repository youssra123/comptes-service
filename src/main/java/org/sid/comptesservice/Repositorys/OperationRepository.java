package org.sid.comptesservice.Repositorys;

import org.sid.comptesservice.Entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
