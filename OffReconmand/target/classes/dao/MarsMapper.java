package dao;

import org.apache.ibatis.type.Alias;
import pojo.Mars;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/6 21:17
 * @描述
 */
@Alias("MarsMapper")
public interface MarsMapper {
    void insertMarsList(List<Mars> marsList);
    List<Mars> getMarsALL();
    List<Mars> getMarsMin();
}
