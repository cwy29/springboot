import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.Set;

@RunWith(SpringRunner.class)
public class SpringBootTest {

    @Test
    public  void JedisTest(){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.set("k1","v1");
        jedis.set("k2","v2");
        System.out.println(jedis.get("k1"));
        Set<String>keylist=jedis.keys("*");
        System.out.println("***********"+keylist.size()+"***********");
        for (String key:keylist)
        {
            System.out.println(key+"***********"+jedis.get(key));
        }
    }
}
