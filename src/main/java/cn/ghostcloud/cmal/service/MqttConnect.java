package cn.ghostcloud.cmal.service;

import cn.ghostcloud.cmal.enums.CallbackCode;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MqttConnect {
    CallbackCode subscriber(String topic,int Qos);
    CallbackCode publisher(MqttMessage message);
    CallbackCode connect();
}
