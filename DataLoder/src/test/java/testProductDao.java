import dao.BaseDao;
import dao.product.ProductDao;
import dao.product.ProductDaoImpl;
import org.junit.Test;
import pojo.Product;

/**
 * @创建人 YDL
 * @创建时间 2020/4/29 2:30
 * @描述
 */
public class testProductDao {
    ProductDao productDao = new ProductDaoImpl();
    Product product = new Product.ProductBuider().productId(5).
            categories("dadasdasd").imgUrl("222").name("dasdsa").tags("dasda").buide();
    @Test
    public void testInsert()  {
        productDao.insert(product);
        BaseDao.Datader.close();
    }
    @Test
    public void testUpdate(){
        product.setImgUrl("我是你爸爸");
        productDao.update(product);
        BaseDao.Datader.close();
    }

    @Test
    public void testSelect(){
        Product product = productDao.selectById(5);
        System.out.println(product);
        BaseDao.Datader.close();
    }
    @Test
    public void testDelect(){
        productDao.delect(5);
        BaseDao.Datader.close();
    }
}
