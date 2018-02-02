package cn.ghostcloud.cmal.service;

/**
 * 存储服务 消费端对接使用
 * 将从/kafka 的数据转储
 */
public interface StorageService {

    void saveToHBase();

    void saveToHadoop();

    void saveToMysql();
}
