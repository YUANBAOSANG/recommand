package DealData;

import dao.BaseDao;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.mortbay.util.ajax.JSON;
import recom.ASLtraining;
import scala.Int;
import scala.Some;
import scala.collection.JavaConverters;
import scala.collection.immutable.Map;
import scala.collection.mutable.WrappedArray;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;

/**
 * @创建人 YDL
 * @创建时间 2020/5/5 3:30
 * @描述
 */
public class FloatToDouble {
    private FloatToDouble(){}
    private static FileWriter getFileW(String path) throws IOException {
        FileWriter fileWriter =new FileWriter(path,true);
        return fileWriter;
    }
    public static List<String> getJson(Dataset<Row> df,String type,String sonType){
        return getJson(df,type,sonType,null,null);
    }
    public static List<String> getAndSaveJson(Dataset<Row> df,String type,String sonType,String filename){
        return getJson(df,type,sonType,filename,null);
    }

    public static List<String> getAndSaveJson(Dataset<Row> df,String type,String sonType,String filename,String path){
        return getJson(df,type,sonType,filename,path);
    }
    private static List<String>  getJson(Dataset<Row> df,String type,String sonType,String filename,String path){
        List l = df.collectAsList();
        List<String> map = new ArrayList<>();
        for(Object o:l){
            java.util.Map<String, java.util.Map> mapt = dealMap(o,type,sonType);
            String json = JSON.toString(mapt);
            System.out.println(json);

            map.add(json);
        }
        if(filename!=null) {
            path = path==null?"E:\\Javatest\\Recommand\\OffReconmand\\src\\main\\resources\\":path+"\\";
            for (String json : map) {
                try {
                    saveJson(json,  path+ filename + ".json");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    private static java.util.Map<String, java.util.Map> dealMap(Object o, String type, String sonType){
        GenericRowWithSchema b = (GenericRowWithSchema)o;
        List<String> s = Arrays.asList("recommendations",type);
        Map p = b.getValuesMap(JavaConverters.asScalaIteratorConverter(s.listIterator()).asScala().toSeq());
        int id = getSomeX(type,p);
        WrappedArray.ofRef pp = getSomeX("recommendations",p);
        Object[] pps = pp.array();
        Object[] ppps = Arrays.stream(pps).map(x->{
            List<String> ss = Arrays.asList(sonType,"rating");
            return ((GenericRowWithSchema)x).getValuesMap(JavaConverters.asScalaIteratorConverter(ss.listIterator()).asScala().toSeq());
        }).toArray();
        Object[] maps =  Arrays.stream(ppps).map(x->{
            double rating = new BigDecimal(String.valueOf(((Some)((Map)x).get("rating")).x())).doubleValue();
            int productId = (int) ((Some)((Map)x).get(sonType)).x();
            java.util.Map<String,Object> mapps = new HashMap<>();
            mapps.put("rating",rating);
            mapps.put(sonType,productId);
            return mapps;
        }).toArray();
        java.util.Map map_0 = new HashMap();
        map_0.put(type,id);
        map_0.put("recomList",maps);
        map_0.put("timesamp",System.currentTimeMillis());
       return map_0;
    }
    private static void saveJson(String json,String path) throws FileNotFoundException {
        System.out.println("Json   +"+json);
        try(FileWriter fileWriter =getFileW(path)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static <T> T getSomeX(String key,Map map){
           Some some  = (Some)map.get(key);
           return (T) some.x();
    }
    public static void main(String[] args){
        try(BaseDao baseDao = BaseDao.Datader) {
            Dataset<Row> retingDs = baseDao.getDFFromDB("Rating");
            int[] maxI = new int[]{113};
            double[] reg = new double[]{0.29};
            int[] rank = new int[]{307};
            ASLtraining asLtraining = new ASLtraining(retingDs, maxI, reg, "userId", "productId", "rating",rank);
            Dataset<Row>[] datasets = asLtraining.getGood(10);
            Dataset<Row> userRec = datasets[0];
            Dataset<Row> itemRec =datasets[1];
            List<String> user = getAndSaveJson(userRec,"userId","productId","UserRecOff");
            List<String >product = getAndSaveJson(itemRec,"productId","userId","ProductRecOff");
            baseDao.saveJsons(user,baseDao.getMap().get("conllection_userrecoff"));
            baseDao.saveJsons(product,baseDao.getMap().get("conllection_productrecoff"));
        }
    }
}
