package ascelion.kalah.shared;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SortKind {
	ASC,
	DESC,
	;

	@JsonCreator
	static public SortKind fromText(String text) {
		return isEmpty(text) ? null : valueOf(text.toUpperCase());
	}

	@JsonValue
	public final String value() {
		return name().toLowerCase();
	}
}
