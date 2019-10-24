package ascelion.kalah.engine.exceptions;

import ascelion.kalah.engine.model.Board;
import ascelion.kalah.engine.model.House;

import static java.lang.String.format;

import lombok.Getter;
import lombok.NonNull;

/**
 * Thrown when a player tries to pick seeds from a house of the adversary.
 */
@Getter
public class HouseInvalidException extends RuntimeException {
	static private final String MESSAGE = "GAME[%s]: player %s cannot select house %d";

	static private String buildMessage(@NonNull Board board, @NonNull House house) {
		return format(MESSAGE, board.getId(), board.getCurrentPlayer(), house.getIndex() + 1);
	}

	public HouseInvalidException(Board board, House house) {
		super(buildMessage(board, house));
	}
}
