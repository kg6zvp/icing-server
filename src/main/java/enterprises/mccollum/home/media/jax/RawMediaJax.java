package enterprises.mccollum.home.media.jax;

import java.io.File;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import enterprises.mccollum.home.media.model.FilePathCodec;

/**
 * Provides raw access to files
 * 
 * @author smccollum
 */
@Path(RawMediaJax.PATH)
public class RawMediaJax {
	public static final String PATH="/raw";
	
	@Inject
	FilePathCodec pathCodec;
	
	@GET
	@Path("{source}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile(@PathParam("source")String source, @HeaderParam("path")String filePathEncoded){
		String filePath = pathCodec.decodePath(filePathEncoded);
		String basePath = System.getProperty("media.filepath.base");
		System.out.println("Getting "+basePath+filePath);
		File file = new File(basePath+filePath);
		return Response.ok(file).build();
	}
}
