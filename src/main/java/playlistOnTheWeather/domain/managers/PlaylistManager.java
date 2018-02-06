package playlistOnTheWeather.domain.managers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import playlistOnTheWeather.domain.entities.City;
import playlistOnTheWeather.domain.entities.Playlist;
import playlistOnTheWeather.domain.entities.Track;
import playlistOnTheWeather.domain.managers.utils.TemperatureConverter;
import playlistOnTheWeather.domain.services.CityServiceImpl;
import playlistOnTheWeather.domain.services.PlaylistServiceImpl;

@Service
public class PlaylistManager {

	@Autowired
	private CityServiceImpl cityService;

	@Autowired
	private PlaylistServiceImpl playlistService;

	public Playlist mountPlaylist(HashMap<String, ?> params) throws IOException {

		City city = null;
		if (params.containsKey("lat") && params.containsKey("lon")) {
			city = cityService.find((Float) params.get("lat"), (Float) params.get("lon"));
		} else if (params.containsKey("countryCode")) {
			city = cityService.find(params.get("city").toString(), params.get("countryCode").toString());
		} else if (params.containsKey("city")) {
			city = cityService.find(params.get("city").toString());
		}else {
			throw new IOException();
		}

		Double temperature = TemperatureConverter.KelvinToCelsius(city.getTemp());
		city.setTemp(temperature);
		String styleSuggested = "";
		
		if (temperature > 30) {
			styleSuggested = "party";
		} else if (temperature >= 15 && temperature < 30) {
			styleSuggested = "pop";
		} else if (temperature >= 10 && temperature < 15) {
			styleSuggested = "rock";
		} else {
			styleSuggested = "classical";
		}

		List<Playlist> playlists = playlistService.findPlaylists(styleSuggested);

		Collections.shuffle(playlists);

		Playlist playlistSelected = playlists.get(0);
		List<Track> tracks = playlistService.findTracksByPlaylist(playlistSelected.getId());

		playlistSelected.setTracks(tracks);
		playlistSelected.setCity(city);
		return playlistSelected;
	}

}