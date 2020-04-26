package begin.hzk.demo2;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

import java.util.Iterator;

public class Client2 {

    public static void main(String args[]) {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();
        IList<Integer> ilist = hazelcastInstance.getList("ilist");
        int c=0;
        Iterator<Integer> it = ilist.iterator();
        while(it.hasNext()) {
            c++;
            it.next();
        }
        System.out.println(c);
    }
}
