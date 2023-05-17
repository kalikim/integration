package com.tanda.paymentgateway.integration.service;

import com.tanda.paymentgateway.integration.db.model.Log;

import java.util.Set;

public interface LogService {
    Log saveLog(Log log);
    Log getLogByRef(String ref);

    void deleteLog(Log log);

    Set<Log> getPendingLogs(String status);
}
