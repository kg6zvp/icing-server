package enterprises.mccollum.home.media.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Movie {
	MediaSource source;
	String filePath;
	MediaMetadata metaData;
	
	public MediaSource getSource() {
		return source;
	}
	public void setSource(MediaSource src) {
		this.source = src;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getEncodedFilePath() {
		try {
			return URLEncoder.encode(filePath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "oops";
	}
	public void setFilePath(String relPath) {
		this.filePath = relPath;
	}
	public MediaMetadata getMetaData() {
		return metaData;
	}
	public void setMetaData(MediaMetadata data) {
		this.metaData = data;
	}
}
