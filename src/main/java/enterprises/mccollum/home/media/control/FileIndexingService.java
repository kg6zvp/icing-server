package enterprises.mccollum.home.media.control;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;

import static enterprises.mccollum.home.media.control.MediaFileUtils.getMimeTypeByFilePath;

public class FileIndexingService {
	@Inject
	MediaSourceDao mediaSources;
	
	/**
	 * Pass in a mediasource to get a list of media files inside it and their associated mimetypes
	 * 
	 * Output is a map like so: Map<FilePathRelativeToSourceBaseDir, MimeType>
	 * 
	 * @param src The media source to scan for content
	 * @return Map<String, String> key:value pairs of relative file path and associated mime type
	 */
	public Map<String, String> search(MediaSource src) {
		Map<String, String> files = new HashMap<>();
		doSearch(files, src, "");
		return files;
	}
	
	/**
	 * Pass in a mediasource to get a list of media files inside it and their associated mimetypes
	 * 
	 * Output is a map like so: Map<FilePathRelativeToSourceBaseDir, MimeType>
	 * 
	 * @param sourceName The name of the media source to scan for content
	 * @return Map<String, String> key:value pairs of relative file path and associated mime type
	 */
	public Map<String, String> search(String sourceName) {
		return search(mediaSources.getByName(sourceName));
	}

	private String directoryHelper(MediaSource src, String baseDir) {
		return src.getBasePath()+baseDir;
	}
	
	private void doSearch(Map<String, String> files, MediaSource src, String baseDir) {
		Logger.getLogger(getClass().getSimpleName()).log(Level.FINEST,
				String.format("Indexing %s: %s", src.getName(), directoryHelper(src, baseDir)));
		File dir = new File(directoryHelper(src, baseDir));
		File[] filesList = null;
		if(!dir.exists()
				|| dir.isFile()
				|| !dir.canExecute()
				|| (filesList = dir.listFiles()) ==  null){
			if(filesList == null) {
				Logger.getLogger(getClass().getSimpleName()).log(Level.INFO,
					String.format("Bugging out because dir.listFiles() == null"));
			}else if(!dir.exists()) {
				Logger.getLogger(getClass().getSimpleName()).log(Level.INFO,
					String.format("Bugging out because dir.exists() = %b", dir.exists()));
			}else {
				Logger.getLogger(getClass().getSimpleName()).log(Level.INFO,
					String.format("Bugging out because dir.isFile = %b", dir.isFile()));
			}
			return;
		}
		for(File f : filesList){
			if(f.isDirectory()){
				doSearch(files, src, baseDir+"/"+f.getName());
			}else{
				files.put(baseDir+"/"+f.getName(), getMimeTypeByFilePath(f.getName()));
			}
		}
	}
}
