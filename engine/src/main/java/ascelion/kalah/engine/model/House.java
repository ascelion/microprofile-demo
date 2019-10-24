package ascelion.kalah.engine.model;

import javax.persistence.Embeddable;

import ascelion.kalah.engine.exceptions.HouseEmptyException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class House {
	/**
	 * The position of this house on the board, also used for ordering in the SELECT.
	 */
	@Getter
	private int index;
	/**
	 * The number of seeds in this house.
	 */
	@Getter
	private int seeds;

	/**
	 * Drop one seed into this house.
	 */
	public void dropSeed() {
		this.seeds++;
	}

	/**
	 * Pick one seed from this house.
	 *
	 * @throws HouseEmptyException if the house is empty.
	 */
	public void pickSeed() {
		if (this.seeds == 0) {
			// XXX this should never happen in the current implementation
			throw new IllegalStateException("Picking from empty house");
		}

		this.seeds--;
	}

	/**
	 * Move all seeds from the given house to this one.
	 */
	public void takeFrom(House house) {
		this.seeds += house.take();
	}

	/**
	 * Take all the seeds from this house.
	 */
	public int take() {
		try {
			return this.seeds;
		} finally {
			this.seeds = 0;
		}
	}

	/**
	 * Check if this house is empty.
	 */
	public boolean isEmpty() {
		return this.seeds == 0;
	}
}
