package ascelion.kalah.players;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("players")
public class PlayersResource implements PlayersEndpoint {
	@Inject
	private PlayersRepository repo;

	@Override
	public List<Player> getAll() {
		return this.repo.findAll();
	}

	@Override
	public Player get(UUID playerId) {
		return this.repo.findOptionalBy(playerId)
				.orElseThrow(() -> new EntityNotFoundException("Cannot find player with id " + playerId));
	}
}
