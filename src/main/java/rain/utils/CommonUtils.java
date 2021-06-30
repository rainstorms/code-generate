package rain.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    /**
     * 清除字符串下划线
     *
     * @param strKey
     * @return
     */
    public static String getNoUnderlineStr(String strKey) {
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("_[a-z|A-Z]");
        Matcher m = p.matcher(strKey);
        while (m.find()) {
            m.appendReplacement(sb, m.group().toUpperCase().replace("_", ""));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 去掉字符串指定的前缀
     *
     * @param str    字符串名称
     * @param prefix 前缀数组
     * @return
     */
    public static String removePrefix(String str, String prefix) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(prefix)) return str;

        if (str.toLowerCase().matches("^" + prefix.toLowerCase() + ".*"))
            return str.substring(prefix.length());

        return str;
    }

    public static String getFormatTime(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return sdf.format(date == null ? new Date() : date);
    }

    public static void xxx(String pattern, Date date) {
        System.out.println("test 1");
    }

    public static void xxx2(String pattern, Date date) {
        System.out.println("test 2");
    }
}
