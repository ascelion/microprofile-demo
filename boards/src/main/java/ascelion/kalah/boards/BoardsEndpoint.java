package ascelion.kalah.boards;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ascelion.kalah.engine.model.Board;
import ascelion.kalah.players.PlayersEndpoint;
import ascelion.kalah.shared.endpoint.FullEntityEndpointBase;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("boards")
@ApplicationScoped
public class BoardsEndpoint extends FullEntityEndpointBase<Board, Board, BoardsRepository> {
	private final PlayersEndpoint players;

	@Inject
	public BoardsEndpoint(BoardsRepository repo, @RestClient PlayersEndpoint players) {
		super(repo);

		this.players = players;
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
