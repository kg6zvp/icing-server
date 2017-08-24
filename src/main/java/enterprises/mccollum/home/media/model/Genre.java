package enterprises.mccollum.home.media.model;

import javax.json.JsonObject;

public class Genre {
	
	int id;
	String name;
	
	public Genre(){}
	
	public Genre(JsonObject jsonGenre){
		this.id = jsonGenre.getInt("id");
		this.name = jsonGenre.getString("name");
	}
}
