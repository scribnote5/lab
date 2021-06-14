package kr.ac.univ.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NewIconCheck {
    public static Boolean isNew(LocalDateTime pastLocalDateTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        boolean result;

        // 현재 시간과 비교하여 24시간 이내인 경우 newIcon 생성
        if (ChronoUnit.HOURS.between(currentTime, pastLocalDateTime) >= -24) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }
}