package playlistOnTheWeather.domain.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playlistOnTheWeather.domain.entities.utils.ArtistsDeserializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

	@JsonIgnore
	private String id;
	@JsonProperty("name")
	private String nameTrack;
	@JsonProperty("artists")
	@JsonDeserialize(using = ArtistsDeserializer.class)
	private List<String> artists;

}
