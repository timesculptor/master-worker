package cn.qs.masterwork;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class  Worker implements Runnable {

    private ConcurrentLinkedQueue<Object> workQueue;
    private ConcurrentHashMap<String, Object> resultMap;

    public void setWorkQueue(ConcurrentLinkedQueue<Object> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        while(true){
            Object task = this.workQueue.poll();
            if(task == null){
                break;
            }
            Object output = handle(task);
            //返回结果key暂用用任务hash ？？是否可用其他可识别替代
            this.resultMap.put(Integer.toString(task.hashCode()), output);
        }
    }

    //需要子类实现
    public abstract Object handle(Object input);

}
