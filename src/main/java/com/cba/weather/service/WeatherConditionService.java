/**
 * 
 */
package com.cba.weather.service;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.cba.weather.beans.WeatherData;

/**
 * Class to calculate the weather condition - Rain / Sunny / Snow
 * @author Rishina Valsalan
 *
 */
@Component
public class WeatherConditionService {
	
	/**
	 * Method to get the weather condition.
	 * If the humidity is too high, it causes rain. Low pressure have rains.
	 * The average of humidity index and pressure index is calculated.
	 * If the average is less than 0.5, then the weather condition is Sunny. Else, rain.
	 * If the temperature is less tha 0, then the weather condition is Snow.
	 * 
	 * @param currentDateTime
	 * @param dataSegments
	 * @return
	 */
	public String getWeatherCondition(Calendar currentDateTime, WeatherData weatherData){
		double humidityIndex = weatherData.getHumidityIndex();
		double pressureIndex  = weatherData.getPressureIndex();
		
		double rainIndex = ( humidityIndex + (1-pressureIndex) )/2;
		
		if(weatherData.getTemperature()< 0){
			return "Snow";
		}else if (rainIndex >0.5){
			return "Rain";
		}else{
			return "Sunny";
		}
	}
}
