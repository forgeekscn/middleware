package cn.forgeeks.awesome.common.util.format;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalFomatUtil {

    public static double getFormatDouble(double duration){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(duration));
        bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
