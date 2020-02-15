package com.example;

import com.alibaba.fastjson.JSONObject;
import com.example.enums.LogType;
import com.example.input.MiniProgramProductHuoDong;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MiniProgramEventGenerateData {

    private static String [] weixinAccountinfoes = new String[]{
            "xiaogao==小高==男==上海","xiaobai==小白==女==深圳","xiaohei==小黑==男==北京"
    };
    private static int[] days  = new int[]{3,4,5,6};
    static Random random = new Random();
    private static String[] userIds = new String[]{"1","2"};
    private static String[] deviceIds = new String[]{"deviceId1","deviceId2"};
    private static String[] huodongIdes = new String[]{"1","2","3"};
    private static String[] ips = new String[]{"192.168.4.5","192.168.4.6"};//用户app访问的ip
    private static String[] countrys = new String[]{"china"};//国家
    private static String[] provinces = new String[]{"guangdong","beijing","shanghai"};//省
    private static String[] citys = new String[]{"shenzheng","beijing","shanghai"};//市

    private static List<String> timesList = new ArrayList<String>();
    static{
        for(int i=0;i<5;i++){
            long openTime = genernateDateData(new Date());
            long scantime = openTime+random.nextInt(100)*1000;
            long jumpTime = scantime+random.nextInt(100)*1000;
            long leaveTime = jumpTime+random.nextInt(100)*1000;
            String resultstring = openTime+"=="+scantime+"=="+jumpTime+"=="+leaveTime;
            timesList.add(resultstring);
        }
    }



    private static MiniProgramProductHuoDong genernateData(){
        MiniProgramProductHuoDong xiaoChenXuProductHuoDong = new MiniProgramProductHuoDong();
        xiaoChenXuProductHuoDong.setDeviceId(deviceIds[random.nextInt(deviceIds.length)]);
        xiaoChenXuProductHuoDong.setHuodongId(huodongIdes[random.nextInt(huodongIdes.length)]);
        xiaoChenXuProductHuoDong.setUserId(userIds[random.nextInt(userIds.length)]);
        xiaoChenXuProductHuoDong.setIp(ips[random.nextInt(ips.length)]);
        xiaoChenXuProductHuoDong.setCountry(countrys[random.nextInt(countrys.length)]);
        xiaoChenXuProductHuoDong.setProvince(provinces[random.nextInt(provinces.length)]);
        xiaoChenXuProductHuoDong.setCity(citys[random.nextInt(citys.length)]);
        String timerandom = timesList.get(random.nextInt(timesList.size()));
        String openTime = timerandom.split("==")[0];
        String scanTime = timerandom.split("==")[1];
        String jumpTime = timerandom.split("==")[2];
        String leaveTime = timerandom.split("==")[3];
        xiaoChenXuProductHuoDong.setOpenTime(openTime);
        xiaoChenXuProductHuoDong.setScantime(scanTime);
        xiaoChenXuProductHuoDong.setJumpTime(jumpTime);
        xiaoChenXuProductHuoDong.setLeaveTime(leaveTime);
        //微信信息
        String weinxinInfo = weixinAccountinfoes[random.nextInt(weixinAccountinfoes.length)];
        String weixinAccount = weinxinInfo.split("==")[0];//微信号
        String weixinName = weinxinInfo.split("==")[1];//微信昵称
        String weixinSex = weinxinInfo.split("==")[2];//微信性别信息
        String weixinArea = weinxinInfo.split("==")[3];//微信地区
        xiaoChenXuProductHuoDong.setWeixinAccount(weixinAccount);
        xiaoChenXuProductHuoDong.setWeixinName(weixinName);
        xiaoChenXuProductHuoDong.setWeixinSex(weixinSex);
        xiaoChenXuProductHuoDong.setWeixinArea(weixinArea);
        xiaoChenXuProductHuoDong.setLogType(LogType.XIAOCHENXUHUODONG);
        return  xiaoChenXuProductHuoDong;

    }

    private static long genernateDateData(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,-days[random.nextInt(days.length)]);
        long resultTime = calendar.getTime().getTime();
        return resultTime;
    }
    private static void postHttpMethod(String urlpath,String data){
        try {
            URL url = new URL(urlpath);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
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
            while(inputStream.read(inputdata,0,1024) != -1){
                stringBuffer.append(new String (inputdata));
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
            MiniProgramProductHuoDong xiaoChenXuProductHuoDong = genernateData();
            System.out.println(JSONObject.toJSON(xiaoChenXuProductHuoDong));
            postHttpMethod("http://127.0.0.1:8081/collectAppData", JSONObject.toJSONString(xiaoChenXuProductHuoDong));
        }
    }
}
