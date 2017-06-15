package enterprises.mccollum.home.media.jax;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(RawMedia.PATH)
public class RawMedia {
	public static final String PATH="/raw";
	
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile(@HeaderParam("path")String filePathEncoded){
		String filePath = new String(Base64.getUrlDecoder().decode(filePathEncoded), StandardCharsets.UTF_8);
		String basePath = System.getProperty("media.filepath.base");
		System.out.println("Getting "+basePath+filePath);
		File file = new File(basePath+filePath);
		return Response.ok(file).build();
	}
}
