package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysUser;
import tk.mybatis.simple.model.SysRole;

import java.util.*;

public interface UserMapper {
    SysUser selectById(Long id);

    List<SysUser> selectAll();

    List<SysRole> selectRoleByUserId(Long userId);

    int insert(SysUser sysUser);

    int insert2(SysUser sysUser);

    int insert3(SysUser sysUser);

    int updateById(SysUser sysUser);

    int deleteById(Long id);
    // 以上等效于
    // int deleteById(SysUser sysUser);

    /**
     * 根据用户id和角色的enabled状态获取用户的角色
     *
     * @param userId
     * @param enabled
     * @return
     */
    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId, @Param("enabled") Integer enabled);

    List<SysUser> selectByUser(SysUser sysUser);

    int updateByIdSelective(SysUser sysUser);

    SysUser selectByIdOrUserName(SysUser sysUser);

    List<SysUser> selectByIdList(List<Long> idList);

    List<SysUser> selectByIdMap(@Param("idKey") Map<String, Long> idMap);

    List<SysUser> selectByIdMap2(Map<String, Long> idMap);

    int insertList(List<SysUser> userList);

    /**
     *  通过Map更新列
     *
     * @param map
     * @return
     */
    int updateByMap(Map<String, Object> map);

    /**
     * 根据用户id获取用户信息和用户的角色信息
     *
     * @param id
     * @rertun
     */
    SysUser selectUserAndRoleById(Long id);

    SysUser selectUserAndRoleById2(Long id);

    SysUser selectUserAndRoleByIdSelect(Long id);

    /**
     * 获取所有的用户以及对应的所有角色
     *
     * @return
     */
    List<SysUser> selectAllUserAndRoles();

    SysUser selectAllUserAndRolesSelect(Long id);

    /**
     * 使用存储过程查询用户信息
     *
     * @param user
     * @return
     */
    void selectUserById(SysUser user);

    /**
     * 使用存储过程分页查询
     *
     * @param userName
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SysUser> selectUserPage(Map<String, Object> params);

    /**
     *保存用户信息和角色关联信息
     *
     * @param user
     * @param roleIds
     * @return
     */
    int insertUserAndRoles(@Param("user")SysUser user, @Param("roleIds")String roleIds);

    /**
     * 根据用户id删除用户和用户的角色信息
     *
     * @param id
     * @return
     *
     */
    int deleteUserById(Long id);
}

