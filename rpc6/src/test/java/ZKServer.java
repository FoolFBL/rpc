import java.util.Random;
import java.util.Scanner;
 
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
 
 
public class ZKServer {
	//Zookeeper中集群服务器的总结点
	private String groupNode = "sgroup";
	private ZooKeeper zooKeeper;
	private String serverPath = "";
	//服务器创建的节点的路径
	private String serverNodePath = "";
	//当前服务器的负载
	private int loadBalance = 0;
	
	/*
	 * 连接Zookeeper服务器
	 */
	public void connectZookeeper(String zookeeperServer, String serverName) throws Exception {
		zooKeeper = new ZooKeeper(zookeeperServer,5000, new Watcher(){
			public void process(WatchedEvent event) {
				//这里不做处理
			}
		});
		
		//先判断sgroup节点是否存在
		String groupNodePath = "/" + groupNode;
		Stat stat = zooKeeper.exists(groupNodePath, false);
		if (stat == null) {
			zooKeeper.create(groupNodePath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		//将Server的地址数据关联到新创建的子节点上
		serverPath = zooKeeper.create(groupNodePath+"/"+serverName, String.valueOf(loadBalance).getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("创建了server节点："+serverNodePath);
		
		//定时上传服务器的负载
		//uploadBarance();
	}
 
	
	/*
	 * 关于于zookeeper服务器的连接
	 */
	public void closeZookeeper() throws Exception {
		if (zooKeeper != null) {
			zooKeeper.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("请输入服务器的名称：");
		Scanner scanner = new Scanner(System.in);
		String serverName = scanner.nextLine();
		ZKServer zkServer = new ZKServer();
		zkServer.connectZookeeper("127.0.0.1:2183", serverName);
		
		while (true) {
			System.out.println("请输入您的操作指令(exit退出系统)");
			String command = scanner.nextLine();
			if ("exit".equals(command)) {
				System.out.println("服务器关闭中.....");
				zkServer.zooKeeper.close();
				System.exit(0);
				break;
			} else {
				continue;
			}
		}
		
	}
}