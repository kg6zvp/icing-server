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
}
