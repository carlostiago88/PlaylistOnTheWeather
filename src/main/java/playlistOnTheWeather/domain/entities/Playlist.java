package playlistOnTheWeather.domain.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playlistOnTheWeather.domain.entities.utils.TracksDeserializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {

	@JsonProperty("city")
	private City city;
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("tracks")
	@JsonDeserialize(using = TracksDeserializer.class)
	private List<Track> tracks;

}
