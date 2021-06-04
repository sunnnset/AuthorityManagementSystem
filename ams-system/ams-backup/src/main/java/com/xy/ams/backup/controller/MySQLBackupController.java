package com.xy.ams.backup.controller;

import com.xy.ams.backup.constants.BackupConstants;
import com.xy.ams.backup.datasource.BackupDataSourceProperties;
import com.xy.ams.backup.service.MySQLBackupService;
import com.xy.ams.common.utils.FileUtils;
import com.xy.ams.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("backup")
public class MySQLBackupController {

    @Autowired
    MySQLBackupService mySQLBackupService;

    @Autowired
    BackupDataSourceProperties properties;

    /**
     * 备份数据库
     * @return
     */
    @GetMapping(value = "backup")
    public HttpResult backup() {
        String backupFolderName = BackupConstants.DEFAULT_BACKUP_NAME + "_" + new SimpleDateFormat(BackupConstants.DATE_FORMAT).format(new Date());
        return backup(backupFolderName);
    }

    private HttpResult backup(String backupFolderName) {
        String host = properties.getHost();
        String userName = properties.getUserName();
        String password = properties.getPassword();
        String database = properties.getDatabase();
        String backupFolderPath = BackupConstants.BACKUP_FOLDER + backupFolderName + File.separator;
        String fileName = BackupConstants.BACKUP_FILE_NAME;

        try {
            boolean success = mySQLBackupService.backup(host, userName, password, backupFolderPath, fileName, database);
            if (!success) {
                return HttpResult.error("数据备份失败");
            }
        } catch (Exception e) {
            return HttpResult.error(500, e.getMessage());
        }
        return HttpResult.ok();
    }

    /**
     * 从备份恢复数据库
     * @param fileName 要使用的备份文件名称
     * @return
     * @throws IOException
     */
    @GetMapping("restore")
    public HttpResult restore(@RequestParam String fileName) throws IOException {
        String host = properties.getHost();
        String userName = properties.getUserName();
        String password = properties.getPassword();
        String database = properties.getDatabase();
        String restoreFilePath = BackupConstants.RESTORE_FOLDER + fileName;

        try {
            mySQLBackupService.restore(restoreFilePath, host, userName, password, database);
        } catch (Exception e) {
            return HttpResult.error(500, e.getMessage());
        }
        return HttpResult.ok();
    }

    /**
     * 获取备份文件列表
     * @return 备份文件列表
     */
    @GetMapping("findRecord")
    public HttpResult findBackupRecords() {
        // 默认备份文件
        if (!new File(BackupConstants.DEFAULT_BACKUP_NAME).exists()) {
            backup(BackupConstants.DEFAULT_BACKUP_NAME);
        }
        List<Map<String, String>> backupRecords = new ArrayList<>();
        File restoreFolder = new File(BackupConstants.RESTORE_FOLDER);
        if (restoreFolder.exists()) {
            for (File file : restoreFolder.listFiles()) {
                Map<String, String> backup = new HashMap<>();
                backup.put("name", file.getName());
                backup.put("title", file.getName());
                if (BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(file.getName())) {
                    backup.put("title", "系统默认备份");
                }
                backupRecords.add(backup);
            }
        }
        // 默认备份位于最前，其余备份按时间排序，新备份在前
        backupRecords.sort((o1, o2) -> BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(o1.get("name")) ? -1
                : BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(o2.get("name")) ? 1 : o2.get("name").compareTo(o1.get("name")));
        return HttpResult.ok(backupRecords);
    }

    /**
     * 删除指定的备份文件
     * @param fileName 备份文件名
     * @return
     */
    @GetMapping("deleteRecord")
    public HttpResult deleteBackupRecord(@RequestParam String fileName) {
        if (BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(fileName)) {
            return HttpResult.error("无法删除系统默认备份");
        }
        String restoreFilePath = BackupConstants.BACKUP_FOLDER + fileName;
        try{
            FileUtils.deleteFile(new File(restoreFilePath));
        } catch (Exception e) {
            return HttpResult.error(500, e.getMessage());
        }
        return HttpResult.ok();
    }


}
