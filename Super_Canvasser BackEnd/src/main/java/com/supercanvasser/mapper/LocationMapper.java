package com.supercanvasser.mapper;

import com.supercanvasser.bean.Location;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LocationMapper {

    /**
     * Get the location by ID
     */
    @Select("SELECT * FROM location WHERE id = #{id}")
    Location getLocationById(Integer id);


    @Insert("INSERT INTO location(id, number, street, unit, city, state, zip, longitude, latitude)" +
            "VALUES(#{id}, #{number}, #{street}, #{unit}, #{city}, #{state}, #{zip}, #{longitude}, #{latitude})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addLocation(Location location);


    @Delete("DELETE FROM location WHERE id = #{locationId}")
    void deleteLocation(@Param("locationId") Integer locationId);

    @Select("SELECT longitude FROM location WHERE id = #{id}")
    Double getLongitude(Integer id);

    @Select("SELECT latitude FROM location WHERE id = #{id}")
    Double getLatitude(Integer id);


    @Select("SELECT * FROM location WHERE id IN (SELECT locationId FROM campaign_locationIdList WHERE campaignId = #{id})")
    List<Location> getLocationByCampaignId(Integer id);



}
