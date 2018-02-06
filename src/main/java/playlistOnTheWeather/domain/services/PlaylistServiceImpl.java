package playlistOnTheWeather.domain.services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import playlistOnTheWeather.domain.entities.Playlist;
import playlistOnTheWeather.domain.entities.Track;
import playlistOnTheWeather.domain.services.spotify.SpotifyAuth;

@Service
public class PlaylistServiceImpl implements PlaylistService {

	@Autowired
	private RestTemplate restTemplate;

	private static final String URL_CATEGORY = "https://api.spotify.com/v1/browse/categories/{category_id}/playlists/";
	private static final String URL_TRACKS = "https://api.spotify.com/v1/users/{user_id}/playlists/{playlist_id}/tracks";
	private static final String TOKEN = new SpotifyAuth().getToken();

	
	private Map<String, String> uriParams = new HashMap<String, String>();
	private HttpHeaders headers = new HttpHeaders();
	private ObjectMapper mapper = new ObjectMapper();
	private HttpEntity<String> entity;

	private static InjectableValues newInjectableValues(String node) {
		return new InjectableValues.Std().addValue("node", node);
	}

	public PlaylistServiceImpl() {
		headers.set("Authorization", "Bearer " + TOKEN);
		entity = new HttpEntity<String>(headers);
	}

	@Override
	public ArrayList<Playlist> findPlaylists(String styleSuggested) throws IOException {

		uriParams.put("category_id", styleSuggested);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_CATEGORY);
		URI uri = builder.buildAndExpand(uriParams).toUri();

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		JsonNode playlistsNode = mapper.readTree(response.getBody());

		if (!playlistsNode.findPath("error").isMissingNode()) {
			throw new IOException(playlistsNode.path("message").asText());
		}

		ArrayList<Playlist> playlists = new ArrayList<>();
		String playlistToString = null;
		for (JsonNode playlist : playlistsNode.path("playlists").path("items")) {
			playlistToString = mapper.writeValueAsString(playlist);
			Playlist temp = mapper.readValue(playlistToString, Playlist.class);
			playlists.add(temp);
		}
		return playlists;
	}

	public List<Track> findTracksByPlaylist(String playlistId) throws IOException {

		uriParams.put("playlist_id", playlistId);
		uriParams.put("user_id", "spotify");
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_TRACKS);
		URI uri = builder.buildAndExpand(uriParams).toUri();

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		JsonNode tracksNode = mapper.readTree(response.getBody());

		if (!tracksNode.findPath("error").isMissingNode()) {
			throw new IOException(tracksNode.path("message").asText());
		}

		List<Track> tracks = new ArrayList<>();
		for (JsonNode playlist : tracksNode.path("items")) {
			String trackToString = mapper.writeValueAsString(playlist.path("track"));
			Track track = mapper.setInjectableValues(newInjectableValues("tracks")).readValue(trackToString,
					Track.class);

			List<String> artistsList = new ArrayList<>();
			for (JsonNode artists : playlist.path("track").path("artists")) {
				artistsList.add(artists.path("name").asText());
			}
			track.setArtists(artistsList);
			tracks.add(track);
		}
		return tracks;
	}
}
