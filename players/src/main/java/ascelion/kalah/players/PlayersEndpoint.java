package ascelion.kalah.players;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("players")
@ApplicationScoped
public class PlayersEndpoint {

	@Inject
	private PlayersRepository repo;

	@GET
	public List<Player> getAll() {
		return this.repo.findAll();
	}
}
