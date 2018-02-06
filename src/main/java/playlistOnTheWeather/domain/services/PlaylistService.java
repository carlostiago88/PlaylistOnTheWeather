package playlistOnTheWeather.domain.services;

import java.io.IOException;
import java.util.List;

import playlistOnTheWeather.domain.entities.Playlist;
import playlistOnTheWeather.domain.entities.Track;

public interface PlaylistService {

	public List<Playlist> findPlaylists(String styleSuggested) throws IOException;
	public List<Track> findTracksByPlaylist(String playlistId) throws IOException;
}
