package enterprises.mccollum.utils.genericentityejb;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

public class DaoMocker {
	public static <T, K> void mockPersistenceManager(GenericPersistenceManager<T, K> mock, Class<T> entityClass){
		when(mock.persist(any(entityClass))).then(new Answer<T>() {
			@Override
			public T answer(InvocationOnMock arg0) {
				return (T)arg0.getArguments()[0];
			}
		});
		when(mock.save(any(entityClass))).then(new Answer<T>() {
			@Override
			public T answer(InvocationOnMock arg0) {
				return (T)arg0.getArguments()[0];
			}
		});
	}
}
