package enterprises.mccollum.home.media.control;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import enterprises.mccollum.home.media.model.MediaMetadata;

public class MovieSearchService {
	@Inject
	TheMoviedbAPIClient movieDb;
	
	/**
	 * Finds movie {@link MediaMetadata} for the file path of a movie
	 * @param filePath The file path of a movie
	 * @return a movie's {@link MediaMetadata}
	 */
	public List<MediaMetadata> searchMovie(String filePath){
		List<MediaMetadata> results = movieDb.searchMovies(getName(filePath), getYear(filePath));
		int resultSize = results.size();
		if(resultSize > 1) {
			Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Found %d results for %s", resultSize, filePath));
		} else if(resultSize < 1) {
			Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Couldn't find any results for %s", filePath));
		} 
		return results;
	}

	private Integer getYear(String filePath) {
		String name = getEndOfFilePath(filePath);
		if(containsYear(filePath)) {
			int year = Integer.valueOf(name.substring( name.indexOf("(")+1, name.lastIndexOf(")")));
			return year;
		} else {
			return null;
		}
	}
	private Boolean containsYear(String filePath) {
		String name = getEndOfFilePath(filePath);
		return (name.contains("(") && name.contains(")"));
	}
	private String getName(String filePath) {
		String name = getEndOfFilePath(filePath);
		if(containsYear(filePath)) {
			return name.substring(0, name.lastIndexOf("("));
		} else {
			return name;
		}
	}
	private String getEndOfFilePath(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/"), filePath.lastIndexOf("."));
	}
	
}
