
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;


/**
 * @创建人 YDL
 * @创建时间 2020/5/3 17:07
 * @描述
 */
public class test {
//    try(BaseDao baseDao = BaseDao.Datader) {
//        //Dataset<Row> pDF = baseDao.getProductDF();
//        //Seq<Seq<String>> sq = pDF.getRows(5,1000);
//        //Dataset<Row> rDF = baseDao.getRatingDF();
//        //baseDao.storeDataToDB(pDF,rDF);
//        //baseDao.createHistoryHotProduct();
//        //baseDao.createAvgHotProduct();
//        //baseDao.createTimeHotProduct();
//        Dataset<Row> retingDs = baseDao.getDFFromDB("Rating");
////             Dataset<Row>[] splits = retingDs.randomSplit(new double[]{0.8,0.2});
////             Dataset<Row> training = splits[0];
////             Dataset<Row> test = splits[1];
////             ALS als = new ALS().setMaxIter(5).setRegParam(0.01).setUserCol("userId").
////                     setItemCol("productId").setRatingCol("rating");
////            ALSModel model = als.fit(training);
////
////            model.setColdStartStrategy("drop");
////            Dataset<Row> predictions = model.transform(test);
////
////            RegressionEvaluator evaluator = new RegressionEvaluator().setMetricName("rmse").
////                    setLabelCol("rating").setPredictionCol("prediction");
////
////            double rmse = evaluator.evaluate(predictions);
////            System.out.println("rmse----------:"+rmse);
////
////            Dataset<Row> userRecs = model.recommendForAllUsers(10);
////            Dataset productRecs = model.recommendForAllItems(10);
////
////            // Generate top 10 movie recommendations for a specified set of users
////            Dataset<Row> users = retingDs.select(als.getUserCol()).distinct();
////            Dataset<Row> userSubsetRecs = model.recommendForUserSubset(users, 1);
////            // Generate top 10 user recommendations for a specified set of movies
////            Dataset<Row> products = retingDs.select(als.getItemCol()).distinct();
////            Dataset<Row> productSubsetRecs = model.recommendForItemSubset(products, 1);
////
////            userSubsetRecs.show();
////            productSubsetRecs.show();
////            Seq<Seq<String>> s = userSubsetRecs.getRows(5,5);
////            baseDao.store(userSubsetRecs,"userRecs",null);
////            baseDao.store(productSubsetRecs,"productRecs",null);
//        int[] maxI = new int[]{5, 10, 20, 30};
//        double[] reg = new double[]{1,0.1, 0.01};
//        ASLtraining asLtraining = new ASLtraining(retingDs, maxI, reg, "userId", "productId", "rating");
//        Dataset<Row>[] datasets = asLtraining.
//                getGood(10);
//        datasets[0].show();
//        datasets[1].show();

}
