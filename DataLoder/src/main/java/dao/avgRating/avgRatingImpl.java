package dao.avgRating;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import pojo.avgRating;

import java.util.ArrayList;
import java.util.List;


/**
 * @创建人 YDL
 * @创建时间 2020/6/3 17:33
 * @描述
 */
public class avgRatingImpl implements avgRatingDao {
    MongoCollection<Document> collection = getProductCollection();
    @Override
    public List<avgRating> selectAll() {
        List<avgRating> list = new ArrayList<>();
        FindIterable<Document> documents = collection.find();
        for(Document d:documents){
            avgRating avgRating = new avgRating();
            avgRating.setAvg((double)d.get("avg"));
            avgRating.setProductId((int)d.get("productId"));
            list.add(avgRating);
        }
        return list;
    }
}
