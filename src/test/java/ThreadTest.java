import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by bayllech on 2017/3/29.
 */
public class ThreadTest {
    public static void main(String args[]) {
        Thread t = new Thread() {
            public void run() {
                pong();
            }
        };
        t.start();
        System.out.print("ping");
    }
    static void pong() {
        System.out.print("pong");
    }

    @Test
    public void iterotorTest() {
        List<String> list = Arrays.asList("aa", "bb", "cc");
        /*for (Iterator iterator = list.iterator() ; iterator.hasNext();  ) {
            Object s = iterator.next();
            System.out.println(s);
        }*/
        for (String s : list) {
            System.out.println(s);
        }
    }
}
