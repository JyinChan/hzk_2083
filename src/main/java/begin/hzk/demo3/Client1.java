package begin.hzk.demo3;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

/*
get/remove并发测试
 */
public class Client1 {

    public static void main(String args[]) {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();
        IList<Integer> ilist = hazelcastInstance.getList("ilist");
        for(int i=0; i<20; i++) {
            ilist.add(i);
        }
        System.out.println("finish add ...");
        while(true) {
            ilist.remove(0);
            try {Thread.sleep(1000); } catch (InterruptedException ignore) {}
        }
    }
}
