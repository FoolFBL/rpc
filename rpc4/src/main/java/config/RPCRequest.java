package config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shijiu
 * 在上个demo中 我们传过去了一个userid参数过去 这显然不合理
 * 因为服务端不会只有一个服务一个方法 因此只传递参数不知道会调用哪个方法
 * 因此在一个RPC请求中 client发送应该是需要调用的service接口名 方法名 参数 参数类型
 * 这样服务端就能根据这些信息通过反射调用相应的方法
 * 这是使用java自带的序列化方法
 */
@Data
@Builder
@AllArgsConstructor
public class RPCRequest implements Serializable {
    //服务类名
    private String interfaceName;
    //方法名
    private String methodName;
    //参数列表
    private Object[] params;
    //参数类型
    private Class<?>[] paramTypes;
}
