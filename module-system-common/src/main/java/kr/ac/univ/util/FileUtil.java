package kr.ac.univ.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
class Disk {
    String driverName;
    String totalSize;
    String freeSize;
    String useSize;

    @Builder
    public Disk(String driverName, String totalSize, String freeSize, String useSize) {
        this.driverName = driverName;
        this.totalSize = totalSize;
        this.useSize = useSize;
    }
}

public class FileUtil {
    public static String getExtension(String fileName) {
        return (fileName.substring(fileName.lastIndexOf("."))).toLowerCase();
    }

    public static String convertFileSize(long fileSize) {
        String retFormat = "0";
        String[] s = {"bytes", "KB", "MB", "GB", "TB", "PB"};
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

    public static List<Disk> getDiskInfo() {
        List<Disk> result = new ArrayList<>();
        double totalSize = 0;
        double useSize = 0;

        File[] drives = File.listRoots();

        for (File drive : drives) {
            totalSize = drive.getTotalSpace() / Math.pow(1024, 3);
            useSize = drive.getUsableSpace() / Math.pow(1024, 3);

            result.add(Disk.builder()
                    .driverName(drive.getAbsolutePath())
                    .totalSize(String.format("%.2f", totalSize) + "GB")
                    .useSize(String.format("%.2f", useSize) + "GB")
                    .build());
        }

        return result;
    }
}
