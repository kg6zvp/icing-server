package enterprises.mccollum.home.themoviedb;

import java.io.StringReader;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import enterprises.mccollum.home.media.model.MovieMetadata;
import enterprises.mccollum.home.media.model.MediaSearchResultContainer;

public class TheMoviedbAPIClient {
	public static final String API_KEY = "c8b6dbadc2817d249f4c587da01533b7"; //kodi: "6889f6089877fd092454d00edb44a84d"
	public static final String MOVIE_GENRE_URL = "https://api.themoviedb.org/3/genre/movie/list?api_key="+API_KEY+"&language=en-US";
	public static final String TV_GENRE_URL = "https://api.themoviedb.org/3/genre/tv/list?api_key="+API_KEY+"&language=en-US";
	
	public List<MovieMetadata> searchMovies(String query, Integer year){
		MediaSearchResultContainer result = null;
		Logger.getLogger("TmDbClient").log(Level.INFO,
				String.format("Search Url: %s", buildSearchUrl(query, year)));
		result = makeMovieDbRequest(() -> {
			return ClientBuilder.newClient().target(buildSearchUrl(query, year)).request().get(MediaSearchResultContainer.class);
		});
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
	
	Genre getGenreById(Long id) {
		JsonObject movieGenres = makeMovieDbRequest(() -> {
			String json = ClientBuilder.newClient().target(MOVIE_GENRE_URL).request().get(String.class);
			try(JsonReader reader = Json.createReader(new StringReader(json))) {
				return reader.readObject();
			}
		});
		JsonArray genres = movieGenres.getJsonArray("genres");
		for(int i = 0; i < genres.size(); ++i) {
			JsonObject genreJson = genres.getJsonObject(i);
			Genre genre = new Genre(genreJson);
			if(genre.id == id)
				return genre;
		}
		return null;
	}
	
	/**
	 * Handles requests to TheMovieDb in a way that allows the consumer of this function not to worry about rate limits
	 * @param requestLambda The lambda that actually performs the request
	 * @return
	 */
	private <T> T makeMovieDbRequest(Supplier<T> requestLambda) {
		T result = null;
		while(result == null) {
			try {
				result = requestLambda.get();
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
		return result;
	}
}
