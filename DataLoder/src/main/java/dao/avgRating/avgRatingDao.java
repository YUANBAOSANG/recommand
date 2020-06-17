package dao.avgRating;

import com.mongodb.client.MongoCollection;
import dao.BaseDao;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.bson.Document;
import pojo.ProductRecOff;
import pojo.avgRating;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/6/3 17:32
 * @描述
 */
public interface avgRatingDao {
    List<avgRating> selectAll();
    default MongoCollection<Document> getProductCollection(){
        return BaseDao.Datader.getAvgConllection();
    }

}
