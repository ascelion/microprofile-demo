package ascelion.kalah.shared;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public interface ChangeEndpoint<V extends AbstractView> extends ViewEndpoint<V> {

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V create(@NotNull @Valid V view);

	@PUT
	@Path("{id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V update(@BeanParam @NotNull @Valid V view);

	@PATCH
	@Path("{id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V patch(@BeanParam @NotNull @Valid V view);
}
