package enterprises.mccollum.home.media.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class MovieFile {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long movieId;
	
	@ManyToOne
	MediaSource source;
	String filePath;
	
	String mimeType;
	
	MovieMetadata metaData;
	
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
	public MovieMetadata getMetaData() {
		return metaData;
	}
	public void setMetaData(MovieMetadata data) {
		this.metaData = data;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getMimeType() {
		return mimeType;
	}
}
