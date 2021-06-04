package com.xy.amsd.common.utils;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PoiUtils {

    /**
     * 生成Excel文件
     * @param workbook POI excel workbook
     * @param fileName 要保存的文件名
     * @return
     * @throws IOException
     */
    public static File createExcelFile(Workbook workbook, String fileName) throws IOException {
        OutputStream stream = null;
        File file = null;
        try {
            file = File.createTempFile(fileName, ".xlsx");
            stream = new FileOutputStream(file.getAbsoluteFile());
            workbook.write(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return file;
    }
}
