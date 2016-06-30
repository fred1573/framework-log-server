import com.tomato.log.dao.MongoDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2015/12/19.
 */
public class SpringContextText {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        MongoDaoImpl logDao = (MongoDaoImpl) ctx.getBean("mongoDao");
        System.out.println(logDao);
    }
}
