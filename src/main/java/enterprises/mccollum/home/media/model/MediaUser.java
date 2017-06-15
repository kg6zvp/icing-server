package enterprises.mccollum.home.media.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

//@Entity
@XmlRootElement
public class MediaUser {
	@Id
	Long id;
	
	@OneToMany(mappedBy="owner")
	List<FileLibrary> owned;
	
	/*@ManyToMany
	List<FileLibrary> sharedWithMe;//*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<FileLibrary> getOwned() {
		return owned;
	}

	public void setOwned(List<FileLibrary> owned) {
		this.owned = owned;
	}
}
