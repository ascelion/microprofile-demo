package ascelion.kalah.boards;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ascelion.kalah.engine.core.TurnEngine;
import ascelion.kalah.engine.model.Board;
import ascelion.kalah.players.PlayersEndpoint;
import ascelion.kalah.shared.endpoint.ViewEntityEndpointBase;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("boards")
@ApplicationScoped
public class BoardsEndpoint extends ViewEntityEndpointBase<Board, Board, BoardsRepository> {
	private final PlayersEndpoint players;

	@Inject
	public BoardsEndpoint(BoardsRepository repo, @RestClient PlayersEndpoint players) {
		super(repo);

		this.players = players;
	}

	@POST
	@Consumes(APPLICATION_FORM_URLENCODED)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	public Board create(@Parameter(name = "userId", description = "The user ID.", schema = @Schema(type = SchemaType.STRING)) @FormParam("userId") @NotNull UUID userId) {
		// verify user
		this.players.get(userId);

		final Board board = Board.builder()
				.southId(userId)
				.build();

		return this.repo.save(board);
	}

	@POST
	@Path("{boardId}")
	@Consumes(APPLICATION_FORM_URLENCODED)
	@Produces(APPLICATION_JSON)
	@NotNull
	@Valid
	public Board join(
			@Parameter(name = "boardId", description = "The board ID.", schema = @Schema(type = SchemaType.STRING)) @PathParam("boardId") @NotNull UUID boardId,
			@Parameter(name = "userId", description = "The user ID.", schema = @Schema(type = SchemaType.STRING)) @FormParam("userId") @NotNull UUID userId) {
		// verify user
		this.players.get(userId);

		final Board board = this.repo.get(boardId);

		try {
			board.start(userId);
		} catch (final IllegalStateException e) {
			throw new WebApplicationException(Response.Status.CONFLICT);
		}

		return this.repo.save(board);
	}

	@PUT
	@Path("{boardId}/{index}")
	@NotNull
	@Valid
	public Board move(
			@Parameter(name = "boardId", description = "The board ID.", schema = @Schema(type = SchemaType.STRING)) @PathParam("boardId") @NotNull UUID boardId,
			@Parameter(name = "index", description = "The house index.", schema = @Schema(type = SchemaType.STRING)) @PathParam("index") @Min(1) @Max(Board.PITS_COUNT) int index) {
		final Board board = this.repo.get(boardId);

		final TurnEngine engine = new TurnEngine(board, index - 1);

		engine.run();

		return this.repo.save(board);
	}
}
