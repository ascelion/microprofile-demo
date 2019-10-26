package ascelion.kalah.players;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import ascelion.kalah.shared.endpoint.FullEntityEndpointBase;

@ApplicationScoped
@Path("players")
public class PlayersResource extends FullEntityEndpointBase<Player, Player, PlayersRepository> implements PlayersEndpoint {
	@Inject
	public PlayersResource(PlayersRepository repo) {
		super(repo);
	}
}
