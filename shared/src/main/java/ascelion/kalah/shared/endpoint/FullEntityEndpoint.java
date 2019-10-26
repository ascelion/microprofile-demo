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

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

public interface FullEntityEndpoint<V> extends ViewEntityEndpoint<V> {

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V createEntity(@NotNull @Valid V view);

	@PUT
	@Path("{id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V updateEntity(@Parameter(name = "id", description = "The entity ID.", schema = @Schema(type = SchemaType.STRING)) @PathParam("id") UUID id, @NotNull @Valid V view);

	@PATCH
	@Path("{id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	V patchEntity(@Parameter(name = "id", description = "The entity ID.", schema = @Schema(type = SchemaType.STRING)) @PathParam("id") UUID id, @NotNull @Valid V view);
}
