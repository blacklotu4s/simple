package tk.mybatis.simple.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * 用户表
 */

public class SysUser implements Serializable {
    private static final long serialVersionUID = 6320941908222932113L;
    private Long id;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userInfo;
    private byte[] headImg;
    private Date createTime;
    private SysRole role;
    private List<SysRole> roleList;

    public void setId (Long id) {
        this.id = id;
    }

    public Long getId () {
        return id;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserPassword (String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword () {
        return userPassword;
    }

    public void setUserEmail (String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setHeadImg(byte[] headImg) {
        this.headImg = headImg;
    }

    public byte[] getHeadImg() {
        return headImg;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }
}
