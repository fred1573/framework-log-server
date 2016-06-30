import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tomato.framework.log.client.BizLogClient;
import com.tomato.framework.log.model.BizLog;
import com.tomato.framework.log.support.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 业务日志测试用例
 * Created by Administrator on 2015/4/17.
 */

public class BizLogTest extends Thread {

    @Test
    public void query() {
//        Calendar cal = Calendar.getInstance();
//        Date time1 = cal.getTime();
//        cal.add(Calendar.DAY_OF_WEEK, -2);
//        Date time2 = cal.getTime();
//        Operation oper1 = new Operation("data.localDate", time1, Operator.LTE);
//        Operation oper2 = new Operation("data.localDate", time2, Operator.GT);
//        Operation operation = new Operation(oper1, oper2, Operator.AND);
        Operation operation = new Operation("innId", 200, Operator.GT);
//        new BizLogClient().find(operation, new GroupBy("innId"));
        List<OrderBy> orderByList = new ArrayList<>();
        orderByList.add(OrderBy.where("data.localTime").desc());
        new BizLogClient().find(new Paging(), operation, null);
    }

    public void save() {
//        new BizLogClient().save(new BizLog(49463, ));
    }


    private class Type{
        private int id;
        private int pId;

        public Type(int id, int pId) {
            this.id = id;
            this.pId = pId;
        }
    }
}
