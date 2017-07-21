
package com.cba.weather.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cba.weather.beans.WeatherData;
import com.cba.weather.repository.WeatherReportingData;

/**
 * Service implementation class to get the position data and to calculate weather parameters and generate the feed.
 * @author Rishina Valsalan
 *
 */
@Service("weatherReportingService")
public class WeatherReportingServiceImpl implements WeatherReportingService{
	
	public static Logger logger = LoggerFactory.getLogger(WeatherReportingServiceImpl.class);
	
	@Autowired
	WeatherReportingData weatherReportingData;
	@Autowired
	HumidityService humidityService;
	@Autowired
	PressureService pressureService;
	@Autowired
	TemperatureService temperatureService;
	@Autowired
	WeatherConditionService weatherConditionService;
	
	/**
	 * Service method to get weather data
	 * 1)Calls getPositionData() to get the locations , their positions and elevations from the location.txt file
	 * 2)From the position data, loops through the arrayList of Locations ; For each location , calculate the humidity,
	 * temperature and pressure and find the weather condition.
	 * 3)Writes weather data in '|' delimited format to WeatherFeed.txt file at \target.
	 */
	public ArrayList<WeatherData> getWeather(Calendar currentDateTime){
		
		ArrayList<WeatherData> arrayListWeatherData = getPositionData();
		arrayListWeatherData = calculateWeather(arrayListWeatherData,currentDateTime);
		generateFeed(arrayListWeatherData,currentDateTime);
		
		return arrayListWeatherData;
	}

	/**
	 * This method gets the locations and their latitude , longitude and elevation data from the location file.
	 */
	public ArrayList<WeatherData> getPositionData(){
		ArrayList<WeatherData> arrayListWeatherData = weatherReportingData.getPositionData();
		return arrayListWeatherData;
	}
	
	/**
	 * This method calculates the weather parameters.
	 * It calculates - Relative Humidity, Pressure, Temperature, WeatherCondition
	 * @param arrayListWeatherData
	 * @param currentDateTime
	 * @return
	 */
	public ArrayList<WeatherData> calculateWeather(ArrayList<WeatherData> arrayListWeatherData, Calendar currentDateTime){
		for(WeatherData weatherData : arrayListWeatherData){
			
			weatherData.setHumidity(Math.round(humidityService.getHumidity(currentDateTime,weatherData) ));
			weatherData.setPressure(Math.round(pressureService.getPressure(currentDateTime,weatherData) ));
			weatherData.setTemperature(Math.round(temperatureService.getTemperature(currentDateTime,weatherData)) );
			weatherData.setWeatherCondition(weatherConditionService.getWeatherCondition(currentDateTime, weatherData) );
			
		}
		return arrayListWeatherData;
		
	}
	/**
	 * This method gets the current time in ISO8601 date format for the feed and writes the delimiter formatted string, holding the weather data for 10 cities, to 
	 * a file in the Application folder.If the file is not present already, new file is created . Else, the
	 * file is updated with the feed from each run.
	 * 
	 * @param arrayListWeatherData 
	 * @param currentDateTime
	 */
	public void generateFeed(ArrayList<WeatherData> arrayListWeatherData, Calendar currentDateTime){
		
		String strCurrentDateTime = getISODate(currentDateTime);
		
		if(null!=arrayListWeatherData && arrayListWeatherData.size()>0){
			try{
				Path filePath = Paths.get(System.getProperty("user.dir")+"\\target\\WeatherFeed.txt");
				try(BufferedWriter writer = Files.newBufferedWriter(filePath,StandardOpenOption.CREATE,StandardOpenOption.APPEND)){
					for (WeatherData weatherData : arrayListWeatherData) {
						writer.write(weatherData.getLocation()
								+"|"+ weatherData.getLatitude()+","+weatherData.getLongitude()+","+weatherData.getElevation()
								+"|"+ strCurrentDateTime
								+"|"+ weatherData.getWeatherCondition()
								+"|"+ weatherData.getTemperature()
								+"|"+ weatherData.getPressure()
								+"|"+ weatherData.getHumidity());
						writer.write(System.lineSeparator());
					}
				}catch(IOException ioException){
					logger.debug("Could not write to file"+ioException.getMessage());
				}
			}catch(InvalidPathException invalidPathException){
				logger.debug("Could not find the file path");
			}
		}
		else{
			logger.debug("There is no data for generating the feed");
		}
	}
	
	/**
	 * This method gets the current time in ISO8601 date format
	 * @param currentDateTime
	 * @return
	 */
	public String getISODate(Calendar currentDateTime){
		DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;
		LocalDateTime localDateTime = LocalDateTime.ofInstant((currentDateTime).toInstant(), ZoneId.of("UTC"));
		return (dateFormatter.format(localDateTime));
	}
}
