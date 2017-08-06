package enterprises.mccollum.home.media.jsf;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import enterprises.mccollum.home.media.control.MovieIndexingService;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSource.Type;
import enterprises.mccollum.home.media.model.MediaSourceDao;
import enterprises.mccollum.home.media.model.Movie;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
@Stateful(passivationCapable=true)
public class MovieIndexView {
	@Inject
	MovieIndexingService movieService;
	
	@Inject
	MediaSourceDao sources;
	
	List<Movie> movies;
	
	@PostConstruct
	public void init() {
		movies = new LinkedList<Movie>();
		for(MediaSource src : sources.getAll()) {
			if(src.getType() == Type.MOVIES)
				movies.addAll(movieService.doIndex(src));
		}
	}

	public List<Movie> getMoviesList(){
		return movies;
	}
	public MovieIndexingService getMovieService() {
		return movieService;
	}
	public void setMovieService(MovieIndexingService movieService) {
		this.movieService = movieService;
	}
}
