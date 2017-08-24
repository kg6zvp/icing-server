package enterprises.mccollum.home.themoviedb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import enterprises.mccollum.home.media.model.MovieMetadata;
import static org.junit.Assert.*;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TheMovieDbApiClientTest {
	@InjectMocks
	TheMoviedbAPIClient tmDb;
	
	@Before
	public void setupTests() {
		//TODO: Do Stuff
	}
	
	@Test
	public void testSearch() {
		List<MovieMetadata> results = tmDb.searchMovies("Serenity", 2005);
		assertEquals(2, results.size()); //should contain two
		MovieMetadata real = null;
		FIND_REAL_RESULT: for(MovieMetadata result : results) {
			if(result.getId() == 16320) {
				real = result;
				break FIND_REAL_RESULT;
			}
		}
		assertNotNull(real);
	}
	
	@Test
	public void testGenres() {
		final Long ADVENTURE_GENRE_ID = 12L;
		final String ADVENTURE_GENRE_NAME = "Adventure";
		Genre adventureGenre = tmDb.getGenreById(ADVENTURE_GENRE_ID);
		assertNotNull(adventureGenre);
		assertEquals(ADVENTURE_GENRE_ID, adventureGenre.getId());
		assertEquals(ADVENTURE_GENRE_NAME, adventureGenre.getName());
	}
}
