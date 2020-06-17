package dao.reting;

import com.mongodb.client.MongoCollection;
import dao.BaseDao;
import org.bson.Document;
import pojo.Reting;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/4/29 3:25
 * @描述
 */
public interface retingDao {
    void insert(Reting reting);
    void delectByUserId(int userId);
    void delectByProductId(int productId);
    List<Reting> selectByUserId(int userId);
    List<Reting> selectByProductId(int productId);
    default MongoCollection<Document> getRetingCollection(){
        return BaseDao.Datader.getRetingConllection();
    }
}
