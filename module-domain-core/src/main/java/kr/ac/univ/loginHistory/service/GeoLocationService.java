package kr.ac.univ.loginHistory.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
public class GeoLocationService {
    private final DatabaseReader databaseReader;

    public GeoLocationService(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    public String getLocationByIp(String ip)  {
        InetAddress ipAddress = null;
        CityResponse cityResponse = null;
        String location = null;

        try {
            ipAddress = InetAddress.getByName(ip);
            cityResponse = databaseReader.city(ipAddress);

            String continent = (cityResponse.getContinent() != null) ? cityResponse.getContinent().getName() : "";
            String country = (cityResponse.getCountry() != null) ? cityResponse.getCountry().getName() : "";
            String city = cityResponse.getCity().getName();

            location = country + ": " + city;
        }
        // localhost에서 테스트하는 경우 예외처리
        catch(AddressNotFoundException e) {
            location = "localhost";
        } catch (Exception e) {
            location = "Error Occurred: " + ipAddress;
        }

        return location;
    }
}