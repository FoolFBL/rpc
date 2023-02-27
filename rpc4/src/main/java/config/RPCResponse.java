package config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shijiu
 * 上个demo中response传递的是User对象 显然在一个应用中我们不可能只传输一种类型的数据
 * 由此我们要将对象抽象为Object
 * RPC要经过网络传输 所以有可能失败 类似于http 引入状态码和状态信息表示服务调用成功还是失败
 */
@Data
@Builder
@AllArgsConstructor
public class RPCResponse implements Serializable {
    //状态信息
    private int code;
    private String message;
    // 更新,这里我们需要加入这个，不然用其它序列化方式（除了java Serialize）得不到data的type
    private Class<?> dataType;
    //具体数据
    private Object data;

    public static RPCResponse success(Object data){
        return RPCResponse.builder().code(200).data(data).build();
    }

    public static RPCResponse fail(){
        return RPCResponse.builder().code(500).data("服务器发生错误").build();
    }
}
