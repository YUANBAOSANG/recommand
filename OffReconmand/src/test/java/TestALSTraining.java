import DealData.FloatToDouble;
import dao.BaseDao;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import recom.ASLtraining;

import java.util.List;


/**
 * @创建人 YDL
 * @创建时间 2020/5/1 16:32
 * @描述
 */
//-Xmx4000m -Xms4000m -Xss5m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParallelOldGC -XX:+UseAdaptiveSizePolicy -XX:GCTimeRatio=19
public class TestALSTraining {
    public static void main(String[] args){
        try(BaseDao baseDao = BaseDao.Datader) {
            long time = System.currentTimeMillis();
            Dataset<Row> retingDs = baseDao.getDFFromDB("Rating");
            int[] maxI = new int[]{113};
            double[] reg = new double[]{0.29};
            int[] ranks = new int[]{307};
            ASLtraining asLtraining = new ASLtraining(retingDs, maxI, reg, "userId", "productId", "rating",ranks);
            asLtraining.setParmsS();
            time =  System.currentTimeMillis()-time;
            System.out.println("总共耗时 time:"+time/1000+"妙");
        }


    }


}
