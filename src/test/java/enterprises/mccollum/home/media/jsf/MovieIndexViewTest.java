package enterprises.mccollum.home.media.jsf;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import enterprises.mccollum.home.media.control.MovieIndexingService;
import enterprises.mccollum.home.media.jsf.MovieIndexView;
import enterprises.mccollum.home.media.model.MediaSource;
import enterprises.mccollum.home.media.model.MediaSource.Type;
import enterprises.mccollum.home.media.model.MediaSourceDao;
import enterprises.mccollum.home.media.model.MovieFile;
import enterprises.mccollum.home.media.model.MovieMetadata;

@RunWith(MockitoJUnitRunner.class)
public class MovieIndexViewTest {
	//test getMoviesList
	
	@Mock
	MovieIndexingService movieIndexingService;
	
	@Mock
	MediaSourceDao sources;
	
	@InjectMocks
	MovieIndexView movieIndexView;

	@Before
	public void setupTests() {
		MovieMetadata a = new MovieMetadata();
		a.setTitle("A Long Story");
		MovieFile fa = new MovieFile();
		fa.setMetaData(a);
		
		MovieMetadata b = new MovieMetadata();
		b.setTitle("The Albatross");
		MovieFile fb = new MovieFile();
		fb.setMetaData(b);
		
		MovieMetadata c = new MovieMetadata();
		c.setTitle("Themes from Hamlet");
		MovieFile fc = new MovieFile();
		fc.setMetaData(c);
	
		MovieMetadata d = new MovieMetadata();
		d.setTitle("Cats");
		MovieFile fd = new MovieFile();
		fd.setMetaData(d);
	
		MovieMetadata e = new MovieMetadata();
		e.setTitle("The Dog");
		MovieFile fe = new MovieFile();
		fe.setMetaData(e);
		
		MovieMetadata f = new MovieMetadata();
		f.setTitle("Golf with Melvin");
		MovieFile ff = new MovieFile();
		ff.setMetaData(f);
		
		MovieMetadata g = new MovieMetadata();
		g.setTitle("The Vulture");
		MovieFile fg = new MovieFile();
		fg.setMetaData(g);
		
		
		final MediaSource src = new MediaSource();
		src.setName("quell");
		src.setType(Type.MOVIES);
		src.getMovies().add(fa);
		src.getMovies().add(fb);
		src.getMovies().add(fc);
		src.getMovies().add(fd);
		src.getMovies().add(fe);
		src.getMovies().add(ff);
		src.getMovies().add(fg);
		
		final List<MediaSource> srcs = new LinkedList<MediaSource>();
		srcs.add(src);
		
		when(movieIndexView.sources.getAll()).then(new Answer<List<MediaSource>>() {
			public List<MediaSource> answer(InvocationOnMock invocation){
				return srcs;
			}
		});
	}
	
	@Test
	public void testMoviesOrdering() {
		assertTrue("A Long Story is the first movie",
				"A Long Story".equals(movieIndexView.getMoviesList().get(0).getMetaData().getTitle()));
		
		assertTrue("The Albatross is the second movie",
				"The Albatross".equals(movieIndexView.getMoviesList().get(1).getMetaData().getTitle()));
		
		assertTrue("Cats is the third movie",
				"Cats".equals(movieIndexView.getMoviesList().get(2).getMetaData().getTitle()));
		
		assertTrue("The Dog is the fourth movie",
				"The Dog".equals(movieIndexView.getMoviesList().get(3).getMetaData().getTitle()));
		
		assertTrue("Golf with Melvin is the fifth movie",
				"Golf with Melvin".equals(movieIndexView.getMoviesList().get(4).getMetaData().getTitle()));
		
		assertTrue("Themes from Hamlet is the sixth movie",
				"Themes from Hamlet".equals(movieIndexView.getMoviesList().get(5).getMetaData().getTitle()));
		
		assertTrue("The Vulture is the seventh movie",
				"The Vulture".equals(movieIndexView.getMoviesList().get(6).getMetaData().getTitle()));
	}
}
