package playlistOnTheWeather.domain.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import playlistOnTheWeather.domain.entities.City;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private RestTemplate restTemplate;

	private static final String URL_CITY = "http://api.openweathermap.org/data/2.5/weather?q={cityName}&appid=d2c30c8cee84da11f3d500571b6aa4b7";
	private static final String URL_COORD = "http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid=d2c30c8cee84da11f3d500571b6aa4b7";
	private static final String URL_CITY_CO = "http://api.openweathermap.org/data/2.5/weather?q={cityName},{countryCode}&appid=d2c30c8cee84da11f3d500571b6aa4b7";

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public City find(String citySearched) throws IOException {
		
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("cityName", citySearched);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_CITY);
		ResponseEntity<String> response = restTemplate.getForEntity(builder.buildAndExpand(uriParams).toUri(),
				String.class);

		JsonNode cityNode = mapper.readTree(response.getBody());

		City city = new City();
		
		city.setId(cityNode.path("id").asLong());
		city.setName(cityNode.path("name").asText());
		city.setCountry(cityNode.path("sys").path("country").asText());
		city.setTemp(cityNode.path("main").path("temp").asDouble());
		city.setLatitude(cityNode.path("coord").path("lat").asText());
		city.setLongitude(cityNode.path("coord").path("lon").asText());

		return city;
	}

	@Override
	public City find(Float latitude, Float longitude) throws IOException {

		Map<String, Float> uriParams = new HashMap<String, Float>();
		uriParams.put("lat", latitude);
		uriParams.put("lon", longitude);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_COORD);

		ResponseEntity<String> response = restTemplate.getForEntity(builder.buildAndExpand(uriParams).toUri(),
				String.class);

		JsonNode cityNode = mapper.readTree(response.getBody());

		City city = new City();
		
		city.setId(cityNode.path("id").asLong());
		city.setName(cityNode.path("name").asText());
		city.setCountry(cityNode.path("sys").path("country").asText());
		city.setTemp(cityNode.path("main").path("temp").asDouble());
		city.setLatitude(cityNode.path("coord").path("lat").asText());
		city.setLongitude(cityNode.path("coord").path("lon").asText());

		return city;
	}

	@Override
	public City find(String citySearched, String countryCodeSearched) throws IOException {

		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("cityName", citySearched);
		uriParams.put("countryCode", countryCodeSearched);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_CITY_CO);

		ResponseEntity<String> response = restTemplate.getForEntity(builder.buildAndExpand(uriParams).toUri(),
				String.class);

		JsonNode cityNode = mapper.readTree(response.getBody());

		City city = new City();
		
		city.setId(cityNode.path("id").asLong());
		city.setName(cityNode.path("name").asText());
		city.setCountry(cityNode.path("sys").path("country").asText());
		city.setTemp(cityNode.path("main").path("temp").asDouble());
		city.setLatitude(cityNode.path("coord").path("lat").asText());
		city.setLongitude(cityNode.path("coord").path("lon").asText());

		return city;
	}

}
