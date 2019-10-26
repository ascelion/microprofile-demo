package ascelion.kalah.shared.endpoint;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import ascelion.kalah.shared.model.AbstractEntity;
import ascelion.kalah.shared.persistence.EntityRepo;

import static java.util.stream.Collectors.toList;

import io.leangen.geantyref.GenericTypeReflector;
import org.eclipse.microprofile.metrics.annotation.Metered;

@Metered
public abstract class ViewEntityEndpointBase<V, E extends AbstractEntity<E>, R extends EntityRepo<E>>
		implements ViewEntityEndpoint<V> {

	@SuppressWarnings("unchecked")
	protected final Class<E> type = (Class<E>) GenericTypeReflector.getTypeParameter(getClass(),
			ViewEntityEndpointBase.class.getTypeParameters()[1]);

	@SuppressWarnings("unchecked")
	protected Function<E, V> etov = x -> (V) x;
	@SuppressWarnings("unchecked")
	protected Function<V, E> vtoe = x -> (E) x;

	protected final R repo;

	protected ViewEntityEndpointBase(R repo) {
		this.repo = repo;
	}

	@Override
	public List<V> getAll(List<String> sort, Integer page, int size) {
		return this.repo.findAll(sort, page, size)
				.map(this.etov)
				.collect(toList());
	}

	@Override
	public V get(UUID id) {
		return this.etov.apply(this.repo.get(id));
	}

	@Override
	public List<V> search(Search<V> search) {
		final E probe = this.vtoe.apply(search.probe);

		return this.repo.findAll(probe, search.sort, search.page, search.size, search.any)
				.map(this.etov)
				.collect(toList());
	}
}
