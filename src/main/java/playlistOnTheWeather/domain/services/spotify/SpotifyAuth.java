package playlistOnTheWeather.domain.services.spotify;

import org.springframework.stereotype.Service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Service
public class SpotifyAuth {
	private static final String clientId = "0520a893e3d0444a939037187009c2df";
	private static final String clientSecret = "d7cde6672460498db1755b3c3d36fd42";

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).build();

	private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

	public String getToken() {
		try {
			final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
			spotifyApi.setAccessToken(clientCredentials.getAccessToken());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return spotifyApi.getAccessToken();

	}
}