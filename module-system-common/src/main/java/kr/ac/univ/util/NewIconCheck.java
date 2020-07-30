package kr.ac.univ.util;

import java.time.LocalDateTime;

public class NewIconCheck {
    public static Boolean isNew(LocalDateTime noticeBoardTime ) {
        LocalDateTime currentTime = LocalDateTime.now();
        // 현재 시간과 비교하여 2일 이내에는 newIcon 생성
        long days = 2;
        boolean result;

        if(noticeBoardTime.isAfter(currentTime.minusDays(days))) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }
}