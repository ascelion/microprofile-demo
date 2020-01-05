package ascelion.kalah.players;

import ascelion.kalah.shared.endpoint.FullEntityEndpointBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("players")
public class PlayersResource extends FullEntityEndpointBase<Player, PlayerEntity, PlayersRepository> implements PlayersEndpoint {
	@Inject
	public PlayersResource(PlayersRepository repo) {
		super(repo);
	}
}
