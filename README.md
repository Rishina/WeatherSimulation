# WeatherSimulation

Application to generate fake weather feed for a game.
The application takes 10 locations and their positions as input. Using the location data and the algorithms to get humidity, pressure, temperature and weather condition, the weather feed is generated and written to the Weather.txt file. The task scheduler in this Spring boot project executes every 30 seconds to generate the feed.

Based on these segments for each location, the humidity , pressure , temperature and weather condition is calculated for each of the location.(Assumptions like temperature decreases in very high altidue area are used. So, if it is a very high altitude area, temperature can be smaller value).

## Algorithm
The application takes a location.txt file as input.The file has json data for the locations of which weather needs to be calculated and the latitude, longitude and elevation. The format is :
{ "location": "Sydney","position": [-33.86,151.21,39]}

### Temperature, Pressure and Relative Humidity Algorithm
For each location, temperature index values based on latitude , elevation and time is calculated.
It is assumed that the temperature is minimum in Polar (MAX_LAT) and maximum at the equator (MIN_LAT)
Temperature index  = 1 - (latitude - MIN_LAT) / (MAX_LAT - MIN_LAT)

[Normalisation is used here to scale the data to have values between 0 and 1.
Formula used to achieve this is x_new = (x-x_min) / (x_max - x_min) ]

The temperature index is value subtracted from 1 because value is maximum at 0 latitude and minimum at 90 latitude.
Similar calculations are used to get the temperature index from elevation and hour of the day.
The temperature indices are averaged and the average value is multiplied with the MAX_GL0BAL_TEMP to get the temperature of a location.

Same algorithms with changes for the assumptions in behavior is used to calculate pressure and temperature.

### Weather Condition Algorithm

For a location if the pressure is low or humidity is high , there is chance of rain.
The location 's pressure index and humidity index is averaged and if the value>0.5, the weather condition is set to Rain.
If the temperature for the location is less than zero, the weather condition is Snow. Otherwise, Sunny.


The calculated values are logged and are written out to a text file - Weather.Txt in \target\resources folder.


## Softwares Required

* [jdk1.8]
* [Maven 3.3.9] - Dependency Management
* [Eclipse Luna] - IDE 

## Installation steps.
* Install JDK from http://www.oracle.com/technetwork/java/javase/downloads/index.html
* Install maven from https://maven.apache.org/download.cgi
* Set the Java and Maven folder paths in the Path Environment variable.
* Install Eclipse from http://www.eclipse.org/downloads/eclipse-packages/ or any use any other IDE

Import the project into eclipse as existing project from Eclipse and run the application.

## Steps to run the application and the testcases
Open the command prompt and change directory to the project folder and execute the below commands.
* mvn clean test - Build and run test cases
* mvn exec:java - Execute


## Sample Output
Sydney|-33.86,151.21,39.0|2017-07-20T23:37:51.248|Sunny|30.0|806.0|26.0<br />
Melbourne|-37.81,144.96,31.0|2017-07-20T23:37:51.248|Sunny|29.0|791.0|25.0<br />
Canberra|-35.47,149.01,720.0|2017-07-20T23:37:51.248|Sunny|28.0|773.0|28.0<br />
Victoria|-36.68,143.58,209.0|2017-07-20T23:37:51.248|Sunny|29.0|788.0|26.0<br />
Hobart|42.88,147.32,19.0|2017-07-20T23:37:51.248|Sunny|28.0|772.0|23.0<br />
Perth|-31.95,115.85,15.0|2017-07-20T23:37:51.248|Sunny|30.0|814.0|27.0<br />
Adelaide|-34.66,138.68,59.0|2017-07-20T23:37:51.248|Sunny|30.0|802.0|26.0<br />
Brisbane|-27.47,153.02,28.0|2017-07-20T23:37:51.248|Sunny|31.0|830.0|29.0<br />
NewCastle|32.92,151.78,9.0|2017-07-20T23:37:51.248|Sunny|30.0|810.0|27.0<br />
GoldCoast|-28.01,153.4,12.0|2017-07-20T23:37:51.248|Sunny|31.0|829.0|29.0<br />


## Major classes used in the solution
* WeatherReport.java - Scheduler
* WeatherReportingServiceImpl.java - Methods for calculation

## Authors

* **Rishina Valsalan** 


