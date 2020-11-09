package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.SelectProvider;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.util.PrivilegeProvider;

import java.util.List;

public interface PrivilegeMapper {
    @SelectProvider(type = PrivilegeProvider.class, method = "selectById")
    SysPrivilege selectById(Long id);

    List<SysPrivilege> selectPrivilegeByRoleId(Long id);
}
