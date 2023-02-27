import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author shijiu
 */
public class DeadLock {

    private static Object lockA = new Object();
    private static Object lockB = new Object();
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                method1();
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                method2();
            }
        }).start();

    }

    public static void method1() throws InterruptedException {
        synchronized (lockA){
            System.out.println("method 1 1");
            Thread.sleep(1000);
            synchronized (lockB){
                System.out.println("method 1 2");
            }
        }
    }

    public static void method2() throws InterruptedException {
        synchronized (lockB){
            System.out.println("method 2 1");
            Thread.sleep(1000);
            synchronized (lockA){
                System.out.println("method 2 2");
            }
        }
    }

}
