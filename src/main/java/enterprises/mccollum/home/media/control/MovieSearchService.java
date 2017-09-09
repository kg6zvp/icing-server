package enterprises.mccollum.home.media.control;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.commons.text.similarity.LevenshteinDistance;

import enterprises.mccollum.home.media.model.MovieMetadata;
import enterprises.mccollum.home.themoviedb.TheMoviedbAPIClient;

public class MovieSearchService {
	@Inject
	TheMoviedbAPIClient movieDb;
	
	LevenshteinDistance lDistance = new LevenshteinDistance();

	public static final String TITLE_YEAR_REGEX = ".* \\([0-9][0-9][0-9][0-9]\\)";
	
	/**
	 * Parse and Search file path of movie in the movieDb
	 * @param filePath the full file path of the movie 
	 * @return List<{@link MovieMetadata} of all result from MovieDb
	 */
	public List<MovieMetadata> searchMovies(String filePath) {
		String fileName = getFileName(filePath);
		String title = getTitle(fileName);
		List<MovieMetadata> results = movieDb.searchMovies(title, getYear(fileName));
		Collections.sort(results, (a, b) ->
				lDistance.apply(title, a.getTitle())
					.compareTo(lDistance.apply(title, b.getTitle())
		));
		
		/*for(MovieMetadata m : results) {
			Logger.getLogger("MovieSearchService").log(Level.WARNING,
			m.getTitle() + ": " + lDistance.apply(title, m.getTitle()));
		}//*/
		return results;
	}

	private String getFileName(String filePath) {
		int fileNameBeginning = filePath.lastIndexOf('/')+1;
		int extensionLocation = filePath.lastIndexOf('.');
		if(extensionLocation > fileNameBeginning) //Check whether file has an extension
			return filePath.substring(fileNameBeginning, extensionLocation);
		return filePath.substring(fileNameBeginning);
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
