package enterprises.mccollum.home.media.jax;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import enterprises.mccollum.home.media.model.FilePathCodec;

@Provider
@PreMatching
public class MediaRequestFilter implements ContainerRequestFilter {
	@Inject
	FilePathCodec pathCodec;

	@Override
	public void filter(ContainerRequestContext reCtx) throws IOException {
		String path = reCtx.getUriInfo().getPath();
		if(!path.startsWith(RawMediaJax.PATH))
			return;
		String baseUrl = path.substring(0, path.indexOf("/", RawMediaJax.PATH.length()+1));
		String realPath = path.substring(baseUrl.length()+1);
		String encodedPath = pathCodec.encodePath(realPath);
		try {
			reCtx.setRequestUri(new URI(baseUrl+"/"+encodedPath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
