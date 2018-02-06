package playlistOnTheWeather.domain.services;

import java.io.IOException;

import playlistOnTheWeather.domain.entities.City;

public interface CityService {

	public City find(String nameCity) throws IOException;
	public City find(String nameCity, String countryCode) throws IOException ;
	public City find(Float lat, Float lon) throws IOException ;

}

