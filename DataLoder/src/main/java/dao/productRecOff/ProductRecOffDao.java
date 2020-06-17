package dao.productRecOff;

import com.mongodb.client.MongoCollection;
import dao.BaseDao;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import pojo.ProductRecOff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 1:25
 * @描述
 */
public interface ProductRecOffDao {
    ProductRecOff getRecListList(int productId);
    List<ProductRecOff> getRecListAll(int productId);
    ProductRecOff getRecListList(int productId,long time);
    default MongoCollection<Document> getProductRecOffCollection(){
        return BaseDao.Datader.getProductRecCollection();
    }
    default List<ProductRecOff> getArray(String sql){
            BaseDao baseDao = BaseDao.Datader;
            Dataset<Row> dff = baseDao.getDFFromDB(BaseDao.Datader.getMap().get("conllection_productrecoff"));
            dff.createOrReplaceTempView("tmp");
            Dataset<ProductRecOff> df = BaseDao.Datader.getSpark().sql(sql).as(Encoders.bean(ProductRecOff.class));
            df.show();
            List<ProductRecOff> productRecOffs =df.collectAsList();
            return  productRecOffs;
    }
    default void close(){
        BaseDao.Datader.close();
    }
}
