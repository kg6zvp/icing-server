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

	@PostConstruct
	public void init(){
		sources = mediaSources.getAll();
	}
	
	public List<MediaSource> getSources(){
		return sources;
	}
	
}
