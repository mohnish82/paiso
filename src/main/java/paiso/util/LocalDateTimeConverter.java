package paiso.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime date) {
		return Timestamp.from(date.atZone(ZoneOffset.systemDefault()).toInstant());
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp date) {
		return LocalDateTime.ofInstant(date.toInstant() , ZoneOffset.systemDefault());
	}
}
