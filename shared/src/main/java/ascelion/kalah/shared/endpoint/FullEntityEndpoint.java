package ascelion.kalah.shared.endpoint;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public interface FullEntityEndpoint<V> extends ViewEntityEndpoint<V> {

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V createEntity(@NotNull V view);

	@PUT
	@Path("{id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V updateEntity(@NotNull @PathParam("id") UUID id, @NotNull V view);

	@PATCH
	@Path("{id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V patchEntity(@NotNull @PathParam("id") UUID id, @NotNull V view);
}
