package dao.product;

import com.mongodb.client.MongoCollection;
import dao.BaseDao;
import org.bson.Document;
import pojo.Product;

/**
 * @创建人 YDL
 * @创建时间 2020/4/28 21:49
 * @描述
 */
public interface ProductDao {
    void insert(Product product);
    void delect(int productId);
    void update(Product product);
    Product selectById(int ProductId);
    default MongoCollection<Document> getProductCollection(){
        return BaseDao.Datader.getProductConllection();
    }
}
