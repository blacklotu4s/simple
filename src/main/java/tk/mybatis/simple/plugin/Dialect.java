package tk.mybatis.simple.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * 数据库方言，针对不同数据库分别实现
 */
@SuppressWarnings("rawtypes")
public interface Dialect {
    /**
     * 跳过count和分页查询
     *
     * @param msId执行的MyBatis方法全名
     * @param parameterObject方法参数
     * @param rowBounds分页参数
     * @return true跳过，返回默认查询结果，false则执行分页查询
     */

    boolean skip(String msId, Object parameterObject, RowBounds rowBounds);

    /**
     * 执行分页前，返回true会进行count查询，返回false会继续下面的beforePage判断
     *
     * @param msId执行的MyBatis方法全名
     * @param parameterObject方法参数
     * @param rowBounds分页参数
     * @return true跳过，返回默认查询结果，false则执行分页查询
     */
    boolean beforeCount(String msId, Object parameterObject, RowBounds rowBounds);

    /**
     * 生成count查询sql
     *
     * @param boundSql绑定SQL对象
     * @param parameterObject方法参数
     * @param rowBounds分页参数
     * @param countKey count缓存key
     * @return
     *
     */
    String getCountSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey countKey);

    /**
     * 执行完count查询后
     *
     * @param count查询总数
     * @param parameterObject接口参数
     * @param rowBounds分页参数
     *
     */
    void afterCount(long count, Object parameterObject, RowBounds rowBounds);

    /**
     * 执行分页前，返回true会返回分页查询，返回false会返回默认查询结果
     *
     * @param msId执行的MyBatis方法全名
     * @param parameterObject方法参数
     * @param rowBounds分页参数
     * @return
     */
    boolean beforePage(String msId, Object parameterObject, RowBounds rowBounds);

    /**
     * 生成分页 查询sql
     *
     * @param boundSql绑定SQL对象
     * @param parameterObject方法参数
     * @param rowBounds分页参数
     * @param pageKey分页缓存key
     * @return
     */
    String getPageSql(BoundSql boundSql, Object parameterObject,
                      RowBounds rowBounds, CacheKey pageKey);


    /**
     * 分页查询后，处理分页结果，拦截器中直接return该方法的返回值
     *
     * @param pageList分页查询结果
     * @param parameterObject方法参数
     * @param rowBounds分页参数
     * @return
     */
    Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds);

    /**
     * 设置参数
     *
     * @param properties插件属性
     */
    void setProperties(Properties properties);
}
