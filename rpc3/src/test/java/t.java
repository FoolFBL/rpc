import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author shijiu
 */
public class t {
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        Integer poll = queue.poll();
        System.out.println(poll);
        System.out.println(queue.isEmpty());
        System.out.println(queue.size());
    }

}
