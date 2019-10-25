package ascelion.kalah.boards;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ascelion.kalah.engine.model.Board;
import ascelion.kalah.players.PlayersEndpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("boards")
@ApplicationScoped
public class BoardsEndpoint {

	@Inject
	private BoardsRepository repo;
	@Inject
	@RestClient
	private PlayersEndpoint players;

	@GET
	public List<Board> getAll() {
		return this.repo.findAll();
	}

	@POST
	@Consumes(APPLICATION_FORM_URLENCODED)
	public Board create(@FormParam("userId") UUID userId) {
		this.players.get(userId);

		final Board board = Board.builder()
				.southId(userId)
				.build();

		return this.repo.save(board);
	}

	@POST
	@Path("{boardId}")
	@Consumes(APPLICATION_FORM_URLENCODED)
	public Board join(@PathParam("boardId") UUID boardId, @FormParam("userId") UUID userId) {
		final Board board = this.repo.findOptionalBy(userId)
				.orElseThrow(() -> new EntityNotFoundException());

		if (board.getNorthId() != null) {
			throw new WebApplicationException(Response.Status.CONFLICT);
		}

		this.players.get(userId);

		board.setNorthId(userId);

		return this.repo.save(board);
	}

}
