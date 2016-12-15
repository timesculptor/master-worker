package cn.qs.masterwork;


public class MyWorker extends Worker{

    @Override
    public Object handle(Object task) {
        //处理任务
        Task mytask = (Task)task;
        return mytask.getData()*10;
    }
}
