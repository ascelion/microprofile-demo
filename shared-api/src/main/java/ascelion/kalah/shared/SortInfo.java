package ascelion.kalah.shared;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;

@Builder
public class SortInfo {
	@NotBlank
	public final String path;
	@NotNull
	@Builder.Default
	public final SortKind direction = SortKind.ASC;
}
