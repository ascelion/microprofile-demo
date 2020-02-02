package ascelion.kalah.shared.endpoint;

import ascelion.kalah.shared.AbstractView;
import ascelion.kalah.shared.ChangeEndpoint;
import ascelion.kalah.shared.bbm.BeanToBean;
import ascelion.kalah.shared.model.AbstractEntity;
import ascelion.kalah.shared.persistence.EntityRepo;

import javax.transaction.Transactional;

public abstract class ChangeEndpointBase<V extends AbstractView, E extends AbstractEntity, R extends EntityRepo<E>>
		extends ViewEndpointBase<V, E, R>
		implements ChangeEndpoint<V> {

	public ChangeEndpointBase(R repo, BeanToBean<V, E> bbm) {
		super(repo, bbm);
	}

	@Override
	@Transactional
	public V create(V view) {
		return this.bbm.btoa(this.repo.save(this.bbm.atob(view)));
	}

	@Override
	@Transactional
	public V update(V view) {
		final E target = this.repo.get(view.getId());

		this.bbm.atob(view, target);

		return this.bbm.btoa(this.repo.save(target));
	}

	@Override
	@Transactional
	public V patch(V view) {
		final E target = this.repo.get(view.getId());

		this.bbm.atobPatch(view, target);

		return this.bbm.btoa(this.repo.save(target));
	}
}
