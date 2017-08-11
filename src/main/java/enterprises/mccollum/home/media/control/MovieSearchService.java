package enterprises.mccollum.home.media.control;

import java.util.List;

import javax.inject.Inject;

import enterprises.mccollum.home.media.model.MediaMetadata;

public class MovieSearchService {
	@Inject
	TheMoviedbAPIClient movieDb;

	public static final String TITLE_YEAR_REGEX = ".* \\([0-9][0-9][0-9][0-9]\\)";
	
	/**
	 * Parse and Search file path of movie in the movieDb
	 * @param filePath the full file path of the movie 
	 * @return List<{@link MediaMetadata} of all result from MovieDb
	 */
	public List<MediaMetadata> searchMovies(String filePath) {
		String fileName = getFileName(filePath);
		return movieDb.searchMovies(getTitle(fileName), getYear(fileName));
	}

	private String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf('/')+1, filePath.lastIndexOf('.'));
	}
	
	private String getTitle(String fileName) {
		if(hasYear(fileName)) {
			return fileName.substring(0, fileName.lastIndexOf('(')).trim();
		} else {
			return fileName;
		}
	}

	private Integer getYear(String fileName) {
		if(hasYear(fileName)){
			String title = getTitle(fileName);
			Integer year = Integer.valueOf(fileName.substring(fileName.indexOf('(', title.length())+1, fileName.lastIndexOf(')')));
			return year;
		} else {
			return null;
		}
	}

	private boolean hasYear(String fileName) {
		return fileName.matches(TITLE_YEAR_REGEX);
	}
	
}
