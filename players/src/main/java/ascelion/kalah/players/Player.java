package ascelion.kalah.players;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import ascelion.kalah.shared.model.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "players")
@NoArgsConstructor
@SuperBuilder
public class Player extends AbstractEntity<Player> {

	@Getter
	@Min(0)
	private int score;

	public void add(int score) {
		this.score += score;
	}
}
