package com.cba.weather.service;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.cba.weather.beans.WeatherData;
import com.cba.weather.constants.WeatherConstants;

/**
 * Service class to calculate Humidity value based on different factors
 * @author Rishina Valsalan
 */
@Component
public class HumidityService {

 

    /**
     * Method to calculate the humidity index based on latitude value.
     * Humidity is maximum at Polar (MAX_LAT) and at the equator (MIN_LAT)
     * The below calculation would return an index of 1 for polar and index of 1 for equator. 
     * It reduces from reduces from 1 to .5 from equator  to mid tropical and from mid tropical again increases to be 1 at polar.
     * This humidity index is used in calculating the humidity.
     * @param latitude
     * @return
     */
    public double getLatitudeHumidityIndex(double latitude) {
    	latitude=Math.abs(latitude);
    	if(latitude>=0 && latitude<=45){
    		return 1 - (latitude - WeatherConstants.MIN_LAT) / (WeatherConstants.MAX_LAT - WeatherConstants.MIN_LAT);
    	}else {
    		return  (latitude - WeatherConstants.MIN_LAT) / (WeatherConstants.MAX_LAT - WeatherConstants.MIN_LAT);
    	}
    }

    /**
     * Method to calculate the humidity index based on elevation value.
     * It is assumed that the humidity is maximum at earth's top-most point (MAX_ELEVATION) and minimum at the sea 
     * level (MIN_ELEVATION)
     * The below calculation would return an index of 1 for MAX_ELEVATION and index of 0 for MIN_ELEVATION  - the min 
     * and max values. And for elevation values in between , the index would be between 0 and 1.
     * This humidity index is used in calculating the humidity. 
     * @param elevation
     * @return
     */
    public double getElevationHumidityIndex(double elevation) {
        return  (elevation - WeatherConstants.MIN_ELEVATION) / (WeatherConstants.MAX_ELEVATION - WeatherConstants.MIN_ELEVATION);
    }

    /**
     * Method to calculate the humidity index based on time of the day.
     * It is assumed that the humidity goes up at night
     * The below calculation would return an index value that increases 6am to 12am and then reduces till 6 am. 
     * This humidity index is used in calculating the humidity. 
     * @param elevation
     * @return
     */
    public double getTimeHumidityIndex(double hour) {
        double timeIndex;
        double minHour;
        double maxHour;
        if (hour > 6 && hour <= 24) {
            minHour = 6;
            maxHour = 24;
            timeIndex = ((hour - minHour) / (maxHour - minHour));
        } else {
            minHour = 0;
            maxHour = 6;
            timeIndex = 1 - (double) Math.sqrt( (hour - minHour) / (maxHour - minHour));
        }
        return timeIndex;
    }

    /**
     * Calculate the humidity from the index values calculated from Latitude, Elevation and Time.
     * An average of the three indices is found and is multiplied with the global maximum humidity to
     * get the humidity value.
     * @param currentDateTime
     * @return
     */
    public double getHumidity(Calendar currentDateTime, WeatherData weatherData) {
        double latitudeIndex = getLatitudeHumidityIndex(weatherData.getLatitude());
        double elevationIndex = getElevationHumidityIndex(weatherData.getElevation());
        double timeIndex = getTimeHumidityIndex(currentDateTime.get(Calendar.HOUR_OF_DAY));
        double humidityIndex = (latitudeIndex + elevationIndex + timeIndex) / 3;
        weatherData.setHumidityIndex(humidityIndex);
        return WeatherConstants.MAX_GLOBAL_HUMIDITY * humidityIndex;
    }
}
