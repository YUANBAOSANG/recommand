import dao.MarsMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pojo.Mars;
import util.MybitsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 21:55
 * @描述
 */
public class testMarsMapper {
    @Test
    public void testInsret(){
        try(SqlSession sqlSession = MybitsUtils.getSqlSession()) {
            List<Mars> mars = new ArrayList<>();
            Mars mars1 = new Mars();
            mars1.setMaxI(2);
            mars1.setRank(2);
            mars1.setReg(2.0);
            mars1.setMars(2.0);
            mars.add(mars1);
            MarsMapper marsMapper = sqlSession.getMapper(MarsMapper.class);
            marsMapper.insertMarsList(mars);
            sqlSession.commit();
        }
    }
}
