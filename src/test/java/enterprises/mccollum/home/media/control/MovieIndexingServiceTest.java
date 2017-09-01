package enterprises.mccollum.home.media.control;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import enterprises.mccollum.home.media.control.MovieIndexingService;
import enterprises.mccollum.home.media.model.MovieMetadata;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSourceDao;
import enterprises.mccollum.home.media.model.MovieFile;
import enterprises.mccollum.home.media.model.MovieDao;

import static enterprises.mccollum.utils.genericentityejb.DaoMocker.mockPersistenceManager;

@RunWith(MockitoJUnitRunner.class)
public class MovieIndexingServiceTest {
	@Mock
	FileIndexingService fileIndexer;
	
	@Mock
	MovieSearchService movieSearch;
	
	@Mock
	MediaSourceDao sources;
	
	@Mock
	MovieDao movies;
	
	@InjectMocks
	MovieIndexingService movieIndexingSvc;
	
	@Captor
	ArgumentCaptor<MovieFile> movieCaptor;
	
	@Before
	public void setupTests() {
		mockPersistenceManager(movies, MovieFile.class);
	}
	
	/**
	 * Test the decision-making process for resolving multiple results for a movie
	 */
	@Test
	public void testResolveMultipleResults() {
		MediaSource source = new MediaSource();
		source.setName("a");
		
		Map<String, String> files = new HashMap<>();
		files.put("favorite movie ever.mkv", "video/x-matroska");
		
		when(movieIndexingSvc.fileIndexer.search(any(MediaSource.class))).thenReturn(files);
		
		MovieMetadata a = new MovieMetadata();
		a.setId(0L);
		a.setTitle("cool story");
		a.setOverview("and they all lived happily ever after");
		a.setPoster_path("480p.jpg");
		MovieMetadata b = new MovieMetadata();
		b.setId(1L);
		b.setTitle(null);
		b.setOverview(null);
		b.setPoster_path(null);

		List<MovieMetadata> searchResults = new LinkedList<>();
		searchResults.add(a);
		searchResults.add(b);
		
		when(movieIndexingSvc.movieSearch.searchMovies("favorite movie ever.mkv")).thenReturn(searchResults);
		
		
		
		movieIndexingSvc.doIndex(source);
		
		
		
		verify(movieIndexingSvc.movies).persist(movieCaptor.capture());
		
		assertEquals(a.getId(), movieCaptor.getValue().getMetaData().getId());
		assertSame("Expected mime type is the same", "video/x-matroska",
				movieCaptor.getValue().getMimeType());
		assertSame("Expected file paty is the same", "favorite movie ever.mkv",
				movieCaptor.getValue().getFilePath());
		assertSame("a", movieCaptor.getValue().getSource().getName());
	}
}
