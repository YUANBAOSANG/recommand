import dao.userRecOff.UserRecDao;
import dao.userRecOff.UserRecDaoImpl;
import org.junit.Test;
import pojo.UserRecOff;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 15:47
 * @描述
 */
/*UserRecOff getUserRec(int UserId);
    UserRecOff getUserRec(int UserId,long time);
    List<UserRecOff> getUserRecAll(int UserId);
*/
public class UserRecDaoTest {
    UserRecDao userRecDao = new UserRecDaoImpl();
    @Test
    public void testGetUserRec(){
        UserRecOff userRec = userRecDao.getUserRec(78);
        System.out.print("");
        userRecDao.close();
    }

    @Test
    public void testGetUserRec0(){
        UserRecOff userRec = userRecDao.getUserRec(78,0);
        System.out.print("");
        userRecDao.close();
    }

    @Test
    public void testGetUserRecAll(){
        List<UserRecOff> userRec = userRecDao.getUserRecAll(78);
        System.out.print("");
        userRecDao.close();
    }
}
