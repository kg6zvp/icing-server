package enterprises.mccollum.home.media.jax;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import enterprises.mccollum.home.media.model.MovieFile;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MovieJax extends CrudJax<MovieFile, Long> {
	@Override
	Long stringToId(String id) {
		return Long.valueOf(id);
	}
}
