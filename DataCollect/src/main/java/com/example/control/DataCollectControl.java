package com.example.control;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataCollectControl {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "collectDataToMemory", method = RequestMethod.POST)
    public void collectDataToMemory(@RequestBody String data) {
        System.out.println(data);
    }

    @RequestMapping(value = "collectDataToKafka", method = RequestMethod.POST)
    public void collectDataToKafka(@RequestBody String data) {
        System.out.println(data);
        JSONObject jsonObject = JSONObject.parseObject(data);
//        String LogType = jsonObject.getString("logType");
        String topicname = "test";
//        if (LogType.startsWith("APP")) {//appinfo
//            topicname = "appinfo";
//        } else if (LogType.startsWith("XIAOCHENXU")) {//xiaochegnxuinfo
//            topicname = "xiaochegnxuinfo";
//        } else if (LogType.startsWith("PC")) {//pcinfo
//            topicname = "pcinfo";
//        }
//        System.out.println(LogType);

        kafkaTemplate.send(topicname, data);
    }
}
