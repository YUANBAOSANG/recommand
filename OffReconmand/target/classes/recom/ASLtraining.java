package recom;

import dao.MarsMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import pojo.Mars;
import util.MybitsUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * @创建人 YDL
 * @创建时间 2020/5/2 2:43
 * @描述
 */
public class ASLtraining {
    private Dataset<Row> rowDataset;
    private int[] maxIter;
    private double[] regParam;
    private String user;
    private String item;
    private String reting;
    private List<Mars> marss = new ArrayList<>();
    private int[] rank;

    private List<Bind> parms = new ArrayList<>();
    private void saveMars(){
        try(SqlSession sqlSession = MybitsUtils.getSqlSession()){
            MarsMapper marsMapper = sqlSession.getMapper(MarsMapper.class);
            marsMapper.insertMarsList(marss);
            sqlSession.commit();
        }
    }
    private List<Bind> parmsCu = Collections.synchronizedList( new ArrayList<>());
    public ASLtraining(Dataset<Row> rowDataset, int[] maxIter, double[] regParam, String user, String item, String reting,int[] rank) {
        this.rowDataset = rowDataset;
        this.maxIter = maxIter;
        this.regParam = regParam;
        this.user = user;
        this.item = item;
        this.reting = reting;
        this.rank = rank;
    }
    private Bind getGoodBind(List<Bind> bs){
        if(bs.size()<2){
            return bs.size()==1?bs.get(0):null;
        }
        return bs.stream().max((o1,o2)->{
            double val = o2.mars-o1.mars;
            return val==0?0:(val<0?-1:1);
        }).get();
    }

    public void setParmsS(){
        for(int i=0;i<maxIter.length;i++){
            for(int j=0;j<regParam.length;j++) {
                for(int k=0;k<rank.length;k++) {
                    getGoodPart(maxIter[i], regParam[j],rank[k]);
                }
            }
        }
        saveMars();
    }

    public Dataset<Row>[] getGood(int recommandListSize) {

        setParmsS();
        Bind b = getGoodBind(parms);

        Dataset<Row> userRecs = b.model.recommendForAllUsers(recommandListSize);
        Dataset productRecs = b.model.recommendForAllItems(recommandListSize);

//        // Generate top 10 movie recommendations for a specified set of users
//        Dataset<Row> users = rowDataset.select(b.als.getUserCol()).distinct();
//        Dataset<Row> userSubsetRecs = b.model.recommendForUserSubset(users, recommandListSize);
//        // Generate top 10 user recommendations for a specified set of movies
//        Dataset<Row> products = rowDataset.select(b.model.getItemCol()).distinct();
//        Dataset<Row> productSubsetRecs = b.model.recommendForItemSubset(products, recommandListSize);
//        return new Dataset[]{userSubsetRecs,productSubsetRecs};
        return new Dataset[]{userRecs,productRecs};
    }

    private Bind getGoodPart(int maxI,double reg,int rank){
        Dataset<Row>[] splits = rowDataset.randomSplit(new double[]{0.8,0.2});
        Dataset<Row> training = splits[0];
        Dataset<Row> test = splits[1];
        training.cache();
        ALS als = new ALS().setMaxIter(maxI).setRegParam(reg).setRank(rank).setUserCol(user).
                setItemCol(item).setRatingCol(reting);
        ALSModel model = als.fit(training);
        model.setColdStartStrategy("drop");
        Dataset<Row> predictions = model.transform(test);
        RegressionEvaluator evaluator = new RegressionEvaluator().setMetricName("rmse").
                setLabelCol(reting).setPredictionCol("prediction");
        double rmse = evaluator.evaluate(predictions);
        System.out.println("Thread Name:"+Thread.currentThread().getName()+"rmse----------:"+rmse+"    maxI:"+maxI+"  reg"+reg+"rank"+rank);
        Bind b = new Bind(rmse,model,als,reg,maxI,rank);
        Mars mars = new Mars();
        mars.setMaxI(maxI);
        mars.setRank(rank);
        mars.setReg(reg);
        mars.setMars(rmse);
        marss.add(mars);
        parms.add(b);
        return b;
    }

//多线程版
    public void setParmsC(int threadPoolSize){
        ExecutorService exec = Executors.newFixedThreadPool(threadPoolSize);
        CountDownLatch latch = new CountDownLatch(maxIter.length*regParam.length*rank.length);
        for(int i=0;i<maxIter.length;i++){
            for(int j=0;j<regParam.length;j++) {
                for (int k = 0; k < rank.length; k++) {
                    int maxI = maxIter[i];
                    double regP = regParam[j];
                    int rank = this.rank[k];
                    exec.execute(() -> {
                        getGoodPartCu(maxI, regP, rank);
                        System.out.println("latch:" + latch.getCount());
                        latch.countDown();
                    });
                }
            }
        }
        try {
            System.out.println("main latch:"+latch.getCount());
            latch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdown();
        saveMars();
    }


    public Dataset<Row>[] getGooduCu(int recommandListSize,int threadPoolSize) {
        setParmsC(threadPoolSize);
        Bind b = getGoodBind(parmsCu);
        Dataset<Row> userRecs = b.model.recommendForAllUsers(recommandListSize);
        Dataset productRecs = b.model.recommendForAllItems(recommandListSize);
        return new Dataset[]{userRecs,productRecs};
    }

    private Bind getGoodPartCu(int maxI,double reg,int rank){
        Dataset<Row>[] splits = rowDataset.randomSplit(new double[]{0.8,0.2});
        Dataset<Row> training = splits[0];
        Dataset<Row> test = splits[1];
        training.cache();
        ALS als = new ALS().setMaxIter(maxI).setRegParam(reg).setRank(rank).setUserCol(user).
                setItemCol(item).setRatingCol(reting);
        ALSModel model = als.fit(training);
        model.setColdStartStrategy("drop");
        Dataset<Row> predictions = model.transform(test);
        RegressionEvaluator evaluator = new RegressionEvaluator().setMetricName("rmse").
                setLabelCol(reting).setPredictionCol("prediction");
        double rmse = evaluator.evaluate(predictions);
        System.out.println("Thread Name:"+Thread.currentThread().getName()+"rmse----------:"+rmse+"    maxI:"+maxI+"  reg"+reg+"rank"+rank);
        Bind b = new Bind(rmse,model,als,reg,maxI,rank);
        parmsCu.add(b);
        Mars mars = new Mars();
        mars.setMars(rmse);
        mars.setReg(reg);
        mars.setRank(rank);
        mars.setMaxI(maxI);
        synchronized (marss) {
            marss.add(mars);
        }
        return b;
    }


    private static class Bind{
        private double mars;
        private ALSModel model;
        private ALS als;
        private double reg;
        private int maxI;
        private int rank;

        public Bind(double mars, ALSModel model, ALS als, double reg, int maxI,int rank) {
            this.mars = mars;
            this.model = model;
            this.als = als;
            this.reg = reg;
            this.maxI = maxI;
            this.rank = rank;
        }
    }
}
