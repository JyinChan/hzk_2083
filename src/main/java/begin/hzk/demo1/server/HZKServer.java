package begin.hzk.demo1.server;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HZKServer {

    public static void main(String args[]) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

    }
}
