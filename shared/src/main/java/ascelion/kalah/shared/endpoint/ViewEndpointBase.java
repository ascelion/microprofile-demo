package ascelion.kalah.shared.endpoint;

import static java.util.stream.Collectors.toList;

import ascelion.kalah.shared.AbstractView;
import ascelion.kalah.shared.PageInfo;
import ascelion.kalah.shared.SearchParam;
import ascelion.kalah.shared.SortInfo;
import ascelion.kalah.shared.SortKind;
import ascelion.kalah.shared.ViewEndpoint;
import ascelion.kalah.shared.bbm.BeanToBean;
import ascelion.kalah.shared.model.AbstractEntity;
import ascelion.kalah.shared.persistence.EntityRepo;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.metrics.annotation.Metered;

@Metered
@RequiredArgsConstructor
public abstract class ViewEndpointBase<V extends AbstractView, E extends AbstractEntity, R extends EntityRepo<E>>
		implements ViewEndpoint<V> {

	protected final R repo;
	protected final BeanToBean<V, E> bbm;

	@Override
	public List<V> getAll(PageInfo page, List<String> sort, SortKind direction) {
		return this.repo.findAll(page,
				sort.stream()
						.map(s -> SortInfo.builder().direction(direction).path(s).build())
						.collect(toList()))
				.map(this.bbm::btoa)
				.collect(toList());
	}

	@Override
	public V get(UUID id) {
		return this.bbm.btoa(this.repo.get(id));
	}

	@Override
	public List<V> search(SearchParam<V> param) {
		final E info = this.bbm.atob(param.getInfo());

		return this.repo.findAll(info, param.getPage(), param.getSort())
				.map(this.bbm::btoa)
				.collect(toList());
	}
}
