package com.tanda.paymentgateway.integration.service.impl;


import com.tanda.paymentgateway.integration.db.model.Log;
import com.tanda.paymentgateway.integration.db.repo.LogRepo;
import com.tanda.paymentgateway.integration.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepo logRepo;

    public Log saveLog(Log log) {
        return logRepo.save(log);
    }

    public Log getLogByRef(String ref){
        return logRepo.getLogByRef(ref);
    }

    @Override
    public void deleteLog(Log log) {
        logRepo.delete(log);
    }

    @Override
    public Set<Log> getPendingLogs(final String status) {
        return logRepo.getLogByStatus(status);
    }
}
