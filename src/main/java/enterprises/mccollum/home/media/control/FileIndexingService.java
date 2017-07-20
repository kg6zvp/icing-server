package enterprises.mccollum.home.media.control;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import enterprises.mccollum.home.media.model.MediaSource;

public class FileIndexingService {
	/**
	 * Pass in a mediasource to get a list of media files inside it and their associated mimetypes
	 * 
	 * Output is a map like so: Map<FilePathRelativeToSourceBaseDir, MimeType>
	 * 
	 * @param src The media source to scan for content
	 * @return Map<String, String> key:value pairs of relative file path and associated mime type
	 */
	public Map<String, String> search(MediaSource src){
		Map<String, String> files = new HashMap<>();
		doSearch(files, src, "/");
		return files;
	}

	private void doSearch(Map<String, String> file, MediaSource src, String baseDir){
		if(src.getProtocol().equals("file")){
			//oops
		}else{
			throw new NotImplementedException("Don't know how to handle "+src.getProtocol()+" procotol");
		}
	}
}
