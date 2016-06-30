import com.tomato.framework.log.annotation.Log;
import com.tomato.framework.log.annotation.LogModule;

/**
 * 系统日志测试用例
 * Created by Administrator on 2015/4/17.
 */

@LogModule("order module")
public class SysLogTest extends Thread {

    public static void main(String[] args) {
        SysLogTest demo = new SysLogTest();
        for (int i = 0; i < 1; i++) {
            demo.foo();
        }
//        demo.countAll();
//        demo.count();
//        demo.queryBasic();
//        demo.query();
    }

    /**
     * 日志记录
     */
    @Log(descr = "记录日志", before = true, module = "order", extension = "{\"a\":1,\"b\":\"zzz\"}")
    public void foo() {
        System.out.println("suc");
    }

    @Log(descr = "记录日志1", before = true, module = "order", extension = "{\"a\":1,\"b\":\"zzz\"}")
    public void foo(int i){
        System.out.println("fail");
    }

//    private void countAll() {
//        System.out.println(new SysLogClient().countAll());
//    }
//
//    private void count(){
//        Query query = new QueryTemplate().getQuery();
//        query = query.setKey("userId").eq("1968");
//        System.out.println(new SysLogClient().count(query));
//    }
//
//    private void query(){
//        Query query = new QueryTemplate().getQuery();
//        query = query.setKey("userId").eq("1968").or(query.setKey("extension.a").gte(1), query.setKey("time").lte("2015-04-29 03:41:35"));
//        List list = new SysLogClient().query(query, new Paging());
//        if (CollectionUtils.isNotEmpty(list)){
//            for (Object o : list) {
//                if(o instanceof SysLog){
//                    SysLog log = (SysLog)o;
//                    System.out.println(log.getExtension() + "\t" + log.getTime());
//                }
//            }
//        }
//    }
}
