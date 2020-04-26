package begin.hzk.demo3;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

public class Client2 {

    public static void main(String args[]) {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();
        IList<Integer> ilist = hazelcastInstance.getList("ilist");

//        int i = ilist.size();
//        for(int j=0; j<i; j++) {
//            System.out.println(ilist.get(j));
//            try { Thread.sleep(1000);}catch (InterruptedException ignore) {}
//        }

        ilist.iterator().forEachRemaining(e -> {
            System.out.println(e);
            try { Thread.sleep(1000);}catch (InterruptedException ignore) {}
        });

    }
}
