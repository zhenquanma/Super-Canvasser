package com.supercanvasser.mapper;

import com.supercanvasser.bean.User;
import com.supercanvasser.service.UserService;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;

public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> getAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(
                    property = "freeDays", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.UserMapper.getFreeDaysByCanvasserId",
                            fetchType = FetchType.LAZY
                    )
            ),
            @Result(
                    property = "campaignList", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.CampaignMapper.getCampaignByManagerId",
                            fetchType = FetchType.LAZY
                    )
            )
    })
    User getUserById(Integer id);


    @Select("SELECT * FROM user WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(
                    property = "freeDays", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.UserMapper.getFreeDaysByCanvasserId",
                            fetchType = FetchType.LAZY
                    )
            ),
            @Result(
                    property = "campaignList", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.CampaignMapper.getCampaignByManagerId",
                            fetchType = FetchType.LAZY
                    )
            )
    })
    User getUserByUsername(String username);


    @Insert("INSERT INTO user (first_name, last_name, username, password)" +
            " VALUES (#{firstName}, #{lastName}, #{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUser(User user);


    /**
     * Update parts of information
     *
     * @param firstName
     * @param lastName
     */
    @Update("UPDATE user SET first_name = #{firstName}, last_name = #{lastName} WHERE id = #{id}")
    void updateUser(@Param("id") Integer id, @Param("firstName") String firstName, @Param("lastName") String lastName);
//    @Update("UPDATE user SET first_name = #{firstName}, last_name = #{lastName}, WHERE id = #{id}")
//    void updateUser(User user);


    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    void updatePassword(@Param("id") Integer id, @Param("password") String password);



    @Insert("INSERT INTO user_freeDays(free_day, user_id) VALUES (#{date}, #{userId})")
    void addFreeDay(@Param("date") Date date, @Param("userId") Integer userId);


    @Delete("DELETE FROM user_freeDays WHERE user_id = #{userId}")
    void deleteAllFreeDay(Integer userId);


    @Select("SELECT free_day FROM user_freeDays WHERE user_id = #{userId}")
    List<Date> getFreeDaysByCanvasserId(Integer userId);


    @Select("SELECT MIN(user_id) FROM user_freeDays WHERE free_day = #{freeDay} GROUP BY free_day")
    Integer getCanvasserByFreeDay(Date date);

    @Delete("DELETE FROM user_freeDays WHERE user_id = #{userId} AND free_day = #{date}")
    void deleteFreeDayByDateUserId(@Param("date") Date date, @Param("userId") Integer userId);

    /**
     * Delete user by its id
     * @param id
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUserById(Integer id);

    @Select("SELECT username FROM user WHERE id = #{id}")
    String getUsernameById(Integer id);


    @Insert("INSERT INTO user_currentLocation(userId, locationId) VAlUES(#{canvasserId}, #{locationId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addCurrentLocationId(@Param("canvasserId") Integer canvasserId, @Param("locationId") Integer locationId);

    @Select("SELECT locationId FROM user_currentLocation WHERE userId = #{canvasserId}")
    Integer getCurrentLocationId(Integer canvasserId);


    @Update("UPDATE user_currentLocation SET locationId = #{locationId} WHERE userId = #{canvasserId}")
    void setCurrentLocationId(@Param("canvasserId") Integer canvasserId, @Param("locationId") Integer locationId);

    @Delete("DELETE FROM user_currentLocation WHERE userId = #{canvasserId}")
    void deleteCurrentLocationId(Integer canvasserId);
}
