package enterprises.mccollum.home.media.jax;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class MediaRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext reCtx) throws IOException {
		String path = reCtx.getUriInfo().getPath();
		System.out.println("path: "+path);
		if(!path.startsWith(RawMedia.PATH))
			return;
		String realPath = path.substring(RawMedia.PATH.length());
		String encodedPath = Base64.getUrlEncoder().withoutPadding().encodeToString(realPath.getBytes(StandardCharsets.UTF_8));
		try {
			reCtx.setRequestUri(new URI(RawMedia.PATH));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		reCtx.getHeaders().add("path", encodedPath);
	}

}
