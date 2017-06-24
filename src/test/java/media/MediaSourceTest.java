package media;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import enterprises.mccollum.home.media.model.MediaSource;

public class MediaSourceTest {
	MediaSource source;
	
	@Before
	public void init(){
		source = new MediaSource();
	}
	
	@Test
	public void runTests(){
		source.setProtocol("sftp");
		source.setUsername("user");
		source.setHost("localhost");
		source.setBasePath("//files//");
		assertEquals("Path is built correctly", source.getCompleteUrl(), "sftp://user@localhost/files");
		
		source.setPassword("password");
		assertEquals("Path is built correctly", source.getCompleteUrl(), "sftp://user:password@localhost/files");
		
		source.setHost("/localhost//");
		assertEquals("Path is built correctly", source.getCompleteUrl(), "sftp://user:password@localhost/files");
	}
}
