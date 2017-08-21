package enterprises.mccollum.home.themoviedb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import enterprises.mccollum.home.media.model.MediaMetadata;
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
		List<MediaMetadata> results = tmDb.searchMovies("Serenity", 2005);
		assertEquals(2, results.size()); //should contain two
		MediaMetadata real = null;
		FIND_REAL_RESULT: for(MediaMetadata result : results) {
			if(result.getId() == 16320) {
				real = result;
				break FIND_REAL_RESULT;
			}
		}
		assertNotNull(real);
	}
}
