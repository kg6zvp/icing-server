package enterprises.mccollum.home.media.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author smccollum
 */
@RunWith(MockitoJUnitRunner.class)
public class MediaSourceTest {
	@Test
	public void testNameShouldBeAlphanumericAndUnderscoresOnPersist() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MediaSource mediaSource = new MediaSource();
		Method prePersist = mediaSource.getClass().getDeclaredMethod("prePersist");
		prePersist.setAccessible(true);
		
		mediaSource.setName("This Name Should Be Fixed");
		prePersist.invoke(mediaSource);
		assertEquals("This_Name_Should_Be_Fixed", mediaSource.getName());

		mediaSource.setName("This%Name%Should%Be%Fixed");
		assertEquals("This%Name%Should%Be%Fixed", mediaSource.getName());
		prePersist.invoke(mediaSource);
		assertEquals("This_Name_Should_Be_Fixed", mediaSource.getName());

		mediaSource.setName("This$Name$Should$Be$Fixed");
		assertEquals("This$Name$Should$Be$Fixed", mediaSource.getName());
		prePersist.invoke(mediaSource);
		assertEquals("This_Name_Should_Be_Fixed", mediaSource.getName());

		mediaSource.setName("This(Name(Should(Be(Fixed");
		assertEquals("This(Name(Should(Be(Fixed", mediaSource.getName());
		prePersist.invoke(mediaSource);
		assertEquals("This_Name_Should_Be_Fixed", mediaSource.getName());
	}
	
	@Test
	public void testNameShouldBeAlphanumericAndUnderscoresOnUpdate() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MediaSource mediaSource = new MediaSource();
		Method preUpdate = mediaSource.getClass().getDeclaredMethod("preUpdate");
		preUpdate.setAccessible(true);
		
		mediaSource.setName("This Name Should Be Fixed");
		preUpdate.invoke(mediaSource);
		assertEquals("This_Name_Should_Be_Fixed", mediaSource.getName());
	}
}
