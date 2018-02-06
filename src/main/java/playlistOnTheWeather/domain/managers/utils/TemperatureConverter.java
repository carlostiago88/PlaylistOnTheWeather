package playlistOnTheWeather.domain.managers.utils;

public class TemperatureConverter {

	public static Double FahrenheitToCelsius(Double temp) {
		return (temp - 32) / 1.8;
	}
	
	public static Double KelvinToCelsius(Double temp) {
		return temp - 273.15;
	}
}
