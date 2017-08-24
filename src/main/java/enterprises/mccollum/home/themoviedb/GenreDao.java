package enterprises.mccollum.home.themoviedb;

import javax.ejb.Local;
import javax.ejb.Stateless;

import enterprises.mccollum.utils.genericentityejb.GenericPersistenceManager;

@Local
@Stateless
public class GenreDao extends GenericPersistenceManager<Genre, Long>{
	public GenreDao() {
		super(Genre.class);
	}
}
