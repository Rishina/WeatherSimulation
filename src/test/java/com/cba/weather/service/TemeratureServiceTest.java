package com.cba.weather.service;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cba.weather.WeatherTest;
import com.cba.weather.beans.WeatherData;



/**
 * Test class to test the methods of TemperatureService
 * @author Rishina Valsalan
 *
 */
public class TemeratureServiceTest extends WeatherTest {
	@Autowired
	TemperatureService temperatureService;
	@Autowired
	WeatherData weatherData;

	/**
	 * Test method to check temperature value is calculated and is not zero.
	 * Sets the value for current date to be passed for the getValue method of TemperatureService
	 * Sets the values for elevation and latitude to be passed for the getTemperature method of TemperatureService
	 */
	@Test
	public void testGetTemperature(){
		double temperature;
		
		Calendar currentDateTime = Calendar.getInstance();
		currentDateTime.setTime(new Date());
		
		
		weatherData.setElevation(0);
		weatherData.setLatitude(0);
		temperature = temperatureService.getTemperature(currentDateTime, weatherData);
		
		assertTrue("Failure - Temperature value is not calculated", (temperature != 0) );
		
	}
	
	/**
	 * Test method to check if the temperature index is returned correct for the Evening time.
	 * A latitude value of 0 is passed to assert that it returns temperatureIndex as 1
	 */
	@Test
	public void testGetLatitudeTemperatureIndex(){
		double temperatureIndex;
		double latitude =0;
		temperatureIndex = temperatureService.getLatitudeTemperatureIndex(latitude);
		assertTrue("Failure - Temperature calculated from Latitude is not correct",temperatureIndex == 1);
	}
	
	/**
	 * Test method to check if the temperature index is returned correct for the elevation
	 * The lowest elevation point value is passed and checked that it returns index value  of 1
	 */
	@Test
	public void testGetElevationTemperatureIndex(){
		double temperatureIndex;
		double elevation=0;
		temperatureIndex = temperatureService.getElevationTemperatureIndex(elevation);
		assertTrue("Failure - Temperature calculated from Season Segment is not correct",temperatureIndex == 1);
	}
	
	/**
	 * Test method to check if the temperature index is returned correct for the Hour of the day.
	 * The hour is passed as 2pm when temperature is maximum to check the index returned is 1.
	 */
	@Test
	public void testGetTimeTemperatureIndex(){
		double temperatureIndex;
		double hour=14;
		temperatureIndex = temperatureService.getTimeTemperatureIndex(hour);
		assertTrue("Failure - Temperature calculated from Hour is not correct",temperatureIndex == 1);
	}
	
}
