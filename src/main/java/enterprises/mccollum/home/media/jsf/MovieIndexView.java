package enterprises.mccollum.home.media.jsf;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import enterprises.mccollum.home.media.control.MovieIndexingService;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSource.Type;
import enterprises.mccollum.home.media.model.MediaSourceDao;
import enterprises.mccollum.home.media.model.MovieFile;

import java.util.LinkedList;
import java.util.List;

@Named
@RequestScoped
@Stateful(passivationCapable=true)
public class MovieIndexView {
	@Inject
	MovieIndexingService movieIndexingService;
	
	@Inject
	MediaSourceDao sources;
	
	List<MovieFile> movies;

	public List<MovieFile> getMoviesList(){
		if(movies == null) {
			movies = new LinkedList<MovieFile>();
			for(MediaSource src : sources.getAll()) {
				if(src.getType() == Type.MOVIES)
					movies.addAll(src.getMovies());
			}
		}
		return movies;
	}
	
	public void reIndex() {
		for(MediaSource src : sources.getAll()) {
			if(src.getType() == Type.MOVIES)
				movieIndexingService.doIndex(src);
		}
	}
}
