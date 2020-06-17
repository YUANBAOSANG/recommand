package dao.preRating;

import com.mongodb.client.MongoCollection;
import dao.BaseDao;
import org.bson.Document;
import pojo.preRating;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/6/3 19:43
 * @描述
 */
public interface PreRatingDao {
    List<preRating> selectAll();
    default MongoCollection<Document> getProductCollection(){
        return BaseDao.Datader.getPreConllection();
    }
}
