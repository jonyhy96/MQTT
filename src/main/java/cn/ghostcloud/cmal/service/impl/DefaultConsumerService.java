package cn.ghostcloud.cmal.service.impl;

import cn.ghostcloud.cmal.enums.DataTypeEnum;
import cn.ghostcloud.cmal.service.ConsumerService;
import cn.ghostcloud.cmal.web.DataHeader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * 消费者服务
 */
@Service
@EnableKafka
public class DefaultConsumerService implements ConsumerService {

    public static Logger logger = LoggerFactory.getLogger(DefaultConsumerService.class);
    @Override
    public int process(DataTypeEnum type, Object payload) {
        switch (type){
            case rangeEvent: rangeEvent(payload);
            break;

        }
        return -1;
    }

    private DataHeader convertToDataHeader(Object rawData){
        return new ObjectMapper().convertValue(rawData, DataHeader.class);
    }


    @KafkaListener(topics = "default")
    public void defaultEvent(Object data){
        System.out.println(convertToDataHeader(data));
        // 消费
    }

    @KafkaListener(topics = "rangeEvent")
    public void rangeEvent(Object payload){
        logger.info("Get message from rangeEvent");
        logger.info(payload.toString());
        logger.info("Get message from rangeEvent successed");
    }

    @KafkaListener(topics = "unknownEvent")
    public void unknownEvent(Object payload){
        logger.info("Get message from unknownEvent");
        logger.info(payload.toString());
        logger.info("Get message from unknownEvent successed");
    }

    @KafkaListener(topics = "Test")
    public void TestEvent(Object payload){
        logger.info("Get message from rangeEvent");
        logger.info(payload.toString());
        logger.info("Get message from rangeEvent successed");
    }



}
