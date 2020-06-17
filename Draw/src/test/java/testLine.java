import dao.avgRating.avgRatingDao;
import dao.avgRating.avgRatingImpl;
import dao.preRating.PreRatingDao;
import dao.preRating.PreRatingImpl;
import dao.productRecOff.ProductRecOffDao;
import dao.productRecOff.ProductRecOffDaoImpl;
import dao.reting.retingDao;
import dao.reting.retingDaoImpl;
import dao.userRecOff.UserRecDao;
import dao.userRecOff.UserRecDaoImpl;
import draw.LineChart;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;
import pojo.*;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @创建人 YDL
 * @创建时间 2020/5/5 2:13
 * @描述
 */
public class testLine {
    public static void main(String[] args) {
//
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        avgRatingDao avgRating = new avgRatingImpl();
//        List<pojo.avgRating> list = avgRating.selectAll();
//        int count = 1;
//        for(pojo.avgRating avg:list){
//            if(count==1){
//                dataset.addValue( Math.random()*200,String.valueOf(avg.getProductId()),String.valueOf(avg.getProductId()));
//                count++;
//            }else {
//                System.out.println("test:" + avg.getProductId() + " " + avg.getAvg());
//                dataset.addValue(Math.random() * 500, String.valueOf(avg.getProductId()), String.valueOf(avg.getProductId()));
//            }
//        }
//        JFrame frame = new JFrame("2011年的历史交互次数统计直方图");
//        frame.add(LineChart.createPort("2009年的历史交互次数统计直方图","产品ID","交互次数",dataset));
//        frame.setBounds(50,50,800,600);
//        frame.setVisible(true);
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        PreRatingDao avgRating = new PreRatingImpl();
//        List<preRating> list = avgRating.selectAll();
//        for(preRating avg:list){
//            System.out.println("test:"+avg.getProductId()+" "+avg.getCount());
//            dataset.addValue(avg.getCount(),String.valueOf(avg.getProductId()),String.valueOf(avg.getProductId()));
//        }
//        JFrame frame = new JFrame("产品历史交互数统计柱状图");
//        frame.add(LineChart.createPort("历史交互数","产品ID","交互映射评分",dataset));
//        frame.setBounds(50,50,800,600);
//        frame.setVisible(true);
        testR();
    }
    @Test
    public void test(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        PreRatingDao avgRating = new PreRatingImpl();
        List<preRating> list = avgRating.selectAll();
        for(preRating avg:list){

            dataset.addValue(avg.getCount(),String.valueOf(avg.getProductId()),String.valueOf(avg.getProductId()));
        }
        JFrame frame = new JFrame("产品平均分柱状图");
        frame.add(LineChart.createPort("平均产品评分","产品ID","交互映射评分",dataset));
        frame.setBounds(50,50,800,600);
        frame.setVisible(true);

    }
    @Test
    public static void testL(){
        UserRecDao userRecDao = new UserRecDaoImpl();
        List<UserRecOff> userRec = userRecDao.getUserRecAll(78);
        int count = 1;
        for(UserRecOff userRecOff:userRec){
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            List<UserRecOff.ProductRating> li = userRecOff.getRecomList();
            for(UserRecOff.ProductRating pro:li){
                dataset.addValue(Math.min(5.0,pro.getRating()),String.valueOf(pro.getProductId()),String.valueOf(pro.getProductId()));
            }

            JFrame frame = new JFrame("产品平均分柱状图");
            frame.add(LineChart.createPort("78号用户的产品推荐列表"+count++,"产品ID","预测值",dataset));
            frame.setBounds(50,50,800,600);
            frame.setVisible(true);
        }
        userRecDao.close();
    }
    public static void testU(){
        retingDao retingDao = new retingDaoImpl();
        List<Reting> list = retingDao.selectByUserId(78);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int count = 1;
        for(Reting userRecOff:list){
            dataset.addValue(Math.min(5.0,userRecOff.getRating()),String.valueOf(userRecOff.getProductId()),String.valueOf(userRecOff.getProductId()));


        }
        JFrame frame = new JFrame(" ");
        frame.add(LineChart.createPort("78号用户的原始交互列表","产品ID","交互映射值",dataset));
        frame.setBounds(50,50,800,600);
        frame.setVisible(true);
    }
    public static void testR(){
        ProductRecOffDao userRecDao = new ProductRecOffDaoImpl();
        List<ProductRecOff> userRec = userRecDao.getRecListAll(300265);
        int count = 1;
        for(ProductRecOff userRecOff:userRec){
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            List<ProductRecOff.UserRating> li = userRecOff.getRecomList();
            for(ProductRecOff.UserRating pro:li){
                dataset.addValue(Math.min(5.0,pro.getRating()),String.valueOf(pro.getUserId()),String.valueOf(pro.getUserId()));
            }

            JFrame frame = new JFrame("产品平均分柱状图");
            frame.add(LineChart.createPort("300265号产品的用户推荐列表"+count++,"用户ID","预测值",dataset));
            frame.setBounds(50,50,800,600);
            frame.setVisible(true);
        }
        userRecDao.close();
    }

    public static void testC(){
        retingDao retingDao = new retingDaoImpl();
        List<Reting> list = retingDao.selectByUserId(78);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int count = 1;

        for(Reting userRecOff:list){
            dataset.addValue(Math.min(5.0,userRecOff.getRating()),String.valueOf(userRecOff.getProductId()),String.valueOf(userRecOff.getProductId()));
        }
        JFrame frame = new JFrame(" ");
        frame.add(LineChart.createPort("78号用户的原始交互列表","产品ID","交互映射值",dataset));
        frame.setBounds(50,50,800,600);
        frame.setVisible(true);
    }
}
