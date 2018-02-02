package cn.ghostcloud.cmal.service.impl;

import cn.ghostcloud.cmal.enums.CallbackCode;
import cn.ghostcloud.cmal.enums.DataTypeEnum;
import cn.ghostcloud.cmal.service.ProducerService;
import cn.ghostcloud.cmal.web.DataHeader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 生产者服务
 * 具体处理各种数据的方法
 * 对每一种数据 （接口）  使用一个与Enum  名字相对应的 Topic .  [Enum].name()
 */
@Service
public class DefaultProducerService implements ProducerService {

    public static Logger logger = LoggerFactory.getLogger(DefaultProducerService.class);

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.34.44:9093");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
        return props;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }
    @Autowired
    private KafkaTemplate kafkaTemplate = kafkaTemplate();

    /**
     * 调用该方法处理数据
     *
     * @param type    _
     * @param payload _
     * @return 处理状态 0 为成功 其他失败
     */
    @Override
    public CallbackCode process(DataTypeEnum type, Object payload) {
        // 增加处理反馈
        //TODO change topic into dynamic event
        try {
            kafkaTemplate.send("Test1", payload).get(3, TimeUnit.SECONDS);
            logger.info("Send to kafka successed");
            return CallbackCode.OK;
        } catch (ExecutionException e) {
            logger.error("Connect to kafka server failed");
            return CallbackCode.INTERNAL_SERVER_DISCONNECTED;
        } catch (InterruptedException e) {
            logger.error("Operation has been interrupted");
            return CallbackCode.INTERRUPTED;
        } catch (TimeoutException e) {
            logger.error("Timeout to connect kafka server");
            return CallbackCode.INTERNAL_SERVER_TIMEOUT;
        }
    }

    /**
     * @param dataHeader _
     * @return _
     */
    @Override
    public CallbackCode process(DataHeader dataHeader) {
        DataTypeEnum dataTypeEnum;
        try {
            dataTypeEnum = DataTypeEnum.valueOf(dataHeader.getEvent());
            //不在事件类型枚举中的事件
        } catch (IllegalArgumentException e) {
            logger.error("Unsurported event type");
            return CallbackCode.UNSUPPORTED_EVENT_TYPE;
        }
        return process(dataTypeEnum, dataHeader.getData());
    }

    @Override
    public CallbackCode process(Object rawData) {
        return process(new ObjectMapper().convertValue(rawData, DataHeader.class));
    }

    @Override
    public CallbackCode process(byte[] rawData) {
        return process(SerializationUtils.deserialize(rawData));
    }

}
