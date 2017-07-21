package com.cba.weather.service;

import java.util.ArrayList;
import java.util.Calendar;

import com.cba.weather.beans.WeatherData;

/**
 * Interface for getting the position data and weather data.
 * @author Rishina Valsalan
 *
 */

public interface WeatherReportingService {
	public  ArrayList<WeatherData> getWeather(Calendar currentDateTime);
	public ArrayList<WeatherData> getPositionData();

}
