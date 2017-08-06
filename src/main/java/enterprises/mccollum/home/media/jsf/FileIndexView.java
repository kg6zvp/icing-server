package enterprises.mccollum.home.media.jsf;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import enterprises.mccollum.home.media.control.FileIndexingService;

import static enterprises.mccollum.home.media.jsf.FacesUtils.getParam;

@Named
//@ConversationScoped
@RequestScoped
@Stateful(passivationCapable=true)
public class FileIndexView {
	@Inject 
	FileIndexingService fileService;
	
	String sourceName;
	
	@PostConstruct
	public void init(){
		sourceName = getParam("source");
	}
	
	/**
	 * 
	 * 
	 * @return a list of relative paths to the media files which can be used to generate links
	 */
	public List<MediaData> getMediaFileList(){
		Map<String, String> foundFiles = fileService.search(sourceName);
		List<MediaData> videoFiles = new LinkedList<MediaData>();
		for(Map.Entry<String, String> file : foundFiles.entrySet()){
			if(file.getValue().startsWith("video/")) {
				try {
				videoFiles.add(new MediaData(file.getKey(), sourceName));
				} catch (UnsupportedEncodingException e) {
					Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, String.format("Failed while URL encoding %s in %s", file.getKey(), sourceName));
//					e.printStackTrace();
				}
			}
		}
		return videoFiles;
	}
	
	public String getSourceName(){
		return sourceName;
	}
	
	
	public static class MediaData{
		private final String filePath;
		private final String fileLink;
		
		public MediaData(String filePath, String sourceName) throws UnsupportedEncodingException{
			this.filePath = filePath;
			this.fileLink = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath()+"/web/raw/"+sourceName+"?filePath="+
			URLEncoder.encode(filePath, "UTF-8");
		}
		
		public String getFilePath(){
			return filePath;
		}
		
		public String getFileLink(){
			return fileLink;
		}
		
		public String getFileName(){
			return filePath.substring(filePath.lastIndexOf('/')+1);
		}
	}
}
