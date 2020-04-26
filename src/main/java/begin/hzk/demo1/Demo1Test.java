package begin.hzk.demo1;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Demo1Test {

    public static void main(String args[]) throws Exception {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();
        IMap<String, Integer> map = hazelcastInstance.getMap("map_F");
        int j = 0;
        while(true) {
            for(int i=0; i<5; i++) map.put("key"+i, j++);
            Thread.sleep(800);
        }
    }
}
