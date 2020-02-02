package ascelion.kalah.players;

import ascelion.kalah.shared.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter
@Setter
public class PlayerEntity extends AbstractEntity {

	@Min(0)
	private int score;

	public void add(int score) {
		this.score += score;
	}
}
