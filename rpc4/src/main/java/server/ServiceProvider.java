package server;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shijiu
 */
@Data
public class ServiceProvider {

    private Map<String,Object> interfaceProvider;

    public ServiceProvider() {
        this.interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object service){
//        String serviceName = service.getClass().getName();
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            interfaceProvider.put(anInterface.getName(),service);
        }
    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }



}
