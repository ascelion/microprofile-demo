package ascelion.kalah.engine.exceptions;

import ascelion.kalah.engine.model.Board;
import ascelion.kalah.engine.model.House;

import static java.lang.String.format;

import lombok.Getter;
import lombok.NonNull;

/**
 * Thrown when trying to pick seeds from an empty house.
 */
@Getter
public class HouseEmptyException extends RuntimeException {
	static private final String MESSAGE = "GAME[%s]: cannot pick from empty house %d";

	static private String buildMessage(@NonNull Board board, @NonNull House house) {
		return format(MESSAGE, board.getId(), house.getIndex() + 1);
	}

	public HouseEmptyException(Board board, House house) {
		super(buildMessage(board, house));
	}
}
