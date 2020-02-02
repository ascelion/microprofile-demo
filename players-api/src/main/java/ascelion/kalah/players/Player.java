package ascelion.kalah.players;

import ascelion.kalah.shared.AbstractView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
public class Player extends AbstractView {
	private int score;
}
