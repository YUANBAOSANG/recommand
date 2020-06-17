package dao.userRecOff;

import pojo.UserRecOff;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 15:39
 * @描述
 */
public class UserRecDaoImpl implements UserRecDao {
    @Override
    public UserRecOff getUserRec(int userId) {
        String sql = "SELECT userId,recomList,timesamp FROM tamp Where userId = " +
                userId+" ORDER BY timesamp DESC";
        return getArray(sql).get(0);
    }

    @Override
    public UserRecOff getUserRec(int userId, long time) {
        String sql = "SELECT userId,recomList,timesamp FROM tamp Where userId = " +
                userId+" ORDER BY ABS(timesamp-"+time+") LIMIT 1";
        return getArray(sql).get(0);
    }

    @Override
    public List<UserRecOff> getUserRecAll(int userId) {
        String sql = "SELECT userId,recomList,timesamp FROM tamp Where userId = " +
                userId;
        return getArray(sql);
    }
}
