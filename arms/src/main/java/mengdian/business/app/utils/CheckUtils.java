package mengdian.business.app.utils;

import android.os.PatternMatcher;

import java.util.regex.Pattern;

/**
 * Created by wall on 2018/7/26 10:18
 * w_ll@winning.com.cn
 */
public class CheckUtils {


    public static boolean isMobile(String phone){
        String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
        return Pattern.matches(pattern,phone);
    }


}
