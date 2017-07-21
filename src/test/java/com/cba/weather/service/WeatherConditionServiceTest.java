package com.cba.weather.service;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cba.weather.WeatherTest;
import com.cba.weather.beans.WeatherData;


/**
 * Test class to test the methods of WeatherConditionService class
 * @author Rishina Valsalan
 *
 */
public class WeatherConditionServiceTest extends WeatherTest {
	@Autowired
	WeatherConditionService weatherConditionService;
	@Autowired
	WeatherData weatherData;

	/**
	 * Test method to check weather condition value is calculated 
	 * Sets the value for current date to be passed for the getValue method of WeatherConditionService
	 * Set the value for humidity and pressure index to be passed for the getWeatherCondition method of WeatherConditionService
	 *
	 */
	@Test
	public void testGetWeatherCondition(){
		String weatherCondition;
		
		Calendar currentDateTime = Calendar.getInstance();
		currentDateTime.setTime(new Date());
		
		weatherData.setHumidityIndex(.8);
		weatherData.setPressureIndex(.2);
		weatherCondition = weatherConditionService.getWeatherCondition(currentDateTime, weatherData);
		assertTrue("Failure - Weather Condition value is not calculated", weatherCondition.equals("Rain") );
		
	}
}	

