package ascelion.kalah.engine.core;

import java.util.List;

import ascelion.kalah.engine.model.Board;
import ascelion.kalah.engine.model.House;
import ascelion.kalah.engine.model.PlayerRole;

import static ascelion.kalah.engine.model.Board.PITS_COUNT;
import static ascelion.kalah.engine.model.PlayerRole.NORTH_ROLE;
import static ascelion.kalah.engine.model.PlayerRole.SOUTH_ROLE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rules handler; note that instances of this class are not reusable.
 */
final class RulesHandler {
	static private final Logger LOG = LoggerFactory.getLogger(RulesHandler.class);

	private final Board board;
	private final House house;
	private final int[] initialSeeds;

	// shortcuts to avoid too many getters
	private final List<House> houses;
	private final PlayerRole currentPlayer;

	RulesHandler(Board board, House house, int[] initialSeeds) {
		this.board = board;
		this.house = house;
		this.initialSeeds = initialSeeds;

		this.houses = this.board.getHouses();
		this.currentPlayer = this.board.getCurrentPlayer();
	}

	/**
	 * Apply the board rules.
	 */
	void apply() {
		if (gameEndedRule()) {
			return;
		}
		if (reachedKalahRule()) {
			LOG.debug("GAME[{}]: {} got one more move",
					this.board.getId(), this.board.getCurrentPlayer());

			return;
		}

		// change the player
		this.board.changePlayer();

		captureOppositeRule();
	}

	/*
	 * If the last sown seed lands in the player's kalah, the player gets an additional move. There is no limit on the
	 * number of moves a player can make in their turn.
	 */
	private boolean reachedKalahRule() {
		LOG.trace("GAME[{}]: checking ReachedKalah rule", this.board.getId());

		return this.house.getIndex() == this.currentPlayer.getKalahIndex();
	}

	/*
	 * If the last sown seed lands in an empty house owned by the player, and the opposite house contains seeds, both the
	 * last seed and the opposite seeds are captured and placed into the player's kalah.
	 */
	private void captureOppositeRule() {
		LOG.trace("GAME[{}]: checking CaptureOpposite rule", this.board.getId());

		// check if the last house had no seed at the beginning of the move
		if (this.initialSeeds[this.house.getIndex()] != 0) {
			return;
		}

		LOG.trace("GAME[{}]: house {} had no seed",
				this.board.getId(), this.house.getIndex() + 1);

		if (!this.currentPlayer.isOwningHouse(this.house)) {
			LOG.trace("GAME[{}]: {} is NOT owning house {}",
					this.board.getId(), this.currentPlayer, this.house.getIndex() + 1);
			return;
		}

		LOG.trace("GAME[{}]: {} is owning house {}",
				this.board.getId(), this.currentPlayer, this.house.getIndex() + 1);

		final House opposite = calculateOpposite(this.house);
		final House kalah = playerKalah(this.currentPlayer);

		LOG.trace("GAME[{}]: the opposite house is {}",
				this.board.getId(), opposite.getIndex() + 1);

		// pick the last seed and drop it to kalah
		this.house.pickSeed();
		kalah.dropSeed();

		// take all seeds from the opposite house
		kalah.takeFrom(opposite);

		LOG.debug("GAME[{}]: {}'s kalah has now {} seeds",
				this.board.getId(), this.currentPlayer, kalah.getSeeds());
	}

	/*
	 * When one player no longer has any seeds in any of their houses, the board ends. The other player moves all remaining
	 * seeds to their store, and the player with the most seeds in their store wins.
	 */
	private boolean gameEndedRule() {
		LOG.trace("GAME[{}]: checking GameEnded rule", this.board.getId());

		final int southSeeds = countSeeds(SOUTH_ROLE);
		final int northSeeds = countSeeds(NORTH_ROLE);

		// verify if any player ran out of seeds
		if (southSeeds != 0 && northSeeds != 0) {
			return false;
		}

		LOG.debug("GAME[{}]: SOUTH has {} seeds, NORTH has {}, ending board",
				this.board.getId(), southSeeds, northSeeds);

		// board ended, let's see who is the winner
		final int southKalahSeeds = moveAllToKalah(SOUTH_ROLE);
		final int northKalahSeeds = moveAllToKalah(NORTH_ROLE);

		LOG.trace("KALAH[{}]: SOUTH has {} seeds, NORTH has {}",
				this.board.getId(), southKalahSeeds, northKalahSeeds);

		final int result = Integer.compare(southKalahSeeds, northKalahSeeds);

		// the documentation of Integer.compare says the result
		// can be less than zero, or zero, or greater than zero
		// but the implementation actually returns -1, 0, +1
		// let's be safe though
		if (result < 0) {
			this.board.setWinner(NORTH_ROLE);
		} else if (result > 0) {
			this.board.setWinner(SOUTH_ROLE);
		} else {
			this.board.setWinner(null);
		}

		return true;
	}

	/**
	 * Returns the kalah of the given player.
	 */
	private House playerKalah(PlayerRole player) {
		return this.board.houseAt(player.getKalahIndex());
	}

	/**
	 * The total number of seeds in the player houses.
	 */
	private int countSeeds(PlayerRole player) {
		return this.houses.stream()
				.skip(player.getFirstIndex())
				.limit(PITS_COUNT / 2 - 1)
				.mapToInt(House::getSeeds)
				.sum();
	}

	/**
	 * Return the opposite house of the given house; no additional check is performed, the house is supposed to not be kalah
	 */
	House calculateOpposite(House house) {
		return this.board.houseAt(PITS_COUNT - house.getIndex() - 2);
	}

	/**
	 * Move all seeds from houses to kalah, returning the seeds in the kalah.
	 *
	 * @return the final number of seeds in the kalah.
	 */
	int moveAllToKalah(PlayerRole player) {
		final House kalah = this.board.houseAt(player.getKalahIndex());

		this.houses.stream()
				.skip(player.getFirstIndex())
				.limit(PITS_COUNT / 2 - 1)
				.forEach(kalah::takeFrom);

		return kalah.getSeeds();
	}
}
