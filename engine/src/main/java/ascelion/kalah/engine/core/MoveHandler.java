package ascelion.kalah.engine.core;

import ascelion.kalah.engine.exceptions.HouseEmptyException;
import ascelion.kalah.engine.exceptions.HouseInvalidException;
import ascelion.kalah.engine.model.Board;
import ascelion.kalah.engine.model.House;
import ascelion.kalah.engine.model.PlayerRole;

import static ascelion.kalah.engine.model.Board.PITS_COUNT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles a move on a board; note that instances of this class are not reusable.
 */
final class MoveHandler {
	static private final Logger LOG = LoggerFactory.getLogger(MoveHandler.class);

	private final Board board;
	private final PlayerRole currentPlayer;
	private final PlayerRole nextPlayer;

	// internal, it keeps the house that is processed during the sowing
	private House house;
	// internal, it the number of left seeds to be sown
	private int seeds;

	MoveHandler(Board board, House house) {
		this.board = board;
		this.currentPlayer = board.getCurrentPlayer();
		this.nextPlayer = this.currentPlayer.next();

		this.house = house;
	}

	/**
	 * Run a move, step by step; return the last house that has been visited by the current move.
	 */
	House execute() {
		validateHouse();

		// take all seeds from the current house
		this.seeds = this.house.take();

		assert this.seeds > 0;

		LOG.debug("GAME[{}]: took {} seeds from house {}",
				this.board.getId(), this.seeds, this.house.getIndex() + 1);

		// sow the seeds one by one
		do {
			sow();
		} while (--this.seeds > 0);

		// return the last house that has been sown
		return this.house;
	}

	private void validateHouse() {
		if (!this.currentPlayer.isOwningHouse(this.house)) {
			throw new HouseInvalidException(this.board, this.house);
		}
		if (this.house.isEmpty()) {
			throw new HouseEmptyException(this.board, this.house);
		}
	}

	private void sow() {
		// move to the next house
		advance();

		// drop a seed into the current house
		this.house.dropSeed();

		LOG.debug("GAME[{}]: dropped one seed into house {} (seeds = {})",
				this.board.getId(), this.house.getIndex() + 1, this.house.getSeeds());
	}

	private void advance() {
		int nextIndex = (this.house.getIndex() + 1) % PITS_COUNT;

		// skip the opposer kalah
		if (nextIndex == this.nextPlayer.getKalahIndex()) {
			LOG.debug("GAME[{}]: house {} is the opposer's kalah, skipping it",
					this.board.getId(), nextIndex + 1);

			nextIndex = (nextIndex + 1) % PITS_COUNT;
		}

		this.house = this.board.houseAt(nextIndex);
	}
}
