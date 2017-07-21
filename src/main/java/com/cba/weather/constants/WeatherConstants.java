/**
 * 
 */
package com.cba.weather.constants;

import org.springframework.stereotype.Component;

/**
 * Classs for storing the constants of minimum and maximum latitude, minimum and maximum elevation,
 * maximum temperature, maximum pressure and maximum relative humidity.
 * @author Rishina Valsalan
 *
 */
@Component
public class WeatherConstants {
	
	public static final int MAX_LAT = 90;
	public static final int MIN_LAT = 0;
	public static final int MAX_ELEVATION = 8848;
	public static final int MIN_ELEVATION = 0;
	public static final int MAX_GLOBAL_TEMPERATURE = 45;
	public static final int MAX_GLOBAL_HUMIDITY = 100;
	public static final int MAX_PRESSURE = 1020;

	
}
