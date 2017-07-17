package enterprises.mccollum.home.media.model;

import javax.ejb.Local;
import javax.ejb.Stateless;

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
}
