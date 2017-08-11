package media;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import enterprises.mccollum.home.media.control.MovieSearchService;
import enterprises.mccollum.home.media.control.TheMoviedbAPIClient;

@RunWith(MockitoJUnitRunner.class)
public class MovieSearchServiceTest {
	@Mock
	TheMoviedbAPIClient movieDbCli;

	@InjectMocks
	MovieSearchService movieSearchService;
	
	@Test
	public void testFilenameParsing() {
		movieSearchService.searchMovies("/home/spidey/(500) days of summer (2009).mkv");
		verify(movieDbCli).searchMovies("(500) days of summer", 2009);
		
		movieSearchService.searchMovies("/home/spidey/(500) days of summer.mkv");
		verify(movieDbCli).searchMovies("(500) days of summer", null);

		movieSearchService.searchMovies("/home/spidey/Serenity (2005).mkv");
		verify(movieDbCli).searchMovies("Serenity", 2005);

		movieSearchService.searchMovies("/home/spidey/Serenity (200).mkv");
		verify(movieDbCli).searchMovies("Serenity (200)", null);

		movieSearchService.searchMovies("home/spidey/Real Life - Spidey Sux (at CoD).mov");
		verify(movieDbCli).searchMovies("Real Life - Spidey Sux (at CoD)", null);

		movieSearchService.searchMovies("Halo 4 - Forward unto Dawn (2012).mkv");
		verify(movieDbCli).searchMovies("Halo 4 - Forward unto Dawn", 2012);
		
		movieSearchService.searchMovies("Special Effects - Gordy with Girl (2016).mov");
		verify(movieDbCli).searchMovies("Special Effects - Gordy with Girl", 2016);
		
		movieSearchService.searchMovies("Real Life - Jared Sux at $mash (2017).mov");
		verify(movieDbCli).searchMovies("Real Life - Jared Sux at $mash", 2017);
	}
}
