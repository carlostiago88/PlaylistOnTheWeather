package playlistOnTheWeather.resources;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import playlistOnTheWeather.domain.entities.Playlist;
import playlistOnTheWeather.domain.managers.PlaylistManager;

@RestController
public class PlaylistOnTheWeatherResource {
	
	@Autowired
	private PlaylistManager manager;
	
	@GetMapping("/city/{city}")
	@ResponseBody
	public Playlist playlistByCity(@PathVariable(value = "city") String citySearched) throws Exception {
		HashMap<String,String> params = new HashMap<>();
		params.put("city", citySearched);
		Playlist result = manager.mountPlaylist(params);
		return result;
	}
	@GetMapping("/city/{city}/country-code/{countryCode}")
	@ResponseBody
	public Playlist playlistByCityAndCountry(@PathVariable(value = "city") String citySearched, @PathVariable(value="countryCode") String countryCodeSearched) throws Exception {
		HashMap<String,String> params = new HashMap<>();
		params.put("city", citySearched);
		params.put("countryCode", countryCodeSearched);
		Playlist result = manager.mountPlaylist(params);
		return result;
	}
	@GetMapping("/lat/{lat}/lon/{lon}")
	@ResponseBody
	public Playlist playlistByCoord(@PathVariable(value = "lat") Float latitude, @PathVariable(value="lon") Float longitude) throws Exception {
		HashMap<String,Float> params = new HashMap<>();
		params.put("lat", latitude);
		params.put("lon", longitude);
		Playlist result = manager.mountPlaylist(params);
		return result;
	}
}
