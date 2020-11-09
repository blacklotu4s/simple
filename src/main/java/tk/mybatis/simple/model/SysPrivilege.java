package tk.mybatis.simple.model;

/*
权限表
 */

import java.io.Serializable;

public class SysPrivilege  implements Serializable {
    private static final long serialVersionUID = 6320941908222932118L;
    private Long id;
    private String privilegeName;
    private String privilegeUrl;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeUrl(String privilegeUrl) {
        this.privilegeUrl = privilegeUrl;
    }

    public String getPrivilegeUrl() {
        return privilegeUrl;
    }
}
