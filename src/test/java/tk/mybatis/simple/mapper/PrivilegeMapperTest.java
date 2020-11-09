package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;

public class PrivilegeMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById() {
        SqlSession sqlSession = getSqlSession();
        try {
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            SysPrivilege privilege = privilegeMapper.selectById(1L);
            // privilege不为空
            Assert.assertNotNull(privilege);
            // privilegeName = 用户管理
            Assert.assertEquals("用户管理", privilege.getPrivilegeName());
        }
        finally {
            sqlSession.close();
        }
    }
}
