package cn.ghostcloud.cmal.service;


import cn.ghostcloud.cmal.enums.DataTypeEnum;

/**
 * 消费者服务
 */
public interface ConsumerService {

    /**
     * 消费方法
     * @param type 数据类型
     * @param payload 内容
     * @return 处理结果
     */
    int process(DataTypeEnum type,Object payload);

}
