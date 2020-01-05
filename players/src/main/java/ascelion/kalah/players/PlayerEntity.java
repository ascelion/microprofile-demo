package ascelion.kalah.players;

import ascelion.kalah.shared.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "players")
@NoArgsConstructor
@SuperBuilder
public class PlayerEntity extends AbstractEntity<PlayerEntity> {

	@Getter
	@Setter
	@Min(0)
	private int score;

	public void add(int score) {
		this.score += score;
	}
}
