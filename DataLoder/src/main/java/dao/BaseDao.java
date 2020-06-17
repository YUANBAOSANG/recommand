package dao;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.bson.Document;
import org.codehaus.jackson.map.Deserializers;
import pojo.Product;
import pojo.Reting;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.WrappedArray;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @创建人 YDL
 * @创建时间 2020/4/28 19:24
 * @描述
 */
public enum BaseDao implements Closeable {
    Datader;

    public Map<String, String> getMap() {
        return map;
    }

    public SparkSession getSpark() {
        return spark;
    }

    private volatile  Map<String,String> map = readProperty();
    private volatile SparkSession spark = getSpark("MySpark",map.get("mongo_uri"),map.get("mongo_uri"));;
    private volatile MongoClient mongoClient = new MongoClient(new MongoClientURI(map.get("mongo_uri")));
    private volatile JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
    private void start(){
        if(spark==null) {
            spark = getSpark("MySpark",map.get("mongo_uri"),map.get("mongo_uri"));
        }
        if(mongoClient==null) {
            mongoClient = new MongoClient(new MongoClientURI(map.get("mongo_uri")));
        }
        if(jsc == null){
            jsc.close();
        }
    }

    /**
     * 功能描述:停止spark服务
     * @return : void
     * @author : YDL
     * @date : 2020/4/29 1:29
     **/
    private void sparkStop(){
        if(spark!=null) {
            spark.stop();
            spark = null;
        }
    }
    /**
     * 功能描述:关闭mongoClient
     * @return : void
     * @author : YDL
     * @date : 2020/4/29 1:29
     **/
    private void mongoClientClose(){
        if(mongoClient!=null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    private void javaSesionContextColse(){
        if(jsc!=null){
            jsc.close();
            jsc = null;
        }
    }
    /**
     * 功能描述:关闭mongoClient，停止spark服务
     * @return : void
     * @author : YDL
     * @date : 2020/4/29 1:29
     **/
    public void close(){
        mongoClientClose();
        javaSesionContextColse();
        sparkStop();
    }
    /**
     * 功能描述:获取Product集合的连接
     * @return : com.mongodb.client.MongoCollection<org.bson.Document>
     * @author : YDL
     * @date : 2020/4/29 1:30
     **/
    public MongoCollection<Document> getProductConllection()  {
       return getConllection(map.get("mongodb_name"),map.get("conllection_paoduct"));

    }
    public  MongoCollection<Document> getUserRecConllection(){
        return getConllection(map.get("mongodb_name"),map.get("conllection_userrecoff"));
    }

    public  MongoCollection<Document> getProductRecCollection() {
        return getConllection(map.get("mongodb_name"),map.get("conllection_productrecoff"));
    }

    private MongoCollection<Document> getConllection(String dbName,String collectionName){
        if(mongoClient==null){
            start();
        }
        return mongoClient.getDatabase(dbName).
                getCollection(collectionName);
    }
    /**
     * 功能描述:获取Rating集合的连接
     * @return : com.mongodb.client.MongoCollection<org.bson.Document>
     * @author : YDL
     * @date : 2020/4/29 1:30
     **/
    public MongoCollection<Document> getRetingConllection(){
        return getConllection(map.get("mongodb_name"),map.get("conllection_rating"));
    }
    /**
     * 功能描述:读取
     * @return : java.util.Map<java.lang.String,java.lang.String>
     * @author : YDL
     * @date : 2020/4/29 1:31
     **/
    private Map<String,String> readProperty(){
        Map<String,String> map = new HashMap<>();
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("mongodb.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            for(Object key:properties.keySet()){
                map.put((String)key,(String)properties.get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 功能描述:获取Spark
     * @return : org.apache.spark.sql.SparkSession
     * @author : YDL
     * @date : 2020/4/29 1:32
     **/
    private SparkSession getSpark(String name,String inputUri,String outputUri) {
        SparkSession spark = SparkSession.builder().master("local")
                .appName(name)
                .config("spark.mongodb.input.uri", inputUri)
                .config("spark.mongodb.output.uri", outputUri)
                .getOrCreate();
        return spark;
    }
//3982
// Fuhlen 富勒 M8眩光舞者时尚节能无线鼠标(草绿)(眩光.悦动.时尚炫舞鼠标 12个月免换电池 高精度光学寻迹引擎 超细微接收器10米传输距离)
// 1057,439,736
// B009EJN4T2
// https://images-cn-4.ssl-images-amazon.com/images/I/31QPvUDNavL._SY300_QL70_.jpg
// 外设产品|鼠标|电脑/办公
// 富勒|鼠标|电子产品|好用|外观漂亮
    /**
     * 功能描述: 将Rating.csv product.csv写入MongoDb
     * @param productDF 1
     * @param ratingDF 2
     * @return : void
     * @author : YDL
     * @date : 2020/4/29 1:27
     **/
    private void storeDataToDB(Dataset<Row> productDF,Dataset<Row> ratingDF){
        store(productDF,map.get("conllection_paoduct"),new String[]{"productId"});
        store(ratingDF,map.get("conllection_rating"),new String[]{"productId","userId"});
    }

    public void store(Dataset<Row> df,String collectionName,String[] indexs){
        df.show();
        if(collectionName==null){
            throw new NullPointerException("collectionName为空");
        }else {
            MongoCollection<Document> collection = getConllection(map.get("mongodb_name"),collectionName);
            collection.drop();
            df.write().option("uri", BaseDao.Datader.map.get("mongo_uri")).
                    option("collection", collectionName).
                    mode("overwrite").format("com.mongodb.spark.sql")
                    .save();
            for(String index:indexs){
                collection.createIndex(new Document(index,1));
            }
        }

    }
    /**
     * 功能描述:获取Product的Dataset包装对象，以写入mongodb
     * @return : org.apache.spark.sql.Dataset<org.apache.spark.sql.Row>
     * @author : YDL
     * @date : 2020/4/29 1:26
     **/
    private Dataset<Row> getProductDF(){
        JavaRDD<Product>  sparkDocuments = spark.read().textFile(map.get("path_product")).javaRDD().map(
                (Function<String, Product>) x -> {
                    String[] ss = x.split("\\^");
                    return
                            new Product.ProductBuider().productId(Integer.parseInt(ss[0])).
                                    name(ss[1]).imgUrl(ss[4]).categories(ss[5]).tags(ss[6]).buide();
                });
        Dataset<Row> df = spark.createDataFrame(sparkDocuments,Product.class);
        return df;
    }
    /**
     * 功能描述: 获取Rating的Dataset包装对象，以写入mongodb
     * @return : org.apache.spark.sql.Dataset<org.apache.spark.sql.Row>
     * @author : YDL
     * @date : 2020/4/29 1:25
     **/
    private Dataset<Row> getRatingDF(){

        JavaRDD<Reting>  sparkDocuments = spark.read().textFile(map.get("path_rating")).javaRDD().map(
            (Function<String, Reting>) x -> {
                String[] ss = x.split(",");
                return
                        new Reting(Integer.parseInt(ss[0]), Integer.parseInt(ss[1])
                                ,Double.parseDouble(ss[2]),Integer.parseInt(ss[3]));
            });

        Dataset<Row> df = spark.createDataFrame(sparkDocuments,Reting.class);
        return df;
    }
    private String readJsonFile(String path){
        StringBuffer sb = new StringBuffer();
        File file = new File(path);
        try(FileReader fr = new FileReader(file)) {
            int ch = 0;
            while((ch = fr.read())!=-1 ) {
                sb.append((char)ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(sb);
    }

    public void saveJson(String json,String collectionName){
        if(collectionName==null){
            throw new NullPointerException("collectionName为空");
        }else {
            MongoCollection<Document> collection = getConllection(map.get("mongodb_name"), collectionName);
            Document doct = Document.parse(json);
            collection.insertOne(doct);
        }
    }

    public void saveJsons(List<String> jsons,String collectionName){
        for(String json:jsons){
            saveJson(json,collectionName);
        }
    }
    public Dataset<Row> getDFFromDB(String conllectionName){
        /*Start Example: Read data from MongoDB************************/
        // Create a custom ReadConfig
        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put("collection", conllectionName);
        readOverrides.put("readPreference.name", "secondaryPreferred");
        ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);
        // Load data using the custom ReadConfig
        JavaMongoRDD<Document> customRdd = MongoSpark.load(jsc, readConfig);
        Dataset<Row> df = customRdd.toDF();
        return df;
    }


    private void createStaicHotProduct(String tempName,String collectionName,String sql,String[] indexs){
        Dataset<Row> dff = getDFFromDB(tempName);
        dff.createOrReplaceTempView("temp");
        Dataset<Row> hhpDF = spark.sql(sql);
        store(hhpDF,collectionName,indexs);
    }

    public void createHistoryHotProduct(){
        String sql = "select productId, count(productId) " +
                "as count from temp group by " +
                "productId order by count desc";
        String tempName = map.get("conllection_rating");
        String collectionName = map.get("conllection_prerating");
        String[] index = new String[]{"productId"};
        createStaicHotProduct(tempName,collectionName,sql,index);
    }

    public void createAvgHotProduct(){
        String tempName = map.get("conllection_rating");
        String sql = "select productId, avg(rating) as avg from temp " +
                        "group by productId " +
                        "order by avg desc";
        String collectionName = map.get("conllection_avgrating");
        String[] index = new String[]{"productId"};
        createStaicHotProduct(tempName,collectionName,sql,index);
    }

    public void createTimeProduct(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        spark.udf().register("changedate", (UDF1<Integer, Integer>) x->
                   Integer.valueOf(sdf.format(new Date(x * 1000L))),
                DataTypes.IntegerType);
        String sql = "select productId, rating, changedate(timestamp) as yearmonth from temp";
        String[] indexs = new String[]{"productId"};
        String collectionName = map.get("conllection_timerating");
        String tempName = map.get("conllection_rating");
        createStaicHotProduct(tempName,collectionName,sql,indexs);
    }

    public void createTimeHotProduct(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        spark.udf().register("changedate", (UDF1<Integer, Integer>) x->
                        Integer.valueOf(sdf.format(new Date(x * 1000L))),
                DataTypes.IntegerType);
        String sql = "select productId,yearmonth, count(yearmonth) as count from temp " +
                "group by productId,yearmonth order by yearmonth DESC ,count DESC";
        String[] indexs = new String[]{"productId"};
        String collectionName = map.get("conllection_timeHot");
        String tempName = map.get("conllection_timerating");
        createStaicHotProduct(tempName,collectionName,sql,indexs);
    }
    public static void main(final String[] args) {
        try(BaseDao baseDao = BaseDao.Datader){
             Dataset<Row> data =  baseDao.getDFFromDB(baseDao.map.get("conllection_avgrating"));
             data.createOrReplaceTempView("temp");
             Object object = data.getRows(0,0);
             Object[] o = ((ArraySeq)object).array();
             String[] o2= (String[]) ((WrappedArray) o[0]).array();
             Object[] o3 = ((ArrayBuffer)o[1]).array();
            for(int i=1;i<o2.length;i++){

            }
            System.out.println();
        }
    }

    public MongoCollection<Document> getAvgConllection() {
        return getConllection(map.get("mongodb_name"),map.get("conllection_avgrating"));
    }

    public MongoCollection<Document> getPreConllection() {
        return getConllection(map.get("mongodb_name"),map.get("conllection_prerating"));
    }
}
