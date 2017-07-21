package enterprises.mccollum.home.media.control;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import enterprises.mccollum.home.media.model.MediaSource;
import static enterprises.mccollum.home.media.control.MediaFileUtils.getMimeTypeByFilePath;

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
		doSearch(files, src, "");
		return files;
	}

	private void doSearch(Map<String, String> files, MediaSource src, String baseDir){
		if(src.getProtocol().equals("file")){
			File dir = new File(src.getBasePath()+baseDir);
			if(!dir.exists() || dir.isFile())
				return;
			for(File f : dir.listFiles()){
				if(f.isDirectory()){
					doSearch(files, src, baseDir+f.getName()+"/");
				}else{
					files.put(baseDir+f.getName(), getMimeTypeByFilePath(f.getName()));
				}
			}
		}else{
			throw new NotImplementedException("Don't know how to handle "+src.getProtocol()+" procotol");
		}
	}
}
