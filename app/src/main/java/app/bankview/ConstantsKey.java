package app.bankview;

import java.util.HashMap;

public class ConstantsKey {
    public static final HashMap<String, String> COURSE_NUMBER = new HashMap<>();

    static {
        COURSE_NUMBER.putIfAbsent("1", "上午1、2节");
        COURSE_NUMBER.putIfAbsent("2", "上午3、4节");
        COURSE_NUMBER.putIfAbsent("3", "下午1、2节");
        COURSE_NUMBER.putIfAbsent("4", "下午3、4节");
        COURSE_NUMBER.putIfAbsent("5", "晚上1、2节");
    }

    public static final String selcct_course = "选修课";
    public static final String require_course = "必修课";
    public static final String termUp = "上";
    public static final String termDown = "下";
}
