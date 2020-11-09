package tk.mybatis.simple.util;

import org.apache.ibatis.jdbc.SQL;

public class PrivilegeProvider {
    public String selectById(final Long id) {
        return new SQL() {
            {
                SELECT("id, privilege_name privilegeName, privilege_url privilegeUrl");
                FROM("sys_privilege");
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
