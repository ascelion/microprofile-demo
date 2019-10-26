package ascelion.kalah.shared.persistence;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;
import javax.persistence.metamodel.EntityType;

import ascelion.kalah.shared.model.AbstractEntity;

import static io.leangen.geantyref.GenericTypeReflector.getTypeParameter;
import static java.lang.String.format;

import org.apache.deltaspike.data.api.FullEntityRepository;
import org.apache.deltaspike.data.api.criteria.Criteria;

public interface EntityRepo<E extends AbstractEntity<E>> extends FullEntityRepository<E, UUID> {
	@SuppressWarnings("unchecked")
	default Class<E> entityClass() {
		return (Class<E>) getTypeParameter(getClass(), EntityRepo.class.getTypeParameters()[0]);
	}

	default String entityName() {
		return entityType().getName();
	}

	default EntityType<E> entityType() {
		return getMetamodel().entity(entityClass());
	}

	default E get(UUID id) {
		return findOptionalBy(id)
				.orElseThrow(() -> {
					return new EntityNotFoundException(format("Cannot find %s with id %s", entityName(), id));
				});
	}

	default Stream<E> findAll(List<String> sort, Integer page, int size) {
		final Criteria<E, E> c = criteria();

		if (sort != null) {
			for (final String s : sort) {
				c.orderAsc(entityType().getSingularAttribute(s));
			}
		}

		final List<E> results = page != null
				? c.createQuery().setFirstResult(page * size).setMaxResults(size).getResultList()
				: c.getResultList();

		return results.stream();
	}

	default Stream<E> findAll(E example, String[] sort, Integer page, int size, boolean any) {
		return Stream.empty();
	}
}
