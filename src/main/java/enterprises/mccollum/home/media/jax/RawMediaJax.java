package enterprises.mccollum.home.media.jax;

import java.io.File;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import enterprises.mccollum.home.media.control.FilePathCodec;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;
import static enterprises.mccollum.home.media.control.MediaFileUtils.getMimeTypeByFilePath;

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
	
	@Inject
	MediaSourceDao sources;
	
	@HeaderParam("Range")
	String rangeHeader;
	
	@HEAD
	@Path("{source}/{path}")
	public Response getHead(@PathParam("source")String sourceName, @PathParam("path")String filePathEncoded){
		String filePath = pathCodec.decodePath(filePathEncoded);
		
		MediaSource src = sources.getByName(sourceName);
		
		ResponseBuilder builder = Response.noContent();
		return builder.header("Content-Type", getMimeTypeByFilePath(filePath))
					.header("Accept-Ranges", "bytes")
					.build();
	}
	
	@GET
	@Path("{source}/{path}")
	public Response getFile(@PathParam("source")String sourceName, @PathParam("path")String filePathEncoded){
		String filePath = pathCodec.decodePath(filePathEncoded);
		
		MediaSource src = sources.getByName(sourceName);
		
		File f = new File(src.getBasePath()+"/"+filePath);
		if(!f.exists())
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(f)
					.header("Content-Length", f.length()-parseRangeIgnoreEnd())
					.header("Content-Type", getMimeTypeByFilePath(filePath))
					.header("Accept-Ranges", "bytes")
					.build();
	}
	
	private long parseRangeIgnoreEnd(){
		if(rangeHeader == null)
			return 0L;
		String range[] = rangeHeader.split("=")[1].split("-");
		return Long.parseLong(range[0]);
	}
}
