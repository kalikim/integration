package com.tanda.paymentgateway.integration.db.repo;

import com.tanda.paymentgateway.integration.db.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LogRepo extends JpaRepository<Log, Long> {
    Log getLogByRef(String ref);
    Set<Log> getLogByStatus(String status);
}
