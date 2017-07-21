package com.cba.weather.service;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cba.weather.WeatherTest;
import com.cba.weather.beans.WeatherData;



/**
 * Test class to test the methods of HumidityService
 * @author Rishina Valsalan
 *
 */
public class HumidityServiceTest extends WeatherTest {
	@Autowired
	HumidityService humidityService;
	@Autowired
	WeatherData weatherData;

	/**
	 * Test method to check humidity value is calculated and is not zero.
	 * Sets the value for current date to be passed for the getValue method of HumidityService
	 * Sets the values for elevation and latitude to be passed for the getHumidity method of HumidityService
	 */
	@Test
	public void testGetHumidity(){
		double humidity;
		
		Calendar currentDateTime = Calendar.getInstance();
		currentDateTime.setTime(new Date());
		
		
		weatherData.setElevation(0);
		weatherData.setLatitude(0);
		humidity = humidityService.getHumidity(currentDateTime, weatherData);
		
		assertTrue("Failure - Humidity value is not calculated", (humidity != 0) );
		
	}
	
	/**
	 * Test method to check if the humidity index is returned correct for the Evening time.
	 * A latitude value of 0 is passed to assert that it returns humidityIndex as 1
	 */
	@Test
	public void testGetLatitudeHumidityIndex(){
		double humidityIndex;
		double latitude =0;
		humidityIndex = humidityService.getLatitudeHumidityIndex(latitude);
		assertTrue("Failure - Humidity calculated from Latitude is not correct",humidityIndex == 1);
	}
	
	/**
	 * Test method to check if the humidity index is returned correct for the elevation
	 * The highest elevation point value is passed and checked that it returns index value  of 1
	 */
	@Test
	public void testGetElevationHumidityIndex(){
		double humidityIndex;
		double elevation=8848;
		humidityIndex = humidityService.getElevationHumidityIndex(elevation);
		assertTrue("Failure - Humidity calculated from Season Segment is not correct",humidityIndex == 1);
	}
	
	/**
	 * Test method to check if the humidity index is returned correct for the Hour of the day.
	 * The midnight hour is passed to check the index returned is 1.
	 */
	@Test
	public void testGetTimeHumidityIndex(){
		double humidityIndex;
		double hour=24;
		humidityIndex = humidityService.getTimeHumidityIndex(hour);
		assertTrue("Failure - Humidity calculated from Hour is not correct",humidityIndex == 1);
	}
	
}
