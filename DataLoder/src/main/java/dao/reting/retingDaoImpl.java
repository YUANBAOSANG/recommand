package dao.reting;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import pojo.Reting;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/4/29 3:29
 * @描述
 */
public class retingDaoImpl implements retingDao {
    MongoCollection<Document> collection = getRetingCollection();
    @Override
    public void insert(Reting reting) {
        Document document = new Document();
        document.append((String) reting.userId()[0],(Integer)reting.userId()[1]);
        document.append((String) reting.productId()[0],reting.productId()[1]);
        document.append((String) reting.timestamp()[0],reting.timestamp()[1]);
        document.append((String)reting.rating()[0],reting.rating()[1]);
        collection.insertOne(document);
    }

    @Override
    public void delectByUserId(int userId) {
        Document idD = new Document();
        idD.append("userId",userId);
        collection.deleteOne(idD);
    }

    @Override
    public void delectByProductId(int productId) {
        Document idD = new Document();
        idD.append("productId",productId);
        collection.deleteOne(idD);
    }

    @Override
    public List<Reting> selectByUserId(int userId) {
        List<Reting> retings = new ArrayList<>();
        FindIterable<Document> documents = collection.find(new Document().append("userId",userId));
        for(Document document:documents){
            retings.add(new Reting(document.getInteger("userId"),document.getInteger("productId"),
                    document.getDouble("rating"),document.getInteger("timestamp")));
        }
        return retings;
    }

    @Override
    public List<Reting> selectByProductId(int productId) {
        List<Reting> retings = new ArrayList<>();
        FindIterable<Document> documents = collection.find(new Document().append("productId",productId));
        for(Document document:documents){
            retings.add(new Reting(document.getInteger("userId"),document.getInteger("productId"),
                    document.getDouble("rating"),document.getInteger("timestamp")));
        }
        return retings;
    }


}
