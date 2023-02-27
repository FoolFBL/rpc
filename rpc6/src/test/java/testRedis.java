
import redis.clients.jedis.Jedis;

/**
 * @author shijiu
 */


public class testRedis {
    public static void main(String[] args) {
        System.out.println("pooo");
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
    }
}
