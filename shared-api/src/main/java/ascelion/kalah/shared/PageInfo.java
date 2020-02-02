package ascelion.kalah.shared;

import javax.persistence.TypedQuery;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import lombok.Getter;

@Getter
public class PageInfo {
	@Min(0)
	@QueryParam("from")
	private Integer from;

	@Min(1)
	@QueryParam("size")
	@DefaultValue("10")
	private int size;

	public <T> TypedQuery<T> paginate(TypedQuery<T> q) {
		if (this.from != null) {
			q.setFirstResult(this.from * this.size);
			q.setMaxResults(this.size);
		}

		return q;
	}
}
