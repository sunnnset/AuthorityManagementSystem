package com.xy.ams.backup.service;

public interface MySQLBackupService {

    /**
     * 备份数据库
     * @param host 数据库所在主机地址
     * @param userName 数据库用户名
     * @param password 数据库密码
     * @param backupFolderPath 备份文件的路径
     * @param fileName 备份的文件名
     * @param database 要备份的数据库
     * @return
     * @throws Exception
     */
    boolean backup(String host, String userName, String password, String backupFolderPath, String fileName, String database) throws  Exception;

    /**
     * 还原数据库
     * @param restoreFilePath 备份文件路径
     * @param host 数据库主机地址
     * @param username 用户名
     * @param password 密码
     * @param database 数据库名称
     * @return
     * @throws Exception
     */
    boolean restore(String restoreFilePath, String host, String username, String password, String database) throws Exception;
}
