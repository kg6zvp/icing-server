package enterprises.mccollum.home.media.control;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;

import enterprises.mccollum.home.media.model.MediaMetadata;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;
import enterprises.mccollum.home.media.model.Movie;
import enterprises.mccollum.home.media.model.MovieDao;

public class MovieIndexingService {
	@Inject
	FileIndexingService fileIndexer;
	
	@Inject
	MovieSearchService movieSearch;
	
	@Inject
	MediaSourceDao sources;
	
	@Inject
	MovieDao movies;
	
	/**
	 * Do a movie index of the given {@link MediaSource}
	 * @param src The {@link MediaSource} to index for movies
	 * @return a list of {@link Movie}s
	 */
	@Transactional
	public void doIndex(MediaSource src){
		Map<String, String> files = fileIndexer.search(src);
		
		//Clear non-existent files from db
		for(int i = 0; i < src.getMovies().size(); ++i) {
			if(!files.containsKey(src.getMovies().get(i).getFilePath())) {
				movies.remove(src.getMovies().get(i));
				src.getMovies().remove(i);
			}
		}
		
		//Exclude already added movies
		for(Movie m : src.getMovies())
			files.remove(m.getFilePath());
		
		for(String filePath : files.keySet()) {
			if(files.get(filePath).startsWith("video/")) {
				List<MediaMetadata> results = movieSearch.searchMovies(filePath);
				if(results.size() < 1) {
					Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Couldn't find any results for %s: %s", src.getName(), filePath));
				} else if(results.size() > 1) {
					Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Found %d results for %s: %s", results.size(), src.getName(), filePath));
				} else {
					Movie movie = new Movie();
					movie.setMetaData(results.get(0));
					movie.setFilePath(filePath);
					movie = movies.persist(movie);
					movie.setSource(src);
					movie = movies.save(movie);
					src.getMovies().add(movie);
					sources.save(src);
				}
			}
		}
	}

	/**
	 * Do a movie index of the given {@link MediaSource}
	 * @param sourceName The name of the {@link MediaSource} to index for movies
	 * @return a list of {@link Movie}s
	 */
	public void doIndex(String sourceName) {
		doIndex(sources.getByName(sourceName));
	}
}
