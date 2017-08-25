package enterprises.mccollum.home.media.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import enterprises.mccollum.home.media.model.MediaSourceDao;
import enterprises.mccollum.home.media.model.MovieDao;

@Named
@RequestScoped
public class DashboardView {
	@Inject
	MovieDao movies;
	
	@Inject
	MediaSourceDao sources;

	public MovieDao getMovies() {
		return movies;
	}
	public void setMovies(MovieDao movies) {
		this.movies = movies;
	}
	public MediaSourceDao getSources() {
		return sources;
	}
	public void setSources(MediaSourceDao sources) {
		this.sources = sources;
	}
}
