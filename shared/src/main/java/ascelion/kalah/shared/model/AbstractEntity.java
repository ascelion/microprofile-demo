package ascelion.kalah.shared.model;

import ascelion.kalah.shared.POJO;
import ascelion.kalah.shared.validation.ValidateColumns;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ValidateColumns
public abstract class AbstractEntity extends POJO {

	@Id
	@GeneratedValue
	@Column(insertable = false, nullable = false, updatable = false, unique = true)
	private UUID id;

	@Override
	public final int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final AbstractEntity that = (AbstractEntity) obj;

		return Objects.equals(this.id, that.id);
	}

}