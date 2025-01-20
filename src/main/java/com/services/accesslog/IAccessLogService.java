package com.services.accesslog;

import com.dao.entities.AccessLog;
import com.dao.entities.User;

import java.util.List;

public interface IAccessLogService {
    boolean logAccess(User user);

    List<AccessLog> getAllAccessLogs();

    int getAccessLogCount();
}
