package com.supercanvasser.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.supercanvasser.bean.Location;
import com.supercanvasser.constant.Constants;
import com.supercanvasser.exception.LocationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {

    private static GeoApiContext geoApiContext;

    public LocationService() {
        geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyCSBQ5PQYQZwAxqE08kxZlQKR1yuwu7Xnc")
                .build();
    }

    /**
     * Get the latitude and longitude from the string containing only one address.
     * Result is stored in a hash map.
     * @param locationStr
     * @return
     */
    public Map<String, Double> getLatlng(String locationStr){
        GeocodingResult[] results;
        Map<String, Double> latlng = new HashMap<>();
        try {
            results = GeocodingApi.geocode(geoApiContext, locationStr).await();
            latlng.put(Constants.LATITUDE, results[0].geometry.location.lat);
            latlng.put(Constants.LONGITUDE, results[0].geometry.location.lng);
        } catch (ApiException e) {
            throw new LocationException(e.getMessage());
        } catch (InterruptedException e) {
            throw new LocationException(e.getMessage());
        } catch (IOException e) {
            throw new LocationException(e.getMessage());
        }
        return latlng;
    }

    /**
     * Retrieve a list of location from a string
     * @param locationsStr
     * @return
     */
    public List<Location> retrieveLocations(String locationsStr){
        List<Location> locationList = new ArrayList<>();
        locationsStr = locationsStr.trim();
        String[] locationStrArr = locationsStr.split("\n");
        int size = locationStrArr.length;
        for(int i = 0; i < size; i++){
            //Storing each element in a location into a string array
            String[] sArr = locationStrArr[i].split(",");
            int len = sArr.length;
            Location location = null;
            if(len == 5 || len == 6){
                Integer number = Integer.valueOf(sArr[0].trim());
                String street = sArr[1].trim();
                String unit = null, city, state;
                Integer zip;

                if (len == 5){
                    city = sArr[2].trim();
                    state = sArr[3].trim();
                    zip = Integer.valueOf(sArr[4].trim());
                }
                else{
                    unit = sArr[2].trim();
                    city = sArr[3].trim();
                    state = sArr[4].trim();
                    zip = Integer.valueOf(sArr[5].trim());
                }
                Map<String, Double> latlng = getLatlng(locationStrArr[i]);
                location = new Location(null, number, street, unit, city, state, zip, latlng.get(Constants.LONGITUDE), latlng.get(Constants.LATITUDE));
            }
            locationList.add(location);
        }
        return locationList;
    }
}
