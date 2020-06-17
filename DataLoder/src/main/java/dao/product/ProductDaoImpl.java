package dao.product;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import pojo.Product;


/**
 * @创建人 YDL
 * @创建时间 2020/4/29 1:37
 * @描述
 */
public class ProductDaoImpl implements ProductDao {
    MongoCollection<Document> collection = getProductCollection();
    /**
     * 功能描述:
     * @param product 1
     * @return : void
     * @author : YDL
     * @date : 2020/4/29 2:25
     **/
    @Override
    public void insert(Product product) {
        Document document = new Document();
        document.append(product.categories()[0],product.categories()[1]);
        document.append(product.imgUrl()[0],product.imgUrl()[1]);
        document.append(product.name()[0],product.name()[1]);
        document.append((String)product.productId()[0],(Integer)product.productId()[1]);
        document.append(product.tags()[0],product.tags()[1]);
        collection.insertOne(document);
    }

    @Override
    public void delect(int productId) {
        Document idD = new Document();
        idD.append("productId",productId);
        collection.deleteOne(idD);
    }

    @Override
    public void update(Product product) {
        Document document = new Document();
        document.append(product.categories()[0],product.categories()[1]);
        document.append(product.imgUrl()[0],product.imgUrl()[1]);
        document.append(product.name()[0],product.name()[1]);
        document.append((String)product.productId()[0],(Integer)product.productId()[1]);
        document.append(product.tags()[0],product.tags()[1]);
        Document pre = new Document();
        pre.append((String)product.productId()[0],(Integer)product.productId()[1]);
        collection.replaceOne(pre,document);
        //collection.updateOne(pre,new Document().append("$set",pre));
    }

    @Override
    public Product selectById(int productId) {
        Product product = null;
        FindIterable<Document> documents = collection.find(new Document().append("productId",productId));
        for(Document document:documents){
             product = new Product.ProductBuider().productId(document.get("productId",Integer.class)).
                     name(document.get("name",String.class)).
                     imgUrl(document.get("imgUrl",String.class)).
                     tags(document.get("tags",String.class)).categories(document.get("categories",String.class)).buide();
        }
        return product;
    }

}
