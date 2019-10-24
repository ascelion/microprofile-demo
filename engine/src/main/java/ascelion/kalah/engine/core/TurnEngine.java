package ascelion.kalah.engine.core;

import ascelion.kalah.engine.exceptions.GameEndedException;
import ascelion.kalah.engine.model.Board;
import ascelion.kalah.engine.model.House;

/**
 * Turn orchestrator; for each turn, it calls the moving handler then it applies the rules.
 */
public final class TurnEngine {
	private final Board board;
	private final House house;
	private final int[] initialSeeds;

	public TurnEngine(Board board, int houseIndex) {
		this.board = board;
		this.house = board.houseAt(houseIndex);
		this.initialSeeds = board.getSeeds();
	}

	public void run() {
		if (this.board.isEnded()) {
			throw new GameEndedException(this.board);
		}

		// move
		final MoveHandler move = new MoveHandler(this.board, this.house);
		final House lastHouse = move.execute();

		// rules
		final RulesHandler rules = new RulesHandler(this.board, lastHouse, this.initialSeeds);
		rules.apply();
	}
}
