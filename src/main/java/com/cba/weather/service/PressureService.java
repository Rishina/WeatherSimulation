package com.cba.weather.service;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.cba.weather.beans.WeatherData;
import com.cba.weather.constants.WeatherConstants;

/**
 * Service class to calculate Pressure value based on different factors
 * @author Rishina Valsalan
 */
@Component
public class PressureService {

    /**
     * Method to calculate the pressure index based on latitude value.
     * It is assumed that the pressure is minimum in Polar (MAX_LAT) and maximum at the equator (MIN_LAT)
     * The below calculation would return an index of 0 for polar and index of 1 for equator  - the min and max values.
     * And for latitude values in between , the index would be between 0 and 1.
     * This pressure index is used in calculating the pressure.
     * @param latitude
     * @return
     */
    public double getLatitudePressureIndex(double latitude) {
    	latitude=Math.abs(latitude);
        return 1 - (latitude - WeatherConstants.MIN_LAT) / (WeatherConstants.MAX_LAT - WeatherConstants.MIN_LAT);
    }

    /**
     * Method to calculate the pressure index based on elevation value.
     * It is assumed that the pressure is minimum at earth's top-most point (MAX_ELEVATION) and maximum at the sea 
     * level (MIN_ELEVATION)
     * The below calculation would return an index of 0 for MAX_ELEVATION and index of 1 for MIN_ELEVATION  - the min 
     * and max values. And for elevation values in between , the index would be between 0 and 1.
     * This pressure index is used in calculating the pressure. 
     * @param elevation
     * @return
     */
    public double getElevationPressureIndex(double elevation) {
        return 1 - (elevation - WeatherConstants.MIN_ELEVATION) / (WeatherConstants.MAX_ELEVATION - WeatherConstants.MIN_ELEVATION);
    }

    /**
     * Method to calculate the pressure index based on time of the day.
     * It is assumed that the pressure peaks at morning and night.
     * The below calculation would return a  index value increasing from 6am to 10 am(max) and reducing 
     * after 10am and again increasing at night and then reducing in the morning 
     * This pressure index is used in calculating the pressure. 
     * @param elevation
     * @return
     */
    public double getTimePressureIndex(double hour) {
    	double timeIndex;
    	double minHour;
    	double maxHour;
        if (hour > 6 && hour <= 10) {
            minHour = 6;
            maxHour = 10;
            timeIndex = ((hour - minHour) / (maxHour - minHour));
        } else if (hour > 10 && hour <= 18) {
            minHour = 10;
            maxHour = 18;
            timeIndex = (1 - (hour - minHour) / (maxHour - minHour));
        }else if(hour > 18 && hour <= 24){
        	minHour = 18;
            maxHour = 24;
            timeIndex = ((hour - minHour) / (maxHour - minHour));
        } else {
            minHour = 0;
            maxHour = 6;
            timeIndex = (1 - (hour - minHour) / (maxHour - minHour));
        }
        return timeIndex;
    }

    /**
     * Calculate the pressure from the index values calculated from Latitude, Elevation and Time.
     * An average of the three indices is found and is multiplied with the global maximum pressure to
     * get the pressure value.
     * @param currentDateTime
     * @return
     */
    public double getPressure(Calendar currentDateTime,WeatherData weatherData) {
    	double latitudeIndex = getLatitudePressureIndex(weatherData.getLatitude());
    	double elevationIndex = getElevationPressureIndex(weatherData.getElevation());
    	double timeIndex = getTimePressureIndex(currentDateTime.get(Calendar.HOUR_OF_DAY));
    	double pressureIndex = (latitudeIndex + elevationIndex + timeIndex) / 3;
    	weatherData.setPressureIndex(pressureIndex);
        return WeatherConstants.MAX_PRESSURE * pressureIndex;
    }
}
