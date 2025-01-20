package com.services.accesslog;

import com.dao.AccessLogDaoImpl;
import com.dao.IAccessLogDao;
import com.dao.entities.AccessLog;
import com.dao.entities.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class AccessLogServiceImpl implements IAccessLogService {

    private IAccessLogDao accessLogDao;

    {
        accessLogDao = new AccessLogDaoImpl();
    }

    @Override
    public boolean logAccess(User user) {
        AccessLog accessLog = new AccessLog();
        accessLog.setUser(user);
        accessLog.setAccessTime(new Timestamp(System.currentTimeMillis()));
        return accessLogDao.save(accessLog);
    }

    @Override
    public List<AccessLog> getAllAccessLogs() {
        return accessLogDao.findAll();
    }

    @Override
    public int getAccessLogCount() {
        return accessLogDao.count();
    }
}
