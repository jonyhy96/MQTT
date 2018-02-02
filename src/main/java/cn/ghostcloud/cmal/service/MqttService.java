package cn.ghostcloud.cmal.service;

import cn.ghostcloud.cmal.enums.CallbackCode;
import cn.ghostcloud.cmal.enums.DataTypeEnum;
import cn.ghostcloud.cmal.web.DataHeader;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.Map;


@Service
public class MqttService {

    public static Logger logger = LoggerFactory.getLogger(MqttService.class);

    private String topic = "201JV7/shiyka/rangeEvent";
    private int Qos=2;
    MqttMessage message;

    @Autowired
    MqttConnect mqttConnect;

    public CallbackCode connect(){
        CallbackCode conresult = mqttConnect.connect();
        CallbackCode subreslt = Subscriber(topic,Qos);
        CallbackCode publisher = Publish(message);
      return conresult;
   }

    public CallbackCode Subscriber(String topic,int Qos) {
        CallbackCode subresult = mqttConnect.subscriber(topic,Qos);
        return subresult;
    }

    public CallbackCode Publish(MqttMessage message) {
        DataHeader<Object> dataHeader = new DataHeader<>();
        dataHeader.setEvent(DataTypeEnum.rangeEvent.toString());
        Map<String, String> data = new HashMap<>();
        data.put("code","A82B78AB73CA9215243CCCA1C43FA09D");
        data.put("carnum","黑DJB");
        data.put("driverName","");
        data.put("driverno", "");
        data.put("eventid", "895172950343028737");
        data.put("gpsno", "90016335");
        data.put("imei", "107015090016335");
        data.put("intTruckId", "D4BFD2B167C22575679420FD5E8CA1AF");
        data.put("latitude", "45.311760757237025");
        data.put("longitude", "130.97304330793787");
        data.put("media", "0");
        dataHeader.setData(data);
        int pubQoS = 2;
        message = new MqttMessage(SerializationUtils.serialize(dataHeader));
        message.setQos(pubQoS);
        message.setRetained(false);
        // Publish the message
        logger.info("Publishing to topic \"" + topic + "\" qos " + pubQoS);
        CallbackCode pubresult = mqttConnect.publisher(message);
        return pubresult;
    }
}