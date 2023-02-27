package hook;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.Stack;

public class ShutdownHook {

         	    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

//         	    private final ExecutorService threadPool = ThreadPoolFactory.createDefaultThreadPool("shutdown-hook");
 	    /**
  	     *单例模式创建钩子，保证全局只有这一个钩子
  	     */
         	    private static final ShutdownHook shutdownHook = new ShutdownHook();

         	    public static ShutdownHook getShutdownHook(){
  	        return shutdownHook;
  	    }



         	    //注销服务的钩子
         	    public void addClearAllHook() {
//  	        logger.info("服务端关闭前将自动注销所有服务");
  	        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
             System.out.println("钩子函数在服务器关闭之前调用");
   	        }));
  	    }
 	}