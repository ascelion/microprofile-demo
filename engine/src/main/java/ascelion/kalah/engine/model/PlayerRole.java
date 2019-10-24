package ascelion.kalah.engine.model;

import static ascelion.kalah.engine.model.Board.PITS_COUNT;

import lombok.Getter;
import lombok.ToString;

/**
 * The type of the player.
 */
@ToString(of = "externalName")
public enum PlayerRole {
	SOUTH_ROLE,
	NORTH_ROLE,
	;

	/**
	 * The index of the first house of this player
	 */
	@Getter
	private final int firstIndex;

	/**
	 * The kalah index.
	 */
	@Getter
	private final int kalahIndex;

	private final String externalName;

	private PlayerRole() {
		this.firstIndex = PITS_COUNT / 2 * ordinal();
		this.kalahIndex = PITS_COUNT / 2 * (ordinal() + 1) - 1;

		this.externalName = name().replace("_ROLE", "");
	}

	/**
	 * Verifies if the given house belongs to this player or is its kalah.
	 *
	 * @return true if this player owns the house
	 */
	public boolean isOwningHouseOrKalah(House house) {
		return this.firstIndex <= house.getIndex() && house.getIndex() <= this.kalahIndex;
	}

	/**
	 * Verifies if the given house belongs to this player (but not the kalah).
	 *
	 * @return true if this player owns the house
	 */
	public boolean isOwningHouse(House house) {
		return this.firstIndex <= house.getIndex() && house.getIndex() < this.kalahIndex;
	}

	/**
	 * Returns the the next player of this one.
	 */
	public PlayerRole next() {
		return values()[(ordinal() + 1) % 2];
	}
}
