package com.ai.common;

import com.ai.domain.user.User;
import org.joda.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;
import static com.ai.common.CommonConst.TOKEN_NAME;
import static com.ai.common.CommonConst.USER_CONTEXT;

/**
 * Created by aixiaofei on 2018/11/8.
 */
public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    public static User getUserContext(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(USER_CONTEXT);
    }

    public static void setUserContext(HttpServletRequest request, Object object) {
        request.getSession().setAttribute(USER_CONTEXT, object);
    }

    //判断是否为数字，正整数和浮点数
    public static boolean isNumber(String str) {
        return Pattern.matches(CommonConst.CHECK_NUMBER, str);
    }

    //Base64编码
    public static String Base64Encode(String str) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] text = new byte[0];
        try {
            text = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            LOGGER.info("Base64加密错误");
        }
        return encoder.encodeToString(text);
    }

    //Base64解码
    public static String Base64Decode(String code) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            return new String(decoder.decode(code), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            LOGGER.info("Base64解码错误");
        }
        return null;
    }

    //判断字符串是否有效，不为null和""
    public static boolean stringIsEmpty(String str) {
        return (str == null || str.equals(""));
    }

    //计算两个日期相差天数、小时数、分钟数、秒数
    public static int timeCount(Date startTime, Date endTime, String flag) {
        DateTime start = new DateTime(startTime);
        DateTime end = new DateTime(endTime);
        switch (flag) {
            case "day":
                return Days.daysBetween(start, end).getDays();
            case "hour":
                return Hours.hoursBetween(start, end).getHours();
            case "min":
                return Minutes.minutesBetween(start, end).getMinutes();
            case "second":
                return Seconds.secondsBetween(start, end).getSeconds();
            default:
                return -1;
        }
    }

    //生成密文密码
    public static String produceLockPassword(User user) {
        String info = user.getUserName() + "," + user.getPassword();
        String password = null;
        password = CommonUtil.Base64Encode(info);
        return password;
    }

    //生成个人独有的Love-Lock
    public static String produceLoveLock(User user) {
        String info = user.getUserName() + "," + user.getSex() + "," + user.getBirth();
        String LoveLock = null;
        LoveLock = CommonUtil.Base64Encode(info);
        return LoveLock;
    }

    //获得主机内网Ip
    public static String getHostIp() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            LOGGER.error(e.getMessage());
        }
        assert address != null;
        return address.getHostAddress();
    }

    //获得主机外网Ip
    public static String getInternetIp() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements()) {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements()) {
                    ip = addrs.nextElement();
                    if (ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(getHostIp())) {
                        return ip.getHostAddress();
                    }
                }
            }
            // 如果没有外网IP，就返回内网IP
            return getHostIp();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //获得cookie中的token
    public static String getToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TOKEN_NAME)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}
