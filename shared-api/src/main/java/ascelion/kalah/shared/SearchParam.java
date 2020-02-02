package ascelion.kalah.shared;

import static java.util.Collections.emptyList;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class SearchParam<E> {
	@NonNull
	@Valid
	private E info;
	@Valid
	private PageInfo page;

	@QueryParam("sort")
	private List<SortInfo> sort = emptyList();
}
