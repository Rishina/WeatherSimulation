package com.cba.weather.service;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cba.weather.WeatherTest;
import com.cba.weather.beans.WeatherData;



/**
 * Test class to test the methods of PressureService
 * @author Rishina Valsalan
 *
 */
public class PressureServiceTest extends WeatherTest {
	@Autowired
	PressureService pressureService;
	@Autowired
	WeatherData weatherData;

	/**
	 * Test method to check pressure value is calculated and is not zero.
	 * Sets the value for current date to be passed for the getValue method of PressureService
	 * Sets the values for elevation and latitude to be passed for the getPressure method of PressureService
	 */
	@Test
	public void testGetPressure(){
		double pressure;
		
		Calendar currentDateTime = Calendar.getInstance();
		currentDateTime.setTime(new Date());
		
		
		weatherData.setElevation(0);
		weatherData.setLatitude(0);
		pressure = pressureService.getPressure(currentDateTime, weatherData);
		
		assertTrue("Failure - Pressure value is not calculated", (pressure != 0) );
		
	}
	
	/**
	 * Test method to check if the pressure index is returned correct for the Evening time.
	 * A latitude value of 0 is passed to assert that it returns pressureIndex as 1
	 */
	@Test
	public void testGetLatitudePressureIndex(){
		double pressureIndex;
		double latitude =0;
		pressureIndex = pressureService.getLatitudePressureIndex(latitude);
		assertTrue("Failure - Pressure calculated from Latitude is not correct",pressureIndex == 1);
	}
	
	/**
	 * Test method to check if the pressure index is returned correct for the elevation
	 * The lowest elevation point value is passed and checked that it returns index value  of 1
	 */
	@Test
	public void testGetElevationPressureIndex(){
		double pressureIndex;
		double elevation=0;
		pressureIndex = pressureService.getElevationPressureIndex(elevation);
		assertTrue("Failure - Pressure calculated from Season Segment is not correct",pressureIndex == 1);
	}
	
	/**
	 * Test method to check if the pressure index is returned correct for the Hour of the day.
	 * The midnight hour is passed to check the index returned is 1.
	 */
	@Test
	public void testGetTimePressureIndex(){
		double pressureIndex;
		double hour=24;
		pressureIndex = pressureService.getTimePressureIndex(hour);
		assertTrue("Failure - Pressure calculated from Hour is not correct",pressureIndex == 1);
	}
	
}
