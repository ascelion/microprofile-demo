package ascelion.kalah.shared.endpoint;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.metamodel.Attribute;
import javax.transaction.Transactional;

import ascelion.kalah.shared.model.AbstractEntity;
import ascelion.kalah.shared.persistence.EntityRepo;

public abstract class FullEntityEndpointBase<V, E extends AbstractEntity<E>, R extends EntityRepo<E>>
		extends ViewEntityEndpointBase<V, E, R>
		implements FullEntityEndpoint<V> {

	protected FullEntityEndpointBase(R repo) {
		super(repo);
	}

	@Override
	@Transactional
	public V createEntity(V view) {
		return this.etov.apply(this.repo.save(this.vtoe.apply(view)));
	}

	@Override
	@Transactional
	public V updateEntity(UUID id, V view) {
		final E source = this.vtoe.apply(view);
		final E target = this.repo.get(id);

		copyAttributes(source, target, true);

		return this.etov.apply(this.repo.save(target));
	}

	@Override
	@Transactional
	public V patchEntity(UUID id, V view) {
		final E source = this.vtoe.apply(view);
		final E target = this.repo.get(id);

		copyAttributes(source, target, false);

		return this.etov.apply(this.repo.save(target));
	}

	private void copyAttributes(E source, E target, boolean withNulls) {
		for (final Attribute<? super E, ?> attribute : this.repo.entityType().getAttributes()) {
			final Member member = attribute.getJavaMember();

			if (member instanceof Field) {
				final Field field = (Field) member;

				if (field.isAnnotationPresent(Id.class)) {
					continue;
				}

				field.setAccessible(true);

				try {
					final Object s = field.get(source);

					if (s != null || withNulls) {
						field.set(target, s);
					}
				} catch (final IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
