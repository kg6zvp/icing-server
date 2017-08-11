package enterprises.mccollum.home.media.control;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import enterprises.mccollum.home.media.model.MediaMetadata;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;
import enterprises.mccollum.home.media.model.Movie;

public class MovieIndexingService {
	@Inject
	FileIndexingService fileIndexer;
	
	@Inject
	MovieSearchService movieSearch;
	
	@Inject
	MediaSourceDao sources;
	
	/**
	 * Do a movie index of the given {@link MediaSource}
	 * @param src The {@link MediaSource} to index for movies
	 * @return a list of {@link Movie}s
	 */
	public List<Movie> doIndex(MediaSource src){
		Map<String, String> files = fileIndexer.search(src);
		List<Movie> movies = new LinkedList<Movie>();
		for(String filePath : files.keySet()) {
				List<MediaMetadata> results = movieSearch.searchMovies(filePath);
				if(results.size() > 1) {
					Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Found %d results for %s: %s", results.size(), src.getName(), filePath));
				} else if(results.size() < 1) {
					Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Couldn't find any results for %s: %s", src.getName(), filePath));
				} else {
					Movie movie = new Movie();
					movie.setMetaData(results.get(0));
					movie.setFilePath(filePath);
					movie.setSource(src);
					movies.add(movie);
				}
		}
		return movies;
	}

	/**
	 * Do a movie index of the given {@link MediaSource}
	 * @param sourceName The name of the {@link MediaSource} to index for movies
	 * @return a list of {@link Movie}s
	 */
	public List<Movie> doIndex(String sourceName) {
		return doIndex(sources.getByName(sourceName));
	}
}
