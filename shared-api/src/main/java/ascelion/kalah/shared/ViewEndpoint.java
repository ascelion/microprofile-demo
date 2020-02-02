package ascelion.kalah.shared;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

public interface ViewEndpoint<V extends AbstractView> {
	@GET
	@Produces(APPLICATION_JSON)
	List<V> getAll(@BeanParam @Valid PageInfo page,
			@QueryParam("sort") List<String> sort,
			@NotNull @QueryParam("direction") @DefaultValue("ASC") SortKind direction);

	@GET
	@Path("{id}")
	@Produces(APPLICATION_JSON)
	V get(@PathParam("id") @NotNull UUID id);

	@POST
	@Path("search")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	List<V> search(@NotNull @Valid SearchParam<V> search);
}
