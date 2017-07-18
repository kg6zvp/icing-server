package enterprises.mccollum.home.media.jsf;

import javax.inject.Inject;
import javax.inject.Named;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
@Stateful(passivationCapable=true)
public class SourceView {
	@Inject
	MediaSourceDao mediaSources;
	
	private List<MediaSource> sources;
	public MediaSource selectedSource;
	
	@PostConstruct
	public void init(){
		System.out.println("Sources have been set up");
		sources = mediaSources.getAll();
	}
	
	public List<MediaSource> getSources(){
		System.out.println("Ran Get Sources");
		return sources;
	}
	
	public void delete(){
		if(selectedSource == null){
			System.out.println("No Deletion");
		} else {
			System.out.println("Deletion"+ selectedSource.getName());;
			mediaSources.remove(selectedSource);
		}
	}

	public MediaSource getSelectedSource() {
		return selectedSource;
	}

	public void setSelectedSource(MediaSource selectedRow) {
		this.selectedSource = selectedRow;
	}
}
