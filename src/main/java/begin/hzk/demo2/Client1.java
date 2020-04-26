package begin.hzk.demo2;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

public class Client1 {

    public static void main(String args[]) {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();
        IList<Integer> ilist = hazelcastInstance.getList("ilist");
        for(int i=0; i<100000; i++) {
            ilist.add(i);
        }
        System.out.println("finish add ...");
    }
}
