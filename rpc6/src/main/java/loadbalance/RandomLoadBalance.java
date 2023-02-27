package loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @author shijiu
 */
public class RandomLoadBalance implements LoadBalance{
    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();
        int choice = random.nextInt(addressList.size());
        System.out.println("负载均衡策略选择了"+choice+"服务器");
        return addressList.get(choice);
    }
}
