package kr.ac.univ.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NewIconCheck {
    public static Boolean isNew(LocalDateTime pastLocalDateTime ) {
        LocalDateTime currentTime = LocalDateTime.now();
        // 현재 시간과 비교하여 2일 이내에는 newIcon 생성
        long hours = 24;
        boolean result;

        if(ChronoUnit.HOURS.between(currentTime , pastLocalDateTime)  >= -24 ) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }
}