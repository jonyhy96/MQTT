package cn.ghostcloud.cmal.service.impl;

import cn.ghostcloud.cmal.config.PushProperties;
import cn.ghostcloud.cmal.enums.CallbackCode;
import cn.ghostcloud.cmal.service.MqttConnect;
import cn.ghostcloud.cmal.service.MqttService;
import cn.ghostcloud.cmal.service.ProducerService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * Mqtt连接服务
 */
@Service
public class DefaultMqttConnect implements MqttConnect,MqttCallback,Runnable {

    public static Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    ProducerService producerService;

    @Autowired
    PushProperties pushProperties;

    MqttClient myClient;

    MqttConnectOptions connOpt;

    @Value("${push.mqtt.brokerUrl}")
    private String BROKER_URL;
    static final String M2MIO_DOMAIN = "201JV7";
    static final String M2MIO_THING = "shiyka/rangeEvent";

    private String clientID;
    private String Topic;//subscriber topic
    MqttTopic topic;//publisher topic
    @Value("${push.mqtt.subscriber}")
    private Boolean subscriber=true;
    @Value("${push.mqtt.publisher}")
    private Boolean publisher=true;

    @Override
    public void connectionLost(Throwable t) {
        logger.warn("Connection with MQTT lost!");
        publisher=false;
        subscriber=false;
        // code to reconnect to the broker would go here if desired
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        try{
            CallbackCode result = producerService.process(mqttMessage.getPayload());
            logger.info("Mqtt Send to kafka successed");
        }catch (Exception e){
            logger.error("Mqtt Send to kafka failed");
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        try {
            logger.info("Pub complete" + iMqttDeliveryToken.getMessage().toString());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public MqttConnectOptions Setup() {
        // setup MQTT Client
        clientID = M2MIO_THING;
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);
        connOpt.setUserName("admin");
        connOpt.setPassword("password".toCharArray());
        // setup topic
        // topics on m2m.io are in the form <domain>/<stuff>/<thing>
        Topic = M2MIO_DOMAIN + "/" + M2MIO_THING;
        topic = myClient.getTopic(Topic);
        return connOpt;
    }

    @Override
    public CallbackCode subscriber(String subtopic, int Qos) {
        try {
            int subQoS = 2;//This must be 0 ,or it will has conflict with the line 161 Thread.sleep(5000);
            myClient.subscribe(subtopic, subQoS);
        } catch (Exception e) {
            e.printStackTrace();
            return CallbackCode.SUBSCRIBE_ERROR;
        }
        return CallbackCode.OK;
    }

    @Override
    public CallbackCode publisher(MqttMessage message) {
            MqttDeliveryToken token = null;
            try {
                // publish message to broker
                token = topic.publish(message);
                // Wait until the message has been delivered to the broker
                token.waitForCompletion();
                Thread.sleep(100);
            } catch (Exception e) {
                logger.warn("disconnect failed");
                return CallbackCode.PUBLISH_ERROR;
            }
        return CallbackCode.OK;
        }



    @Override
    public CallbackCode connect() {
        try {
            run();
            return  CallbackCode.OK;
        }catch (Exception e){
            e.printStackTrace();
            return CallbackCode.CONNECT_TO_MQTT_FAILED;
        }
    }

    @Override
    public void run() {
        while (pushProperties.getMqtt().getSubscriber()) {
            // Connect to Broker
            try {
                myClient = new MqttClient("tcp://192.168.34.32:61613",  "shiyka/rangeEvent");
                myClient.setCallback(this);
                myClient.connect(Setup());
                logger.info("Connect success");
                break;
            } catch (MqttException e) {
                e.printStackTrace();
                logger.warn("Connect error");
                try {
                    logger.info("sleep 3 seconds and reconnect");
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    logger.error("thread sleep error");
                    e1.printStackTrace();
                }
            }
            logger.info("Connected to " + BROKER_URL);
        }
//        new Runnable(){
//
//            @Override
//            public void run() {
//                CallbackCode subreslt = subscriber(Topic,2);
//            }
//        };
//        new Runnable(){
//
//            @Override
//            public void run() {
//                CallbackCode subreslt = publisher(new MqttMessage());
//            }
//        };
    }
}
