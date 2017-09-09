package enterprises.mccollum.home.media.jsf;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import enterprises.mccollum.home.media.model.MovieDao;
import enterprises.mccollum.home.media.model.MovieFile;
import enterprises.mccollum.home.media.model.MovieMetadata;

@Named
@RequestScoped
public class MovieView {
	@Inject
	MovieDao movies;
	
	public String getTitle() {
		MovieMetadata mData = getMovie().getMetaData();
		return mData.getTitle() + " (" + mData.getYear() + ")";
	}
	
	public String getId() {
		return getMovieId().toString();
	}
	
	public String getMimeType() {
		return getMovie().getMimeType();
	}
	
	public String getUrl() {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath()
				+ "/api/raw/"
				+ getMovie().getSource().getName()
				+ getMovie().getFilePath();
	}
	
	private MovieFile getMovie() {
		if(movies.get(getMovieId()) == null) {
			for(MovieFile m : movies.getAll()) {
				System.out.println(m.getId());
			}
		}
		return movies.get(getMovieId());
	}
	
	private Long getMovieId() {
		return Long.valueOf(FacesUtils.getParam("movie"));
	}
}
