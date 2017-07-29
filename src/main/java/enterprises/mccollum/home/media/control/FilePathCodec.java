package enterprises.mccollum.home.media.control;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
@Stateless
public class FilePathCodec {
	public static final Charset CODEC_CHARSET = StandardCharsets.UTF_8;
	
	public String encodePath(String path){
		return Base64.getUrlEncoder().withoutPadding().encodeToString(path.getBytes(CODEC_CHARSET));
	}
	
	public String decodePath(String encodedPath){
		return new String(Base64.getUrlDecoder().decode(encodedPath), CODEC_CHARSET);
	}
}
