package enterprises.mccollum.home.media.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Movie {
	@Id
	Long movieId;
	
	@ManyToOne
	MediaSource source;
	String filePath;
	MediaMetadata metaData;
	
	public Long getId() {
		return movieId;
	}
	public void setId(Long id) {
		this.movieId = id;
	}
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
		this.movieId = metaData.getId();
	}
}
