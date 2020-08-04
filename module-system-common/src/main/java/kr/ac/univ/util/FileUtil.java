package kr.ac.univ.util;

import java.text.DecimalFormat;

public class FileUtil {
    public static String getExtension(String fileName) {
        return (fileName.substring(fileName.lastIndexOf("."))).toLowerCase();
    }

    public static String convertFileSize(long fileSize) {
        String retFormat = "0";
        String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };
        DecimalFormat df = new DecimalFormat("#,###.##");

        if (fileSize != 0) {
            int idx = (int) Math.floor(Math.log(fileSize) / Math.log(1024));
            double ret = ((fileSize / Math.pow(1024, Math.floor(idx))));
            retFormat = df.format(ret) + " " + s[idx];
        } else {
            retFormat += " " + s[0];
        }

        return retFormat;
    }

}
