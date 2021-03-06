package ascelion.kalah.engine.model;

import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;

import ascelion.kalah.shared.model.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "boards")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Board extends AbstractEntity<Board> {
	static public final int PITS_COUNT = 14;
	static public final int SEEDS_INITIAL = 6;

	static public final int SOUTH_HOME_INDEX = PITS_COUNT / 2 - 1;
	static public final int NORTH_HOME_INDEX = PITS_COUNT - 1;

	@NotNull
	@Column(name = "south_id", nullable = false)
	private UUID southId;
	@Column(name = "north_id")
	private UUID northId;

	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "player", length = 20)
	private PlayerRole currentPlayer;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private PlayerRole winner;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "houses",
			uniqueConstraints = @UniqueConstraint(name = "house_unique_ix", columnNames = { "board_id", "index" }),
			joinColumns = @JoinColumn(name = "board_id"))
	@OrderBy("index")
	private List<House> houses;

	public void start(UUID northId) {
		if (isStarted()) {
			throw new IllegalStateException("Game already started");
		}
		if (isEnded()) {
			throw new IllegalStateException("Game ended");
		}

		this.northId = northId;
		this.currentPlayer = PlayerRole.SOUTH_ROLE;
	}

	/**
	 * The winner of this game as decided by rules; setting this will also set the <code>ended</code> field.
	 * The winner can be null, which means it is a tie.
	 */
	public void setWinner(PlayerRole winner) {
		this.winner = winner;
		this.currentPlayer = null;
	}

	/**
	 * Switch to the next player.
	 */
	public void changePlayer() {
		if (!isStarted()) {
			throw new IllegalStateException("Game not started");
		}
		if (isEnded()) {
			throw new IllegalStateException("Game ended");
		}

		this.currentPlayer = this.currentPlayer.next();
	}

	public boolean isStarted() {
		return this.northId != null;
	}

	/**
	 * True if the game has ended.
	 */
	public boolean isEnded() {
		return this.northId != null && this.currentPlayer == null;
	}

	public String getPlayer() {
		return ofNullable(this.currentPlayer).map(PlayerRole::toString).orElse(null);
	}

	public String getWinner() {
		return ofNullable(this.winner).map(PlayerRole::toString).orElse(null);
	}

	/**
	 * Returns the unmodifiable list of houses.
	 */
	public List<House> getHouses() {
		return unmodifiableList(this.houses);
	}

	/**
	 * Returns the house at the given index; no check of the index is performed; used as a shortcut of
	 * <code>board.getHouses().get(index)</code>.
	 */
	public House houseAt(int index) {
		return this.houses.get(index);
	}

	/**
	 * Get the number of seeds from all houses.
	 */
	public int[] getSeeds() {
		return this.houses.stream()
				.mapToInt(House::getSeeds)
				.toArray();
	}

	/**
	 * Initialise the houses on insertion.
	 */
	@PrePersist
	private void initialise() {
		this.houses = new ArrayList<>();

		for (int k = 0; k < PITS_COUNT; k++) {
			if (k == SOUTH_HOME_INDEX || k == NORTH_HOME_INDEX) {
				this.houses.add(new House(k, 0));
			} else {
				this.houses.add(new House(k, SEEDS_INITIAL));
			}
		}
	}
}
