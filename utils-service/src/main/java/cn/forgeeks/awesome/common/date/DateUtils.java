package cn.forgeeks.awesome.common.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getFormatDateStr() {
        return simpleDateFormat.format(new Date());
    }

}
