package kr.ac.univ.util;

import java.io.UnsupportedEncodingException;

public class ByteSizeUtil {

    public static int getByteSize(String str) {
        int byteSize = 0;

        try {
            byteSize = str.getBytes("UTF-8").length;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteSize;
    }

}
