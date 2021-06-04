package com.xy.ams.backup.utils;

import com.xy.ams.backup.datasource.BackupDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class MySQLBackupUtils {

    private static final boolean PRINT_SHELL_OUTPUT = false;

    public static boolean backup(String host, String userName, String password, String backupFolderPath, String fileName, String database) throws Exception{
        File backupFolderFile = new File(backupFolderPath);
        if (!backupFolderFile.exists()) {
            backupFolderFile.mkdirs();
        }

        if (!backupFolderPath.endsWith(File.separator) && !backupFolderPath.endsWith("/")) {
            backupFolderPath += File.separator;
        }

        String backupFilePath = backupFolderPath + fileName;

        // 无法使用环境变量，因此需要使用绝对路径
        String stringBuilder = "C:\\Program\" \"Files\\MySQL\\MySQL\" \"Server\" \"8.0\\bin\\mysqldump --opt " + " --add-drop-database " + " --add-drop-table " +
                " -h" + host + " -u" + userName + " -p" + password +
                " --result-file=" + backupFilePath + " --default-character-set=utf8 " + database;
        try {

            if (PRINT_SHELL_OUTPUT) {
                System.out.println(stringBuilder);
                BufferedReader reader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(getCommand(stringBuilder.toString())).getErrorStream(), "GB2312"));
                String line = null;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line + '\n');
            }
            System.out.println(builder);
            } else {
                Process process = Runtime.getRuntime().exec(getCommand((stringBuilder)));
                if (process.waitFor() == 0) {
                    // 线程正常终止
                    System.out.println("数据已备份至 " + backupFilePath + " 文件");
                    return true;
                } else return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean restore(String restoreFilePath, String host, String userName, String password, String database) throws Exception{
        File restoreFile = new File(restoreFilePath);
        if (restoreFile.isDirectory()) {
            for (File file : restoreFile.listFiles()) {
                if (file.exists() && file.getPath().endsWith(".sql")) {
                    restoreFilePath = file.getAbsolutePath();
                    break;
                }
            }
        }
        String stringBuilder = "mysql -h" + host + " -u" + userName + " -p" + password +
                " " + database + " < " + restoreFilePath;
        try {
            Process process = Runtime.getRuntime().exec(getCommand(stringBuilder));
            if (process.waitFor() == 0) {
                System.out.println("数据已从" + restoreFilePath + "导入到数据库中");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String[] getCommand(String command) {
        String os = System.getProperty("os.name");
        String shell;
        String c;
        if (os.toLowerCase().startsWith("win")) {
            shell = "cmd";
            c = "/c";
        } else {
            shell = "/bin/bash";
            c = "-c";
        }
        return new String[]{shell, c, command};
    }

    /**
     * 测试
     * 需要设置好cmd的环境变量
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        String host = "localhost";
        String userName = "root";
        String password = "purestmusic2013";
        String database = "authms";

        System.out.println("Backup:");
        String backupFolder = "c:/dev/";
        String fileName = "ams.sql";
        backup(host, userName, password, backupFolder, fileName, database);
        System.out.println("backup success");

        /*
        System.out.println("restore: ");
        String restorePath = backupFolder + fileName;
        restore(restorePath, host, userName, password, database);
        System.out.println("restore success");*/
    }
}
