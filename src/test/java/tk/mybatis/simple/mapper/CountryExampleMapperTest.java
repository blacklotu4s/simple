package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.Country;
import tk.mybatis.simple.model.CountryExample;

import java.util.List;

public class CountryExampleMapperTest extends BaseMapperTest {
    @Test
    public void testExample() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取CountryMapper接口
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
            // 创建Example对象
            CountryExample example = new CountryExample();
            // 设置排序规则
            example.setOrderByClause("id desc, countryname asc");
            // 设置是否distinct去重
            example.setDistinct(true);
            // 创建条件
            CountryExample.Criteria criteria = example.createCriteria();
//            // id >= 1
//            criteria.andIndepyearGreaterThanOrEqualTo((short)1);
//            // id < 4
//            criteria.andi((short)4);
            // countrycode like '%U%'
            // 最容易出错的地方，注意like必须自己写上通配符的位置
            criteria.andCodeLike("%U%");
            // or的情况
            CountryExample.Criteria or = example.or();
            // countryname=中国
            or.andNameEqualTo("中国");
            // 执行查询
            List<Country> countryList = countryMapper.selectByExample(example);
            printCountryList(countryList);
        }
        finally {
            // 不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    public <T extends Country> void printCountryList(List<T> countryList) {
        for (Country country : countryList) {
            System.out.println(country.getName() + " " + country.getCode());
        }
    }
}
