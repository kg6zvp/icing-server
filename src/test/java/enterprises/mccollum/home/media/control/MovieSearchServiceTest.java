package enterprises.mccollum.home.media.control;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.gson.Gson;

import enterprises.mccollum.home.media.model.MovieMetadata;
import enterprises.mccollum.home.themoviedb.TheMoviedbAPIClient;
import enterprises.mccollum.home.media.model.MediaSearchResultContainer;
import enterprises.mccollum.home.media.model.MediaSource;

@RunWith(MockitoJUnitRunner.class)
public class MovieSearchServiceTest {
	@Mock
	TheMoviedbAPIClient themovieDb;
	
	@InjectMocks
	MovieSearchService movieSearch;
	
	@Before
	public void setupTests() {
		//TODO
	}
	
	@Test
	public void testOrderSearchResults() {
		MediaSource source = new MediaSource();
		source.setName("a");
		
		when(movieSearch.movieDb.searchMovies("The Note", 2007))
			.thenReturn(new Gson().fromJson(
							"{\"page\":1,\"total_results\":6,\"total_pages\":1,\"results\":[{\"vote_count\":155,\"id\":16007,\"video\":false,\"vote_average\":6.9,\"title\":\"Death Note\",\"popularity\":5.228005,\"poster_path\":\"\\/jXaT3k2GuW9WTFmoCyskZyWfKlw.jpg\",\"original_language\":\"ja\",\"original_title\":\"デスノート\",\"genre_ids\":[14,9648,53],\"backdrop_path\":\"\\/jtWLpxu3zZzTNECLImUxo4NhzuF.jpg\",\"adult\":false,\"overview\":\"Light Yagami finds the \\\"Death Note,\\\" a notebook with the power to kill, and decides to create a Utopia by killing the world's criminals, and soon the world's greatest detective, \\\"L,\\\" is hired to find the mysterious murderer. An all out battle between the two greatest minds on earth begins and the winner will control the world.\",\"release_date\":\"2006-06-17\"},{\"vote_count\":3,\"id\":30569,\"video\":false,\"vote_average\":4.3,\"title\":\"The Note\",\"popularity\":1.305184,\"poster_path\":\"\\/4TA0uYGhPV5zxU7JF17DZITCNpW.jpg\",\"original_language\":\"en\",\"original_title\":\"The Note\",\"genre_ids\":[10770,18,10749],\"backdrop_path\":\"\\/fEb9zDthAj7lgBd7y8ErQSuJWQb.jpg\",\"adult\":false,\"overview\":\"Newspaper columnist Peyton MacGruder (Genie Francis) finds a note addressed simply to 'T', washed up on shore. It appears to be from the victim of a recent plane crash, and carries a message of hope and forgiveness from a father to his child. MacGruder's readership is down on her column (called \\\"Heart Healer\\\"), and the paper is going to dump it unless she starts to write from the heart.\",\"release_date\":\"2007-12-08\"},{\"vote_count\":108,\"id\":16140,\"video\":false,\"vote_average\":6.7,\"title\":\"Death Note: The Last Name\",\"popularity\":5.813076,\"poster_path\":\"\\/hxsKIv5U10eZBrlVRzS6hUiiK97.jpg\",\"original_language\":\"ja\",\"original_title\":\"デスノート the Last name\",\"genre_ids\":[14,53,9648],\"backdrop_path\":\"\\/qb9afBMhlrTVqJ1yyuofhGKDc7f.jpg\",\"adult\":false,\"overview\":\"In the second installment of the Death Note film franchise, Light Yagami meets a second Kira and faithful follower Misa Amane and her Shinigami named Rem. Light attempts to defeat L along with Teru Mikami (a Kira follower) and Kiyomi Takada (another Kira follower) but in the end will Light win? or will a Shinigami named Ryuk make all the difference in Light's victory or his ultimate death?\",\"release_date\":\"2006-10-28\"},{\"vote_count\":3,\"id\":27182,\"video\":false,\"vote_average\":7.3,\"title\":\"Note by Note: The Making of Steinway L1037\",\"popularity\":1.017991,\"poster_path\":\"\\/vxF0ow8yFXxUDrY50X27sRCTj87.jpg\",\"original_language\":\"en\",\"original_title\":\"Note by Note: The Making of Steinway L1037\",\"genre_ids\":[99],\"backdrop_path\":\"\\/3HzISgYhyjMgiJySpfIybaTHmA7.jpg\",\"adult\":false,\"overview\":\"A feature-length independent documentary that follows the creation of a Steinway concert grand, #L1037- from forest floor to concert hall.\",\"release_date\":\"2007-11-07\"},{\"vote_count\":0,\"id\":344778,\"video\":false,\"vote_average\":0,\"title\":\"Avishai Cohen - As Is...Live at the Blue Note\",\"popularity\":1.009261,\"poster_path\":\"\\/eW7DHEkPCliDFkoZ0TcGhtxxwJr.jpg\",\"original_language\":\"en\",\"original_title\":\"Avishai Cohen - As Is...Live at the Blue Note\",\"genre_ids\":[],\"backdrop_path\":null,\"adult\":false,\"overview\":\"Avishai Cohen, who became well known in the jazz world during his period with Chick Corea, is one of the top bassists in the world. His virtuosity and constant creativity in both a modern mainstream format and on funkier grooves seem effortless. As Is...Live at the Blue Note contains a CD (the first seven selections) and a DVD. \\\"Smash,\\\" \\\"Feediop,\\\" the ballad \\\"Remembering\\\" and an overlong \\\"Caravan\\\" (the one non-original) are featured in both formats while three songs are different. Cohen takes many solos and is always a prominent force in the ensembles. Pianist-keyboardist Sam Barsh and drummer Mark Guiliana communicate very well with Cohen while trumpeter Diego Urcola and tenor soprano saxophonist Jimmy Greene add plenty of fire during their guest appearances\",\"release_date\":\"2007-04-24\"},{\"vote_count\":0,\"id\":352822,\"video\":false,\"vote_average\":0,\"title\":\"Notes from the Rogues Gallery\",\"popularity\":1.005655,\"poster_path\":null,\"original_language\":\"en\",\"original_title\":\"Notes from the Rogues Gallery\",\"genre_ids\":[35],\"backdrop_path\":null,\"adult\":false,\"overview\":\"What if you decided to become... a super hero?\",\"release_date\":\"2007-12-31\"}]}",
							MediaSearchResultContainer.class).getResults());

		List<MovieMetadata> results = movieSearch.searchMovies("The Note (2007).mkv");
		
		MovieMetadata md = results.get(0);
		
		assertTrue("Expect title to match", "The Note".equals(md.getTitle()));
		assertEquals(30569l, (long)md.getId());
	}
}
