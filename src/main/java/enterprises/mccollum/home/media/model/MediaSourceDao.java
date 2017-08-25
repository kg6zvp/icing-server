package enterprises.mccollum.home.media.model;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import enterprises.mccollum.utils.genericentityejb.GenericPersistenceManager;

@Local
@Stateless
public class MediaSourceDao extends GenericPersistenceManager<MediaSource, Long>{
	public MediaSourceDao(){
		super(MediaSource.class);
	}

	public MediaSource getByName(String sourceName) {
		/*MediaSource src = new MediaSource();
		src.setName(sourceName);
		return getMatching(src).get(0);//*/
		return em.createQuery("SELECT data FROM MediaSource data where data.name = :name", MediaSource.class).setParameter("name", sourceName).getSingleResult();
	}
	
	public long size() {
		Query query = em.createQuery("SELECT count(*) FROM "+tableName);
        return (long) query.getSingleResult();
	}
}
