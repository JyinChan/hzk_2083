package begin.hzk.demo1.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HZKClient {

    private static HazelcastInstance hazelcastInstance;

    public static void main(String args[]) {

        hazelcastInstance = HazelcastClient.newHazelcastClient();
        reconnectTest();
//        visit();
    }

    private static void visit() {
        IMap<String, Integer> map = hazelcastInstance.getMap("map_F");
        map.forEach((k, v) -> {
            System.out.println(k+" "+v);
        });
        System.out.println(map.size());
    }

    //测试断掉连接是否会造成Listener失效
    //测试方法：1开启两个server；2开启client；3开启测试；4断掉一个server再重启
    private static void reconnectTest() {
        IMap<String, Integer> map = hazelcastInstance.getMap("map_F");

        map.addEntryListener(new EntryAddedListener<String, Integer>() {
            public void entryAdded(EntryEvent<String, Integer> event) {
                System.out.println("entry add..."+event.getValue());
            }
        }, true);
        map.addEntryListener(new EntryUpdatedListener<String, Integer>() {
            @Override
            public void entryUpdated(EntryEvent<String, Integer> event) {
                System.out.println("entry update..."+event.getValue());
            }
        }, true);
    }

    //测试方法：调试addEntryListener直至entryAdd触发
    private static void listenerTest() {
        IMap<String, Integer> map = hazelcastInstance.getMap("map_F");

        System.out.println("add listener start...");

        map.addEntryListener(new EntryAddedListener<String, Integer>() {
            public void entryAdded(EntryEvent<String, Integer> var1) {
                //该调用可能早于map.addEntryListener返回，因结果返回消息和节点增加通知消息，两种消息处理线程不一致
                System.out.println("entry add...");
            }
        }, true);
        System.out.println("add finished...");
    }

    //测试map中每个节点的增删改通知是否保证由同一个线程完成
    private static void nodeTest() {
        IMap<String, Integer> map = hazelcastInstance.getMap("map_F");

        Map<String, String> threadMap = new ConcurrentHashMap<>();

        System.out.println("add listener start...");

        map.addEntryListener(new EntryAddedListener<String, Integer>() {
            private String name = "add";
            public void entryAdded(EntryEvent<String, Integer> var1) {
                threadMap.put(var1.getKey(), Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName()+" listener:"+name+" active. data="+var1.getKey()+":"+var1.getValue());
            }
        }, true);

        map.addEntryListener(new EntryUpdatedListener<String, Integer>() {
            private String name = "update";
            @Override
            public void entryUpdated(EntryEvent<String, Integer> event) {
                System.out.println(Thread.currentThread().getName()+" listener:"+name+" active. data="+event.getKey()+":"+event.getValue());
                if(!threadMap.get(event.getKey()).equals(Thread.currentThread().getName())) {
                    System.exit(0);
                }
                try {Thread.sleep(2000); } catch (InterruptedException ignore) {}
            }
        }, true);

        map.addEntryListener(new EntryAddedListener<String, Integer>() {
            private String name = "B";
            public void entryAdded(EntryEvent<String, Integer> var1) {
                System.out.println("listener:"+name+" active");
            }
        }, true);
    }

    private static void createTest() {
        IMap<String, Integer>[] maps = new IMap[100000];
        for(int i=0; i<100000; i++) {
            maps[i] = hazelcastInstance.getMap("map_F"+i);
        }

        for(int i=0; i<100000; i++) {
            IMap<String, Integer> t = hazelcastInstance.getMap("map_F"+i);
            System.out.println(maps[i] == t);
            if(maps[i] != t) break;
        }

        System.out.println("create finished...");
    }
}
