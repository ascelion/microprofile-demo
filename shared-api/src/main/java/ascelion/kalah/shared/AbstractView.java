package ascelion.kalah.shared;

import java.util.UUID;

import javax.ws.rs.PathParam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractView extends POJO {
	@PathParam("id")
	private UUID id;
}
