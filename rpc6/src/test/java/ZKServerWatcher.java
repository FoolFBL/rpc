//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.Watcher.Event.EventType;
//import org.apache.zookeeper.ZooDefs.Ids;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//
///**
// * 监视服务器
// * @author tangxuelei
// *
// */
//public class ZKServerWatcher implements Watcher {
//	private String groupNode = "sgroup";
//	private ZooKeeper zk;
//	private Stat stat = new Stat();
//	//用Map存储服务器信息，key是服务器节点path，value是服务器信息。服务器信息又 包含了服务器名称和负载
//	private volatile Map<String, ServerInfo> serverMap = new HashMap<String, ServerInfo> ();
//
//	/*
//	 * 连接zk
//	 */
//	public void connectZooKeeper() throws Exception {
//		//这里的this就是注册了一个默认Watcher，不过注意该Watcher并不是一次性的。
//		zk = new ZooKeeper("127.0.0.1:2183", 2000, this);
//		//查看要检测的服务器集群的根节点是否存在，如果不存在，则创建
//        zk.create("/"+groupNode, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        updateServerList();
//	}
//
//
//	/*
//	 * 更新服务器列表
//	 */
//	private void updateServerList() throws Exception {
//		Map<String, ServerInfo> newServerMap = new HashMap<String, ServerInfo>();
//
//		// 获取并监听groupNode的子节点变化
//        // watch参数为true, 表示监听子节点变化事件.
//        // 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册
//
//		//这里的true表示注册监听，这里可以使用前面注册的默认Watcher，也可以自定义新的Watcher
//        List<String> subList=zk.getChildren("/"+groupNode,true);
//        for(String subNode:subList){
//            ServerInfo serverInfo=new ServerInfo();
//            serverInfo.setPath("/"+groupNode+"/"+subNode);
//            serverInfo.setName(subNode);
//            //获取每个子节点下关联的服务器负载的信息
//            byte[] data=zk.getData(serverInfo.getPath(), true, stat);	//注册监听
//            String loadBalance=new String(data,"utf-8");
//            serverInfo.setLoadBalance(loadBalance);
//            newServerMap.put(serverInfo.getPath(), serverInfo);
//
//        }
//        // 替换server列表
//        serverMap=newServerMap;
//        System.out.println("$$$更新了服务器列表:"+serverMap);
//	}
//
//	/*
//	 * 更新服务器节点的负载数据
//	 */
//	private void updataServerLoadBalance(String serverNodePath) throws Exception {
//		ServerInfo serverInfo=serverMap.get(serverNodePath);
//        if(null!=serverInfo){
//            //获取每个子节点下关联的服务器负载的信息
//            byte[] data=zk.getData(serverInfo.getPath(), true, stat);
//            String loadBalance=new String(data,"utf-8");
//            serverInfo.setLoadBalance(loadBalance);
//            serverMap.put(serverInfo.getPath(), serverInfo);
//            System.out.println("@@@更新了服务器的负载："+serverInfo);
//            System.out.println("------");
//            System.out.println("###更新服务器负载后，服务器列表信息："+serverMap);
//        }
//	}
//
//	/*
//	 *监听
//	 */
//	public void process(WatchedEvent event) {
//        System.out.println("监听到zookeeper事件-----eventType:"+event.getType()+",path:"+event.getPath());
//        //集群总节点的子节点变化触发的事件
//        if (event.getType() == EventType.NodeChildrenChanged &&
//                event.getPath().equals("/" + groupNode)) {
//             //如果发生了"/sgroup"节点下的子节点变化事件, 更新server列表, 并重新注册监听
//            try {
//                updateServerList();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (event.getType() == EventType.NodeDataChanged &&
//                event.getPath().startsWith("/" + groupNode)) {
//             //如果发生了服务器节点数据变化事件, 更新server列表, 并重新注册监听
//            try {
//                updataServerLoadBalance(event.getPath());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//	/*
//     * client的工作逻辑写在这个方法中
//     * 此处不做任何处理, 只让client sleep
//     */
//    public void handle() throws Exception{
//        Thread.sleep(Long.MAX_VALUE);
//    }
//
//    /*
//     * 主函数
//     */
//    public static void main(String[] args) throws Exception {
//        ZKServerWatcher zkw=new ZKServerWatcher();
//        zkw.connectZooKeeper();
//        zkw.handle();
//    }
//}