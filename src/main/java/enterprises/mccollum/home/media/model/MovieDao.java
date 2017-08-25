package enterprises.mccollum.home.media.model;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import enterprises.mccollum.utils.genericentityejb.GenericPersistenceManager;

@Local
@Stateless
public class MovieDao extends GenericPersistenceManager<Movie, Long> {
	public MovieDao() {
		super(Movie.class);
	}

	public long size() {
		Query query = em.createQuery("SELECT count(*) FROM "+tableName);
        return (long) query.getSingleResult();
	}
}
