package tk.mybatis.simple.model;

/*
用户角色关联表
 */

public class SysUserRole {
    private Long userId;
    private Long roleId;

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getUserId() {
        return userId;
    }
}
