import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class test {
    @Test
    public void aaa(){
//   String a="_One_";
//       String b=a;
//       b=a.trim();
//        System.out.println(a);
//        System.out.println(b);

        List<Object> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        Iterator<Object> it = list.iterator();
        while (it.hasNext()){
            it.next();
            it.remove();
        }
        System.out.println(list);

    }
}
