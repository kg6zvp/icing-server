package enterprises.mccollum.home.media.model;

import javax.ejb.Local;
import javax.ejb.Stateless;

import enterprises.mccollum.utils.genericentityejb.GenericPersistenceManager;

@Local
@Stateless
public class MovieDao extends GenericPersistenceManager<MovieFile, Long> {
	public MovieDao() {
		super(MovieFile.class);
	}
}
