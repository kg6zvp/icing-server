package enterprises.mccollum.home.themoviedb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import enterprises.mccollum.home.media.model.MediaMetadata;
import enterprises.mccollum.home.media.model.MediaSearchResultContainer;

public class TheMoviedbAPIClient {
	public static final String API_KEY = "c8b6dbadc2817d249f4c587da01533b7"; //kodi: "6889f6089877fd092454d00edb44a84d"
	
	public List<MediaMetadata> searchMovies(String query, Integer year){
		MediaSearchResultContainer result = null;
		Logger.getLogger("TmDbClient").log(Level.INFO,
				String.format("Search Url: %s", buildSearchUrl(query, year)));
		while(result == null) {
			try {
				result = ClientBuilder.newClient().target(buildSearchUrl(query, year)).request().get(MediaSearchResultContainer.class);
			} catch(ClientErrorException e) {
				Response r = e.getResponse();
				if(r.getStatus() != 429) //we don't know what to do with it
					throw new RuntimeException(e.getCause());
				
				int waitTime = 250; //wait at least 250 milliseconds
				
				/**
				 * Deal with the Retry-After header if it exists
				 */
				String value = r.getHeaderString("Retry-After");
				if(value != null && value.matches("[0-9]*")) {
					waitTime += (1000*Integer.parseInt(value));
				}
				wait(waitTime);
			}
		}
		return result.getResults();
	}

	private String buildSearchUrl(String query, Integer year) {
		StringBuilder sb = new StringBuilder(String.format("http://api.themoviedb.org/3/search/movie?api_key=%s&query=%s", API_KEY, query));
		if(year != null)
			sb.append("&year="+year);
		return sb.toString();
	}
	
	private void wait(int waitTime) {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
