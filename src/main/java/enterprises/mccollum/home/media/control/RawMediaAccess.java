package enterprises.mccollum.home.media.control;

import java.io.File;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;

@Local
@Stateless
public class RawMediaAccess {
	@Inject
	MediaSourceDao sources;
	
	public File getFile(String source, String path) {
		MediaSource src = sources.getByName(source);
		
		File file = new File(src.getBasePath()+"/"+path);
		if(file.exists())
				return file;
		return null;
	}
}
