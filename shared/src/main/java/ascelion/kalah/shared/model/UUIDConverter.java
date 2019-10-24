package ascelion.kalah.shared.model;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, UUID> {

	@Override
	public UUID convertToDatabaseColumn(UUID attribute) {
		return attribute;
	}

	@Override
	public UUID convertToEntityAttribute(UUID dbData) {
		return dbData;
	}
}
