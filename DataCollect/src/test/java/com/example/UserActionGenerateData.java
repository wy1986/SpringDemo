package com.example;

import com.alibaba.fastjson.JSONObject;
import com.example.enums.Country;
import com.example.input.UserActionLog;
import com.example.util.GenernateUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UserActionGenerateData {

    static Random random = new Random();
    private static String[] userIds = new String[]{"1", "2"};
    // 用户名
    private static String[] userNames = new String[]{"Tom", "Jerry", "Peter"};
    // 用户app访问的ip
    private static String[] ips = new String[]{"192.168.4.5", "192.168.4.6"};
    // 国家
    private static String[] countrys = new String[]{"China"};
    // 省
    private static String[] provinces = new String[]{"GuangDong", "BeiJing", "ShangHai"};
    // 市
    private static String[] citys = new String[]{"ShenZheng", "BeiJing", "ShangHai"};

    private static List<String> timesList = new ArrayList<String>();
    static {
        for (int i = 0; i < 5; i++) {
            long scantime = GenernateUtil.genernateDate(new Date());
            long jumpTime = scantime + random.nextInt(100) * 1000;
            String resultstring = scantime + "==" + jumpTime;
            timesList.add(resultstring);
        }
    }


    private static UserActionLog genernateData() {
        UserActionLog userActionLog = new UserActionLog();
        int randomSelect = random.nextInt(3);
        userActionLog.setUserName(userNames[random.nextInt(userNames.length)]);
        userActionLog.setUserId(userIds[random.nextInt(userIds.length)]);
        userActionLog.setLatestUseIp(ips[random.nextInt(ips.length)]);
        userActionLog.setCountry(Country.CHINA);
        userActionLog.setProvince(provinces[random.nextInt(provinces.length)]);
        userActionLog.setCity(citys[random.nextInt(citys.length)]);
        String timerandom = timesList.get(random.nextInt(timesList.size()));
        String scanTime = timerandom.split("==")[0];
        String jumpTime = timerandom.split("==")[1];
        userActionLog.setLatestUseTime(jumpTime);
        return userActionLog;

    }


    private static void postHttpMethod(String urlpath, String data) {
        try {
            URL url = new URL(urlpath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setUseCaches(true);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setConnectTimeout(1000 * 5);
            urlConnection.connect();
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
            InputStream inputStream = urlConnection.getInputStream();
            int httpCode = urlConnection.getResponseCode();
            byte[] inputdata = new byte[1024];
            StringBuffer stringBuffer = new StringBuffer();
            while (inputStream.read(inputdata, 0, 1024) != -1) {
                stringBuffer.append(new String(inputdata));
            }
            System.out.println(httpCode);
            System.out.println(stringBuffer.toString());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            UserActionLog userActionLog = genernateData();
//            System.out.println(JSONObject.toJSON(userActionLog));
//            postHttpMethod("http://127.0.0.1:8081/collectDataToMemory", JSONObject.toJSONString(userActionLog));
            postHttpMethod("http://127.0.0.1:8081/collectDataToKafka", JSONObject.toJSONString(userActionLog));
        }
    }
}
