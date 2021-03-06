package ascelion.kalah.shared;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonAutoDetect(
		creatorVisibility = Visibility.ANY,
		fieldVisibility = Visibility.ANY,
		getterVisibility = Visibility.NONE,
		isGetterVisibility = Visibility.NONE,
		setterVisibility = Visibility.NONE)
@JsonInclude(content = Include.NON_NULL, value = Include.NON_NULL)
@SuperBuilder
@NoArgsConstructor
public abstract class POJO {
}
