package enterprises.mccollum.home.media.control;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;

import enterprises.mccollum.home.media.model.MovieMetadata;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;
import enterprises.mccollum.home.media.model.MovieFile;
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
	 * @return a list of {@link MovieFile}s
	 */
	@Transactional
	public void doIndex(MediaSource src){
		Map<String, String> files = fileIndexer.search(src);
		
		//Clear non-existent files from db
		for(int i = 0; i < src.getMovies().size(); ++i) {
			if(!files.containsKey(src.getMovies().get(i).getFilePath())) {
				movies.remove(src.getMovies().get(i));
				src.getMovies().remove(i);
				--i;
			}
		}
		
		//Exclude already added movies
		for(MovieFile m : src.getMovies())
			files.remove(m.getFilePath());
		
		for(String filePath : files.keySet()) {
			if(files.get(filePath).startsWith("video/")) {
				List<MovieMetadata> results = movieSearch.searchMovies(filePath);
				if(results.size() < 1) {
					Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Couldn't find any results for %s: %s", src.getName(), filePath));
				} else {
					Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Found %d results for %s: %s", results.size(), src.getName(), filePath));
					MovieFile movieFile = new MovieFile();
					searchResults: for(MovieMetadata currentMetadata : results) {
						if((currentMetadata.getTitle() != null
								&& currentMetadata.getPoster_path() != null
								&& currentMetadata.getOverview() != null) || results.size() == 1) {
							movieFile.setMetaData(currentMetadata);
							break searchResults;
						}
					}
					movieFile.setFilePath(filePath);
					movieFile.setMimeType(files.get(filePath));
					
					movieFile = movies.persist(movieFile);
					movieFile.setSource(src);
					movieFile = movies.save(movieFile);
					src.getMovies().add(movieFile);
					sources.save(src);
				}
			}
		}
	}

	/**
	 * Do a movie index of the given {@link MediaSource}
	 * @param sourceName The name of the {@link MediaSource} to index for movies
	 * @return a list of {@link MovieFile}s
	 */
	public void doIndex(String sourceName) {
		doIndex(sources.getByName(sourceName));
	}
}
