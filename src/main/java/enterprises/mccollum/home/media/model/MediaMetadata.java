package enterprises.mccollum.home.media.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaMetadata {

	Integer vote_count;
	Long id;
	Boolean video;
	Integer vote_average;
	String title;
	Double popularity;
	String poster_path;
	String original_language;
	String original_title;
	//List<Integer> genre_ids = null;
	String backdrop_path;
	Boolean adult;
	
	@Lob
	String overview;
	String release_date;
	//Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getVote_count() {
		return vote_count;
	}
	public void setVote_count(Integer voteCount) {
		this.vote_count = voteCount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getVideo() {
		return video;
	}
	public void setVideo(Boolean video) {
		this.video = video;
	}
	public Integer getVote_average() {
		return vote_average;
	}
	public void setVote_average(Integer vote_average) {
		this.vote_average = vote_average;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPopularity() {
		return popularity;
	}
	public void setPopularity(Double popularity) {
		this.popularity = popularity;
	}
	public String getPoster_path() {
		return poster_path;
	}
	public void setPoster_path(String posterPath) {
		this.poster_path = posterPath;
	}
	public String getOriginal_language() {
		return original_language;
	}
	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}
	public String getOriginal_title() {
		return original_title;
	}
	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}
//	public List<Integer> getGenre_ids() {
//		return genre_ids;
//	}
//	public void setGenre_ids(List<Integer> genre_ids) {
//		this.genre_ids = genre_ids;
//	}
	public String getBackdrop_path() {
		return backdrop_path;
	}
	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}
	public Boolean getAdult() {
		return adult;
	}
	public void setAdult(Boolean adult) {
		this.adult = adult;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	
	public String getYear() {
		if(release_date == null)
			return null;
		return release_date.split("-")[0];
	}

//	public Map<String, Object> getAdditionalProperties() {
//		return this.additionalProperties;
//	}
//
//	public void setAdditionalProperty(String name, Object value) {
//		this.additionalProperties.put(name, value);
//	}
}