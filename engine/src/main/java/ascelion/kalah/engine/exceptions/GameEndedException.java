package ascelion.kalah.engine.exceptions;

import ascelion.kalah.engine.model.Board;

import static java.lang.String.format;

import lombok.NonNull;

/**
 * Thrown when trying to continue an finished board.
 */
public class GameEndedException extends RuntimeException {
	static public final String MESSAGE = "GAME[%s]: already ended";

	static private String buildMessage(@NonNull Board board) {
		return format(MESSAGE, board.getId());
	}

	public GameEndedException(Board board) {
		super(buildMessage(board));
	}
}
