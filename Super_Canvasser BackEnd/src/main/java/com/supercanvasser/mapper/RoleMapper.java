package com.supercanvasser.mapper;

import com.supercanvasser.bean.Role;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RoleMapper {

    @Select("SELECT * FROM role WHERE id = #{id}")
    Role getRoleById(Integer id);


    @Update("UPDATE role SET name = #{name} WHERE id = #{id}")
    void updateRoleById(Integer id);

}
