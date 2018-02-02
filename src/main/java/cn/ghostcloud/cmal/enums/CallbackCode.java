package cn.ghostcloud.cmal.enums;

import cn.ghostcloud.cmal.web.Result;

/**
 * HTTP 接口返回值 枚举
 */
public enum CallbackCode {
    OK(0,"ok"),
    INVALID_SIGNATURE(-1,"无效签名"),
    UNSUPPORTED_EVENT_TYPE(-2,"不支持的事件类型"),
    INTERNAL_SERVER_DISCONNECTED(-3,"内部服务器无法连接"),
    INTERRUPTED(-4,"操作被打断"),
    INTERNAL_SERVER_TIMEOUT(-5,"内部服务器超时"),
    SUBSCRIBE_ERROR(-6,"订阅失败"),
    PUBLISH_ERROR(-7,"发布失败"),
    CONNECT_TO_MQTT_FAILED(-8,"连接MQTT服务器失败");


    private Result result = new Result(-1,"bad");

    CallbackCode(int code,String msg){
        this.result.setCode(code);
        this.result.setMsg(msg);
    }

    /**
     * @return 用于Json序列化的结构
     */
    public Result result(){
        return this.result;
    }

}
