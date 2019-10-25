package ascelion.kalah.players;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "endpoints.players.")
public interface PlayersEndpoint {

	@GET
	@Produces(APPLICATION_JSON)
	List<Player> getAll();

	@GET
	@Path("{playerId}")
	@Produces(APPLICATION_JSON)
	Player get(@PathParam("playerId") UUID playerId);
}
