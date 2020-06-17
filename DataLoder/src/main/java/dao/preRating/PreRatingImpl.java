package dao.preRating;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import pojo.preRating;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/6/3 19:46
 * @描述
 */
public class PreRatingImpl implements PreRatingDao{
    MongoCollection<Document> collection = getProductCollection();
    @Override
    public List<preRating> selectAll() {
        List<preRating> list = new ArrayList<>();
        FindIterable<Document> documents = collection.find();
        for(Document d:documents){
            preRating preRating = new preRating();
            preRating.setProductId((Integer) d.get("productId"));
            preRating.setCount((Long)d.get("count"));
            list.add(preRating);
        }
        return list;
    }
}
