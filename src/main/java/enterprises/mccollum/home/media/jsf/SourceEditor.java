package enterprises.mccollum.home.media.jsf;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;

@Named
@ConversationScoped
@Stateful(passivationCapable=true)
public class SourceEditor {
	@Inject
	MediaSourceDao mediaSources;
	
	MediaSource source;
	
	public SourceEditor(){
		source = new MediaSource();
	}
	
	public String getUrlDisplay(){
		if(source.getPassword() == null){
			return source.getCompleteUrl();
		}else{
			return source.getCompleteUrl().replace(source.getPassword(), getPwdStr(source.getPassword().length()));
		}
	}
	
	private String getPwdStr(int len){
		StringBuilder sb = new StringBuilder(len);
		for(int i = 0; i < len; ++i)
			sb.append('*');
		return sb.toString();
	}
	
	public void save(){
		mediaSources.save(source);
	}

	public MediaSourceDao getMediaSources() {
		return mediaSources;
	}
	public void setMediaSources(MediaSourceDao mediaSources) {
		this.mediaSources = mediaSources;
	}
	public MediaSource getSource() {
		return source;
	}
	public void setSource(MediaSource source) {
		this.source = source;
	}
}
