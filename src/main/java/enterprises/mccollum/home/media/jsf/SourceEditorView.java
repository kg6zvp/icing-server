package enterprises.mccollum.home.media.jsf;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;

@Named
@RequestScoped
@Stateful(passivationCapable=true)
public class SourceEditorView {
	@Inject
	MediaSourceDao mediaSources;
	
	MediaSource source;
	final Map<String, MediaSource.Type> sourceTypes;
	
	public SourceEditorView(){
		sourceTypes = new HashMap<>();
		sourceTypes.put("Movies", MediaSource.Type.MOVIES);
		sourceTypes.put("TV Shows", MediaSource.Type.TV_SHOWS);
	}
	
	@PostConstruct
	public void init(){
		String nameParam = getParam("name");
		System.out.println(String.format("Name: %s", nameParam));
		if(nameParam != null){
			source = mediaSources.getByName(nameParam);
		}else{
			source = new MediaSource();
		}
	}
	
	public String getSourceName(){
		if(source.getName() == null || source.getName().length() < 1){
			return "New Source";
		}
		return source.getName();
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
	
	public Map<String, MediaSource.Type> getSourceTypes(){
		return sourceTypes;
	}

	private String getParam(String param){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(param); /*.getSession(true)).getAttribute(param);*/
	}
}
