package enterprises.mccollum.home.media.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Intended to represent a location on the filesystem containing media
 * 
 * @author smccollum
 */
@Entity
@XmlRootElement
public class MediaSource {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	String name;
	
	Long ownerId;
	
	String protocol;
	Integer port;
	String host;
	String username;
	String password;
	String basePath;
	
	public static enum Type{
		MOVIES,
		TV_SHOWS
	};
	
	Type type;
	
	public MediaSource(){}
	
	/**
	 * Get url suitable for use by apache commons vfs
	 * @return
	 */
	public String getCompleteUrl(){
		StringBuilder sb = new StringBuilder(str(protocol) ? protocol+"://" : "");
		if(str(username)){
			sb.append(username);
			if(str(password)){
				sb.append(":"+password);
			}
			sb.append('@');
		}
		if(str(host)){
			sb.append(host);
			if(port != null)
				sb.append(":"+port);
		}
		if(str(basePath)){
			sb.append(basePath);
		}
		return sb.toString();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwner(MediaUser owner) {
		this.ownerId = owner.getId();
	}
	public void setOwnerId(Long ownerId){
		this.ownerId = ownerId;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		if(host.startsWith("/")){
			setHost(host.substring(1, host.length()));
		}else if(host.endsWith("/")){
			setHost(host.substring(0, host.length()-1));
		}else{
			this.host = host;
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		if(basePath.endsWith("/")){
			//logf("Called set base path with ending slash: %s", basePath);
			basePath = basePath.substring(0, basePath.length()-1);
			setBasePath(basePath);
		}else if(basePath.startsWith("//")){
			//logf("Called set base path with extra beginning: %s", basePath);
			basePath = basePath.substring(1);
			setBasePath(basePath);
		}else{
			if(!basePath.startsWith("/"))
				basePath = "/"+basePath;
			//logf("Setting base path: %s", basePath);
			this.basePath = basePath;
		}
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}

	@SuppressWarnings("unused")
	private void logf(String format, Object...args) {
		Logger.getLogger(MediaSource.class.getSimpleName()).log(Level.INFO, String.format(format, args));
	}
	
	boolean str(String str){
		return (str != null && str.length() > 0);
	}
}
