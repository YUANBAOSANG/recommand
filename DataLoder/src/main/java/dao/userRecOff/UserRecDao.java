package dao.userRecOff;

import com.mongodb.client.MongoCollection;
import dao.BaseDao;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.bson.Document;
import pojo.UserRecOff;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 15:32
 * @描述
 */
public interface UserRecDao {
    UserRecOff getUserRec(int UserId);
    UserRecOff getUserRec(int UserId,long time);
    List<UserRecOff> getUserRecAll(int UserId);
    default List<UserRecOff> getArray(String sql){
        BaseDao baseDao = BaseDao.Datader;
        Dataset<Row> dff = baseDao.getDFFromDB(BaseDao.Datader.getMap().get("conllection_userrecoff"));
        dff.createOrReplaceTempView("tamp");
        Dataset<UserRecOff> df = BaseDao.Datader.getSpark().sql(sql).as(Encoders.bean(UserRecOff.class));
        df.show();
        List<UserRecOff> userRecOffs =df.collectAsList();
        return  userRecOffs;
    }
    default void close(){
        BaseDao.Datader.close();
    }
}
