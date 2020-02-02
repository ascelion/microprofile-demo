package ascelion.kalah.shared.persistence;

import static io.leangen.geantyref.GenericTypeReflector.getTypeParameter;
import static java.lang.String.format;

import ascelion.kalah.shared.PageInfo;
import ascelion.kalah.shared.SortInfo;
import ascelion.kalah.shared.model.AbstractEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.apache.deltaspike.data.api.FullEntityRepository;

public interface EntityRepo<E extends AbstractEntity> extends FullEntityRepository<E, UUID> {
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

	default Path<?> getPath(String path) {
		return getPath(path, getCriteriaBuilder().createQuery().from(entityType()));
	}

	default Path<?> getPath(String path, Path<?> base) {
		final String[] items = path.split("\\.");

		for (final String item : items) {
			base = base.get(item);
		}

		return base;
	}

	default Stream<E> findAll(PageInfo page, List<SortInfo> sort) {
		final CriteriaBuilder cb = getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb.createQuery(entityClass());
		final Root<E> root = cq.from(entityType());

		cq.select(root);
		cq.orderBy(sort.stream()
				.map(s -> JPADirection.orderBy(cb, getPath(s.path, root), s.direction))
				.toArray(Order[]::new));

		return page.paginate(entityManager().createQuery(cq)).getResultStream();
	}

	default Stream<E> findAll(E probe, PageInfo page, List<SortInfo> sort) {
		return Stream.empty();
	}

	EntityManager entityManager();
}
