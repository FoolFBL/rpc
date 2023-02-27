package protocol;

import com.alibaba.fastjson.serializer.JSONSerializer;

/**
 * @author shijiu
 */
public interface Serializer {
    //把对象序列化成字节数组
    byte[] serialize(Object obj);
    //从字节数组反序列化成消息 使用java自带序列化方式不用messageType也能得到响应的对象
    Object deserialize(byte[] bytes,int messageType);
    //messageType返回时序列化方式是哪种
        //0.java自带序列化方式
        //1.json序列化方式
    int getType();
    //根据序号取出序列化器 暂时有两种方式 需要其他方式 实现这个接口即可
    static Serializer  getSerializerByCode(int code){
        switch (code){
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }


}
