package com.example;

import com.alibaba.fastjson.JSONObject;
import com.example.enums.LogType;
import com.example.input.PcProductCartLog;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class PcCartGenerateData {


    private static int[] days  = new int[]{3,4,5,6};
    static Random random = new Random();

    private static String[] cookiees = new String[]{"cookie1","cookie2","cookie3","cookie4",""};
    private static String[] remoteNamees = new String[] {"客户端1","客户端2","客户端3",""};//客户端名称
    private static String[] deviceTypees = new String[]{"pc","手机","pad"};

    private static String[] oses = new String[]{"window10","window8","ios","linux"};//操作系统
    private static String[] broweres = new String[]{"谷歌","火狐","ie"};//浏览器信息
    private static String[] resolutiones = new String[]{"400*800","1200*700","1500*900"};//分辨率
    private static String[] sourceInfoes = new String[]{"谷歌","百度",""};//搜索引擎
    private static String[] sourceTypees = new String[]{"内网","搜索引擎","直接输入网址"};//跳转源类型
    private static String[] srcDomaines = new String[]{"www.youfan.com/shagn1","www.baidu.com/erere",""};//跳转来源地址
    private static  String[] operatorTypees = new String[]{"0","1"};
    private static String[] productIds = new String[]{"1","2","3"};//产品id
    private static String[] pindaoIds = new String[]{"1","2"};//频道id
    private static String[] productTypeIs = new String[]{"2","3"};//产品类别id
    private static String[] macAdresses = new String[]{"40-8d-5c","29-7c-5f"};
    private static String[] userIds = new String[]{"1","2"};
    private static String[] deviceIds = new String[]{"deviceId1","deviceId2"};
    private static String[] ips = new String[]{"192.168.4.5","192.168.4.6"};//用户app访问的ip
    private static String[] countrys = new String[]{"china"};//国家
    private static String[] provinces = new String[]{"guangdong","beijing","shanghai"};//省
    private static String[] citys = new String[]{"shenzheng","beijing","shanghai"};//市

    private static List<String> timesList = new ArrayList<String>();
    static{
        for(int i=0;i<5;i++){
            long operatorTime = genernateDateData(new Date());
            timesList.add(operatorTime+"");
        }
    }



    private static PcProductCartLog genernateData(){
        PcProductCartLog  pcProductCartLog  = new PcProductCartLog();
        int randomSelect = random.nextInt(3);
        if(randomSelect > 1){
            pcProductCartLog.setDeviceId(deviceIds[random.nextInt(deviceIds.length)]);
        }else {
            pcProductCartLog.setMacAdress(macAdresses[random.nextInt(macAdresses.length)]);
        }
        pcProductCartLog.setUserId(userIds[random.nextInt(userIds.length)]);
        pcProductCartLog.setIp(ips[random.nextInt(ips.length)]);
        pcProductCartLog.setCountry(countrys[random.nextInt(countrys.length)]);
        pcProductCartLog.setProvince(provinces[random.nextInt(provinces.length)]);
        pcProductCartLog.setCity(citys[random.nextInt(citys.length)]);
        String timerandom = timesList.get(random.nextInt(timesList.size()));
        pcProductCartLog.setOperatorTime(timerandom);
        pcProductCartLog.setOperatorType(operatorTypees[random.nextInt(operatorTypees.length)]);
        pcProductCartLog.setProductTypeId(productTypeIs[random.nextInt(productTypeIs.length)]);
        pcProductCartLog.setProductId(productIds[random.nextInt(productIds.length)]);
        pcProductCartLog.setPindaoId(pindaoIds[random.nextInt(pindaoIds.length)]);

        pcProductCartLog.setCookie(cookiees[random.nextInt(cookiees.length)]);
        pcProductCartLog.setRemoteName(remoteNamees[random.nextInt(remoteNamees.length)]);
        pcProductCartLog.setDeviceType(deviceTypees[random.nextInt(deviceTypees.length)]);
        pcProductCartLog.setOs(oses[random.nextInt(oses.length)]);
        pcProductCartLog.setBrower(broweres[random.nextInt(broweres.length)]);
        pcProductCartLog.setResolution(resolutiones[random.nextInt(resolutiones.length)]);
        pcProductCartLog.setSourceInfo(sourceInfoes[random.nextInt(sourceInfoes.length)]);
        pcProductCartLog.setSourceType(sourceTypees[random.nextInt(sourceTypees.length)]);
        pcProductCartLog.setSrcDomain(srcDomaines[random.nextInt(srcDomaines.length)]);
        pcProductCartLog.setLogType(LogType.PCCART);
        return  pcProductCartLog;

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
        for(int i=0;i<50;i++) {
            PcProductCartLog pcProductCartLog = genernateData();
            System.out.println(JSONObject.toJSON(pcProductCartLog));
            postHttpMethod("http://127.0.0.1:8081/collectAppData", JSONObject.toJSONString(pcProductCartLog));
        }
    }
}
