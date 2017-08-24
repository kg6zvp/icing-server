package enterprises.mccollum.home.themoviedb;

import java.util.List;

import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import enterprises.mccollum.home.media.model.MovieMetadata;

@Entity
@XmlRootElement
public class Genre {
	@Id
	Long id;
	
	String name;
	
//	@ManyToMany
//	@JoinTable
//	List<MovieMetadata> movies;
	
	public Genre(){}
	
	public Genre(JsonObject jsonGenre){
		this.id = (long)jsonGenre.getInt("id");
		this.name = jsonGenre.getString("name");
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public List<MovieMetadata> getMovies() {
//		return movies;
//	}
//	public void setMovies(List<MovieMetadata> movies) {
//		this.movies = movies;
//	}
}
