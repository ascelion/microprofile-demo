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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ascelion.kalah.engine.model.Board;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Path("boards")
@ApplicationScoped
public class BoardsEndpoint {

	@Inject
	private BoardsRepository repo;

	@GET
	public List<Board> getAll() {
		return this.repo.findAll();
	}

	@POST
	@Consumes(APPLICATION_FORM_URLENCODED)
	public Board create(@FormParam("userId") UUID userId) {
		final Board board = Board.builder()
				.southId(userId)
				.build();

		return this.repo.save(board);
	}

	@POST
	@Path("{boardId}")
	@Consumes(APPLICATION_FORM_URLENCODED)
	public Board join(@FormParam("userId") UUID userId) {
		final Board board = this.repo.findOptionalBy(userId)
				.orElseThrow(() -> new EntityNotFoundException());

		if (board.getNorthId() != null) {
			throw new WebApplicationException(Response.Status.CONFLICT);
		}

		board.setNorthId(userId);

		return this.repo.save(board);
	}

}
