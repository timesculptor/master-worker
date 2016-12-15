package cn.qs.masterwork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Master {

    private ConcurrentLinkedQueue<Object> workQueue = new ConcurrentLinkedQueue<Object>();
    private HashMap<String, Thread> workers = new HashMap<String, Thread>();
    private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();

    public Master(Worker worker , int workerCount,String workerName){
        worker.setWorkQueue(this.workQueue);
        worker.setResultMap(this.resultMap);
        for(int i = 0; i < workerCount; i ++){
            this.workers.put(workerName+Integer.toString(i), new Thread(worker));
        }

    }

    public void submit(List<Object> taskList){
        for(Object task: taskList){
            this.workQueue.add(task);
        }
    }



    public void execute(){
        for(Map.Entry<String, Thread> me : workers.entrySet()){
            me.getValue().start();
        }
    }

    public boolean isComplete() {
        for(Map.Entry<String, Thread> me : workers.entrySet()){
            if(me.getValue().getState() != Thread.State.TERMINATED){
                return false;
            }
        }
        return true;
    }

    public Map<String,Object> getResultMap() {
        return resultMap;
    }

}
