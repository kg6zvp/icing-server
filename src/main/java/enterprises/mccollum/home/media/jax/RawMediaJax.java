package enterprises.mccollum.home.media.jax;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.RandomAccessContent;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.util.RandomAccessMode;

import com.github.amr.mimetypes.MimeTypes;

import enterprises.mccollum.home.media.model.FilePathCodec;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;

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
		
		String fileExtention = filePath.substring(filePath.lastIndexOf(".")+1);

		ResponseBuilder builder = Response.noContent();
		return builder.header("Content-Type", MimeTypes.getInstance().getByExtension(fileExtention).getMimeType())
					.header("Accept-Ranges", "bytes")
					.build();
	}
	
	@GET
	@Path("{source}/{path}")
	public Response getFile(@PathParam("source")String sourceName, @PathParam("path")String filePathEncoded){
		String filePath = pathCodec.decodePath(filePathEncoded);
		
		MediaSource src = sources.getByName(sourceName);
		
		String fileExtention = filePath.substring(filePath.lastIndexOf(".")+1);
		
		try {
			ResponseBuilder builder = null;
			if(src.getProtocol().equals("file")){
				File f = new File(src.getBasePath()+"/"+filePath);
				builder = Response.ok(f)
								.header("Content-Length", f.length()-parseRangeIgnoreEnd());
			}else{
				FileContent file = VFS.getManager().resolveFile(src.getCompleteUrl()+"/"+filePath).getContent();
				RandomAccessContent fContents = file.getRandomAccessContent(RandomAccessMode.READ);
				fContents.seek(parseRangeIgnoreEnd());
				builder = Response.ok(fContents.getInputStream())
								.header("Content-Length", file.getSize()-parseRangeIgnoreEnd());
			}
			return builder.header("Content-Type", MimeTypes.getInstance().getByExtension(fileExtention).getMimeType())
						.header("Accept-Ranges", "bytes")
						.build();
		} catch (FileSystemException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	private long parseRangeIgnoreEnd(){
		if(rangeHeader == null)
			return 0L;
		String range[] = rangeHeader.split("=")[1].split("-");
		return Long.parseLong(range[0]);
	}
}
