package enterprises.mccollum.home.media.control;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;

import enterprises.mccollum.home.media.model.MediaMetadata;
import enterprises.mccollum.home.media.model.MediaSearchResultContainer;

public class TheMoviedbAPIClient {
	public static final String API_KEY = "c8b6dbadc2817d249f4c587da01533b7";
	
	public List<MediaMetadata> searchMovies(String query, Integer year){
		MediaSearchResultContainer result = ClientBuilder.newClient().target(buildSearchUrl(query, year)).request().get(MediaSearchResultContainer.class);
		return result.getResults();
	}

	private String buildSearchUrl(String query, Integer year) {
		StringBuilder sb = new StringBuilder(String.format("http://api.themoviedb.org/3/search/movie?api_key=%s&query=%s", API_KEY, query));
		if(year != null)
			sb.append("&year="+year);
		return sb.toString();
	}
}
