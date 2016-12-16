package cn.qs.masterwork;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class TestCase {

    @Test
    public void testMasterWorker(){
        //构建任务list
        List<Object>myTaskList = new ArrayList<Object>();
        for(int i =0;i<10;i++){
            Task a = new Task(i);
            myTaskList.add(a);
        }
        //应用master-worker框架
        Master master = new Master(new MyWorker(), 10,"myWorker");
        master.submit(myTaskList);
        master.execute();
        Map<String ,Object> resultMap =master.getResultMap();
        //处理各线程返回的结果
        int count=0;
        while(resultMap.size()>0 || !master.isComplete()){
            for (String key : resultMap.keySet()) {
                System.out.println("value=" + resultMap.get(key));
                count+=Integer.parseInt(resultMap.get(key).toString());
                resultMap.remove(key);
            }
        }
        System.out.println(count);
    }
}
