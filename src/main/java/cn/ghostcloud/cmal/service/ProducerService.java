package cn.ghostcloud.cmal.service;


import cn.ghostcloud.cmal.enums.CallbackCode;
import cn.ghostcloud.cmal.enums.DataTypeEnum;
import cn.ghostcloud.cmal.web.DataHeader;

/**
 * @param <T>
 */
public interface ProducerService<T> {


    /**
     * @param type 数据类型
     * @param payload 数据内容   原始Json中的  data字段内容
     * @return 处理状态
     */
    CallbackCode process(DataTypeEnum type, Object payload);

    /**
     * @param dataHeader _
     * @return 处理状态
     */
    CallbackCode process(DataHeader dataHeader);

    /**
     *增加对原始数据的处理
     *
     * @param rawData 从接口获取到的数据 Object 类型
     * @return 处理结果
     */
    CallbackCode process(Object rawData);

    /**
     * 原始数据的处理
     * @param rawData rawData 从接口获取到的数据 byte[] 类型
     * @return 处理结果
     */
    CallbackCode process(byte[] rawData);
}
