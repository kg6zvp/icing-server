package enterprises.mccollum.home.media.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Intended to represent a location on the filesystem containing media
 * 
 * @author smccollum
 */
//@Entity
@XmlRootElement
public abstract class FileLibrary {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	String name;
	
	@ManyToOne
	@JoinColumn(name="id")
	MediaUser owner;
	
	/*@ManyToMany
	List<MediaUser> guests;//*/
	
	/**
	 * Filesystem path or url of some type
	 */
	String url;
	
	/**
	 * optional
	 */
	String username;
	String password;
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
	public MediaUser getOwner() {
		return owner;
	}
	public void setOwner(MediaUser owner) {
		this.owner = owner;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSchema(){
		if(url.startsWith("/"))
			return null;
		return url.split("://")[0];
	}
}
