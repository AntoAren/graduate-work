package by.bsu.zakharankou.restservices.model.util;

public class StringUtil {

    public static Long getIdFromString(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
