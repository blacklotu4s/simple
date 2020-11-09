package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import javax.management.relation.Role;

public class CacheTest extends BaseMapperTest {
    @Test
    public void testL1Cache() {
        SqlSession sqlSession = getSqlSession();
        SysUser user1 = null;
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            user1 = userMapper.selectById(1L);
            user1.setUserName("New Name");
            SysUser user2 = userMapper.selectById(1L);
            // 虽然没有更新数据库，但是这个用户名和user1重新赋值的名字相同
            Assert.assertEquals("New Name", user2.getUserName());
            // 无论如何，user2和user1完全就是同一个实例
            Assert.assertEquals(user1, user2);
        }
        finally {
            sqlSession.close();
        }
        System.out.println("开启新的sqlSession");
        sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user2 = userMapper.selectById(1L);
            // 第二个session获取的用户名仍然是admin
            Assert.assertNotEquals("New Name", user2.getUserName());
            // 这里的user2和前一个session查询的结果是两个不同的实例
            Assert.assertNotEquals(user1, user2);
            //执行删除操作
            userMapper.deleteById(2L);
            // 获取user3
            SysUser user3 = userMapper.selectById(1L);
            // 这里的user2和user3是两个不同的实例
            Assert.assertNotEquals(user2, user3);
        }
        finally {
            sqlSession.close();
        }
    }

    @Test
    public void testL2Cache() {
        SqlSession sqlSession = getSqlSession();
        SysRole role1 = null;
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            role1 = roleMapper.selectById(1L);
            role1.setRoleName("New Name");
            SysRole role2 = roleMapper.selectById(1L);
            // 虽然没有更新数据库，但是这个用户名和role1重新赋值的名字相同
            Assert.assertEquals("New Name", role2.getRoleName());
            Assert.assertEquals(role1, role2);

        }
        finally {
            sqlSession.close();
        }
        System.out.println("开启新的sqlSession");
        sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role2 = roleMapper.selectById(1L);
            Assert.assertEquals("New Name", role2.getRoleName());
            Assert.assertNotEquals(role1, role2);
            SysRole role3 = roleMapper.selectById(1L);
            Assert.assertNotEquals(role2, role3);
        }
        finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDirtyData() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            Assert.assertEquals("普通用户", user.getRole().getRoleName());
            System.out.println("角色名： " + user.getRole().getRoleName());
        }
        finally {
            sqlSession.close();
        }
        System.out.println("开启第二个sqlSession");
        // 开始另一个新的session
        sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(2L);
            role.setRoleName("脏数据");
            roleMapper.updateById(role);
            // 提交修改
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }
        System.out.println("开启第三个sqlSession");
        sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            SysRole role = roleMapper.selectById(2L);
            Assert.assertEquals("普通用户", user.getRole().getRoleName());
            Assert.assertEquals("脏数据", role.getRoleName());
            System.out.println("角色名： " + user.getRole().getRoleName());
            // 还原数据
            role.setRoleName("普通用户");
            roleMapper.updateById(role);
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }
    }

}
