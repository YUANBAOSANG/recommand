import dao.productRecOff.ProductRecOffDao;
import dao.productRecOff.ProductRecOffDaoImpl;
import org.junit.Test;
import pojo.ProductRecOff;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 1:41
 * @描述
 */
public class ProductRecOffTest {
    ProductRecOffDao productRecOffDao = new ProductRecOffDaoImpl();
    @Test
    public void testGetRecListAll(){

        List<ProductRecOff> productRecOff = productRecOffDao.getRecListAll(8195);
        System.out.print("");
    }
    @Test
    public void testGetRecListList(){

      ProductRecOff productRecOff  = productRecOffDao.getRecListList(8195);
      System.out.print("");
    }
    @Test
    public void testgetRecListList(){
        ProductRecOff productRecOff  = productRecOffDao.getRecListList(8195,158868013583L);
        System.out.print("");
    }
}
