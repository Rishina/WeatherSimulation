/**---------------------------------------------------------------------------------------------------------------------
 * Application to generate fake weather feed for a game
 *
 * The application takes a location.txt file as input.The file has json data for the locations of which weather needs 
 * to be calculated and the latitude, longitude and elevation. The format is :
 * { "location": "Sydney","position": [-33.86,151.21,39]}
 *
 * From this file, we parse and find the elevation type and latitude region of each of the locations. They could be:
 * Elevation Segment - Sea Level/ Plateau/ Hilly Area/ Very high altitude area.
 * Latitude - Tropical/ Mid-Tropical/ Polar.
 *
 * Also from the current date and time , we find the time of the day and the season in that location. And , they could be:
 * Day Segment - Early Morning/ Morning/ Noon/ Evening/ Night
 * Season Segment - Summer , Autumn, Winter, Spring.
 *
 * Based on these segments for each location, the humidity , pressure , temperature and weather condition is calculated 
 * for each of the location.(Assumptions like temperature decreases in very high altidue area are used. So, if it is a very 
 * high altitude area, temperature can be smaller value).
 *
 * The calculated values are logged and are written out to a text file - Weather.Txt in \target\resources folder.
 *
 * The Spring boot project executes a task scheduler every 30 seconds from the first run to get weather data from 10 cities. 
 * -------------------------------------------------------------------------------------------------------------------------
 */
package com.cba.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



 /** 
 * Class with main method for starting the spring boot application.
 * @author Rishina Valsalan
 *
 */
@SpringBootApplication
@EnableScheduling
public class Weather {

	/**
	 * Main method to start the application.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Weather.class, args);
	}

}
