package dao.productRecOff;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.apache.spark.sql.Dataset;

import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import pojo.ProductRecOff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 1:30
 * @描述
 */
public class ProductRecOffDaoImpl implements ProductRecOffDao {
    MongoCollection<Document> collection = getProductRecOffCollection();
    @Override
    public ProductRecOff getRecListList(int productId) {
        ProductRecOff productRecOff = null;
        String sql = "SELECT productId,recomList,timesamp FROM tmp WHERE productId="+productId+" ORDER BY timesamp DESC";
        productRecOff = getArray(sql).get(0);
        return productRecOff;
    }

    @Override
    public List<ProductRecOff> getRecListAll(int productId) {
        List<ProductRecOff> productRecOffs = new ArrayList<>();
        FindIterable<Document> documents = collection.find(new Document().append("productId",productId));
        for(Document d:documents){
            ProductRecOff productRecOff = new ProductRecOff();
            productRecOff.setProductId(productId);
            productRecOff.setTimesamp((long)d.get("timesamp"));
            productRecOff.setRecomList((List<Map<String, Object>>) ((List)d.get("recomList")).stream().map(x->{
                double rating = (double)((Document)x).get("rating");
                int userId = (int)((Document)x).get("userId");
                return  new ProductRecOff.UserRating(rating,userId);
            }).collect(Collectors.toList()));
            productRecOffs.add(productRecOff);
        }
        return productRecOffs;
    }

    @Override
    public ProductRecOff getRecListList(int productId, long time) {
        ProductRecOff productRecOff = null;
        String sql = "SELECT productId,recomList,timesamp FROM tmp WHERE productId="+productId+" ORDER BY ABS(timesamp-"+time+") LIMIT 1";
        productRecOff = getArray(sql).get(0);
        return productRecOff;
    }
}
