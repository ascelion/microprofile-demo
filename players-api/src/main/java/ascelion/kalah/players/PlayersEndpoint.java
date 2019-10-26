package ascelion.kalah.players;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import ascelion.kalah.shared.endpoint.ViewEntityEndpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "endpoints.players.")
public interface PlayersEndpoint extends ViewEntityEndpoint<Player> {
	/**
	 * There is a bug in Jersey Rest Client that prevents the proper detection of the return type.
	 */
	@Override
	@GET
	@Path("{id}")
	@Produces(APPLICATION_JSON)
	@Retry(maxRetries = 5)
	Player get(@Parameter(name = "id", description = "The player ID.", schema = @Schema(type = SchemaType.STRING)) @PathParam("id") UUID id);
}
