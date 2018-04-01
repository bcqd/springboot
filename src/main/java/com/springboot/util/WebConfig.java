package com.springboot.util;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: chuan.bai
 * @Description
 * @Date: Created on 16:18 23/11/2017
 * @Modified By:
 */
public class WebConfig {


    //默认头像
    public static final String HEADPICTRE = "xxx.png";


    // 手机号码正则表达式
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,1-9]|(17[0-9])))\\d{8}$";
    //身份证号码正则表达式
    public static final String REGEX_ID = "^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$";

    //支付密码正则表达式
    public static final String PAY_PASSWORD = "[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]";

    //网址正则表达式
    public static final String WEB_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?\n";

    //群二维码格式
    public static final String GROUP_URL = "http://im.ychat.com/g/?";

    //用户二维码格式
    public static final String USER_RUL = "http://im.ychat.com/u/";

    //收款二维码格式
    public static final String TRADE_NOTE_URL = "http://im.ychat.com/p/";

    public static final String SMS_API_URL = "https://api.ala-game.com/common-server/sms/aliyun/send";


    /**
     * 中国移动：China Mobile
     * 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     */
    public static final String CM = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

    /**
     * 中国联通：China Unicom
     * 130,131,132,155,156,185,186,145,176,1709
     */
    public static final String CU = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";
    /**
     * 中国电信：China Telecom
     * 133,153,180,181,189,177,1700
     */
    public static final String CT = "(^1((33|53|77|8[019])[0-9]|349)|(700\\d{7}$)";


    /**
     * 返回成功的方法
     *
     * @return
     */
    public static Map<String, Object> SUCCESS() {
        Map<String, Object> result = new HashMap<>();
        result.put("error", "成功");
        result.put("isSuccessful", 1);
        return result;
    }

    public static Map<String, Object> SUCCESS(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("error", "成功");
        result.put("isSuccessful", 1);
        result.put("data", data);
        return result;
    }

    public static Map<String, Object> ERR(String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("error", msg);
        result.put("isSuccessful", 0);
        return result;
    }

    public static Map<String, Object> PARM_ERR() {
        Map<String, Object> result = new HashMap<>();
        result.put("error", "参数错误");
        result.put("isSuccessful", 0);
        return result;
    }


    public static Map<String, Object> USER_ISNULL() {
        Map<String, Object> result = new HashMap<>();
        result.put("error", "用户不存在");
        result.put("isSuccessful", 0);
        return result;
    }


    public static String createTransferUrl(Integer transferNoteId) {
        return "http://im.ychat.com/p/" + transferNoteId;
    }


    /**
     * 判断参数是否为空  非空为ture
     *
     * @param args
     * @return
     */
    public static boolean checkNull(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                return false;
            }
            if (args[i].toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }


    /***
     * 生成编号
     * @param no 增长的主键
     * @return 编号字符串
     */
    public static String createNumber(Integer no, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        String number = type + date + no;
        return number;
    }


    /**
     * 判断JSON是否为null
     *
     * @param args
     * @param data
     * @return
     */
    public static boolean checkJson(String[] args, String data) {
        JSONObject jo = new JSONObject(data);
        for (int i = 0; i < args.length; i++) {
            if (jo.isNull(args[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 把数组字符串  转化为  逗号隔开的字符串格式
     *
     * @param arrayString
     * @return
     */
    public static String ArrayToString(String arrayString) {
        String a = arrayString.trim().replace("[", "").replace("]", "").trim().replace(" ", "");
        return a;
    }


    /**
     * 生成八位随机数
     *
     * @return
     */
    public static String createRandom() {
        StringBuilder str = new StringBuilder();//定义变长字符串
        Random random = new Random();
//随机生成数字，并添加到字符串
        for (int i = 0; i < 8; i++) {
            str.append(random.nextInt(10));
        }
//将字符串转换为数字并输出
        return str.toString();
    }

    /**
     * 创建uuId
     *
     * @return
     */
    public static String createUUId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
