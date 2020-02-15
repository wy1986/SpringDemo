package com.example.input;

import com.example.enums.Country;
import com.example.enums.LogType;

public class UserActionLog {

    private String userName;//用户名
    private String userId;//用户id
    private String latestUseTime;//最近使用时间
    private String latestUseIp;//最近使用ip
    private Country country;//国家
    private String province;//省
    private String city;//市

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLatestUseTime() {
        return latestUseTime;
    }

    public void setLatestUseTime(String latestUseTime) {
        this.latestUseTime = latestUseTime;
    }

    public String getLatestUseIp() {
        return latestUseIp;
    }

    public void setLatestUseIp(String latestUseIp) {
        this.latestUseIp = latestUseIp;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
