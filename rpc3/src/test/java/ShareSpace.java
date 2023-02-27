import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shijiu
 * 演示多线程导致共享资源错乱
 */
public class ShareSpace {

    private static int sum = 1;
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                share();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                share();
            }
        }).start();
    }

    public static void share(){
        for (int i = 0; i < 5; i++) {
            System.out.println("==="+i);
            sum+=i;
            System.out.println("sum="+sum);
        }
    }
}
