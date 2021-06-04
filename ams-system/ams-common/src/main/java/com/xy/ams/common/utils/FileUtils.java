package com.xy.ams.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtils {

    public static void downloadFile(HttpServletResponse response, File file, String fileName) throws IOException{
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        InputStream is = new FileInputStream(file.getAbsolutePath());
        BufferedInputStream bis = new BufferedInputStream(is);
        int length = 0;
        byte[] temp = new byte[1 * 1024 * 10];
        while ((length = bis.read(temp)) != -1) {
            bos.write(temp, 0, length);
        }
        bos.flush();
        bis.close();
        bos.close();
        is.close();
    }

    public static void deleteFile(File file) {

        // 如果是文件夹则递归删除
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                deleteFile(file);
            }
        }
        file.delete();
    }
}
