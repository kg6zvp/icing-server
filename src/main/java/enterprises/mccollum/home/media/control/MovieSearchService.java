package enterprises.mccollum.home.media.control;

import java.util.List;

import javax.inject.Inject;

import enterprises.mccollum.home.media.model.MediaMetadata;

public class MovieSearchService {
	@Inject
	TheMoviedbAPIClient movieDb;
	
	private String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf('/'), filePath.lastIndexOf('.'));
	}

	public List<MediaMetadata> searchMovies(String filePath) {
		return movieDb.searchMovies(getFileName(filePath), null);
	}
}
