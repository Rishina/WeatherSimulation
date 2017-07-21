package com.cba.weather.service;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.cba.weather.beans.WeatherData;
import com.cba.weather.constants.WeatherConstants;

/**
 * Service class to calculate Temperature value based on different factors
 * @author Rishina Valsalan
 */
@Component
public class TemperatureService {
   
    /**
     * Method to calculate the temperature index based on latitude value.
     * It is assumed that the temperature is minimum in Polar (MAX_LAT) and maximum at the equator (MIN_LAT)
     * The below calculation would return an index of 0 for polar and index of 1 for equator  - the min and max values.
     * And for latitude values in between , the index would be between 0 and 1.
     * This temperature index is used in calculating the temperature.
     * @param latitude
     * @return
     */
    public double getLatitudeTemperatureIndex(double latitude) {
    	latitude=Math.abs(latitude);
        return 1 - (latitude - WeatherConstants.MIN_LAT) / (WeatherConstants.MAX_LAT - WeatherConstants.MIN_LAT);
    }

    /**
     * Method to calculate the temperature index based on elevation value.
     * It is assumed that the temperature is minimum at earth's top-most point (MAX_ELEVATION) and maximum at the sea 
     * level (MIN_ELEVATION)
     * The below calculation would return an index of 0 for MAX_ELEVATION and index of 1 for MIN_ELEVATION  - the min 
     * and max values. And for elevation values in between , the index would be between 0 and 1.
     * This temperature index is used in calculating the temperature. 
     * @param elevation
     * @return
     */
    public double getElevationTemperatureIndex(double elevation) {
        return 1 - (elevation - WeatherConstants.MIN_ELEVATION) / (WeatherConstants.MAX_ELEVATION - WeatherConstants.MIN_ELEVATION);
    }

    /**
     * Method to calculate the temperature index based on time of the day.
     * It is assumed that the temperature keeps increasing from 6am in the morning to 2pm in the afternoon.
     * And decreases from 2pm to midnight and then decreases very gradually till 6am
     * The below calculation would return a maximum index value between 6am to 2pm and lower values after that. 
     * This temperature index is used in calculating the temperature. 
     * @param elevation
     * @return
     */
    public double getTimeTemperatureIndex(double hour) {
        double timeIndex;
        double minHour;
        double maxHour;
        if (hour > 6 && hour <= 14) {
            minHour = 6;
            maxHour = 14;
            timeIndex = ((hour - minHour) / (maxHour - minHour));
        } else if (hour > 14 && hour <= 24) {
            minHour = 14;
            maxHour = 24;
            timeIndex = (1 - (hour - minHour) / (maxHour - minHour));
        } else {
            minHour = 0;
            maxHour = 6;
            timeIndex = (double) Math.sqrt(1 - (hour - minHour) / (maxHour - minHour));
        }
        return timeIndex;
    }

    /**
     * Calculate the temperature from the index values calculated from Latitude, Elevation and Time.
     * An average of the three indices is found and is multiplied with the global maximum temperature to
     * get the temperature value.
     * @param currentDateTime
     * @return
     */
    public double getTemperature(Calendar currentDateTime,WeatherData weatherData) {
        double latitudeIndex = getLatitudeTemperatureIndex(weatherData.getLatitude());
        double elevationIndex = getElevationTemperatureIndex(weatherData.getElevation());
        double timeIndex = getTimeTemperatureIndex(currentDateTime.get(Calendar.HOUR_OF_DAY));
        double tempIndex = (latitudeIndex + elevationIndex + timeIndex) / 3;
        return (WeatherConstants.MAX_GLOBAL_TEMPERATURE * tempIndex);
    }
}
