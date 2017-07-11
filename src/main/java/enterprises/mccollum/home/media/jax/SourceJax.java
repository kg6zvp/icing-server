package enterprises.mccollum.home.media.jax;

import javax.ws.rs.Path;

import enterprises.mccollum.home.media.model.MediaSource;

@Path("/sources")
public class SourceJax extends CrudJax<MediaSource, Long> {
	@Override
	Long stringToId(String id) {
		return Long.valueOf(id);
	}
}
