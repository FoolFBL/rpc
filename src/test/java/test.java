import java.util.*;

/**
 * @author shijiu
 */
public class test {
    public static void main(String[] args) {
        HashSet<Object> objects = new HashSet<>();
        objects.add(1);


        objects.add(2);
        objects.add(2);

        for (Object object : objects) {
            System.out.println(object);
        }
    }
}
