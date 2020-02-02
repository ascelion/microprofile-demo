package ascelion.kalah.shared.bbm;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface BeanToBean<A, B> {

	B atob(A a);

	A btoa(B b);

	void atob(A a, @MappingTarget B b);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void atobPatch(A a, @MappingTarget B b);
}
