package com.xy.ams.backup.service.impl;

import com.xy.ams.backup.service.MySQLBackupService;
import com.xy.ams.backup.utils.MySQLBackupUtils;
import org.springframework.stereotype.Service;

@Service
public class MySQLBackupServiceImpl implements MySQLBackupService  {
    @Override
    public boolean backup(String host, String userName, String password, String backupFolderPath, String fileName, String database) throws Exception {
        return MySQLBackupUtils.backup(host, userName, password, backupFolderPath, fileName, database);
    }

    @Override
    public boolean restore(String restoreFilePath, String host, String username, String password, String database) throws Exception {
        return MySQLBackupUtils.restore(restoreFilePath, host, username, password, database);
    }
}
