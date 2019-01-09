package com.supercanvasser.mapper;

import com.supercanvasser.bean.Role;
import com.supercanvasser.bean.User;
import com.supercanvasser.bean.UserRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface UserRoleMapper {


    /**
     * Get the role of a specific user by its id
     *
     * @param userId
     * @return
     */
    @Select("SELECT * FROM user_role WHERE userId = #{userId}")
    @Results({
            @Result( property = "userId", column = "userId" ),
            @Result( property = "roleId", column = "roleId"),
            @Result(
                    property = "user", column = "userId",
                    javaType = User.class,
                    one = @One(
                            select = "com.supercanvasser.mapper.UserMapper.getUserById",
                            fetchType = FetchType.EAGER
                    )
            ),
            @Result(
                    property = "role", column = "roleId",
                    javaType = Role.class,
                    one = @One(
                            select = "com.supercanvasser.mapper.RoleMapper.getRoleById",
                            fetchType = FetchType.EAGER
                    )
            )
    })
    List<UserRole> getByUserId(Integer userId);


    /**
     * Find all UserRole mappings for a role idr
     *
     * @param roleId
     * @return
     */
    @Select("SELECT * FROM user_role WHERE roleId = #{roleId}")
    @Results({
            @Result(
                    property = "user", column = "userId",
                    javaType = User.class,
                    one = @One(
                            select = "com.supercanvasser.mapper.UserMapper.getUserById",
                            fetchType = FetchType.EAGER
                    )
            ),
            @Result(
                    property = "role", column = "roleId",
                    javaType = Role.class,
                    one = @One(
                            select = "com.supercanvasser.mapper.RoleMapper.getRoleById",
                            fetchType = FetchType.EAGER
                    )
            )
    })
    List<UserRole> getByRoleId(Integer roleId);


    @Insert("INSERT INTO user_role (userId, roleId) VALUES (#{userRole.userId}, #{userRole.roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUserRole(@Param("userRole") UserRole userRole);


    @Delete("DELETE FROM user_role WHERE userId = #{userId}, AND roleId = #{roleId}")
    void deleteUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Delete("DELETE FROM user_role WHERE id = #{userRoleId}")
    void deleteUserRoleById(Integer userRoleId);

}
