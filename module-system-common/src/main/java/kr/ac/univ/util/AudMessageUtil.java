package kr.ac.univ.util;

public class AudMessageUtil {
    public static String getInsertAudMessage(String messageTarget) {
        return "'" + messageTarget + "' has been registered.";
    }

    public static String getUpdateAudMessage(String messageTarget) {
        return "'" + messageTarget + "' has been updated.";
    }

    public static String getDeleteAudMessage(String messageTarget) {
        return "'" + messageTarget + "' has been deleted.";
    }

    public static String getLoginSuccessAudMessage(String messageTarget) {
        return "'" + messageTarget + "' has logged in.";
    }

}
