import dao.reting.retingDao;
import dao.reting.retingDaoImpl;
import org.junit.Test;
import pojo.Reting;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/2 0:50
 * @描述
 */
public class RetingTest {
    @Test
    public void testSelect(){
        retingDao retingDao = new retingDaoImpl();
        List<Reting> list = retingDao.selectByUserId(5040);
        for(Reting r:list){
            System.out.println("---------reting"+r);
        }
        System.out.println();
    }

}
