package com.supercanvasser;

import com.google.maps.GeoApiContext;
import com.supercanvasser.service.LocationService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@MapperScan(value = "com.supercanvasser.mapper")
@SpringBootApplication
public class SuperCanvasserApplication {

    @Bean
    public LocationService locationService(){
        return new LocationService();
    }

    public static void main(String[] args) {
        SpringApplication.run(SuperCanvasserApplication.class, args);
    }
}
