package ascelion.kalah.shared.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.security.AccessController.doPrivileged;
import static org.apache.commons.lang3.reflect.FieldUtils.getFieldsWithAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.PrivilegedAction;

import javax.persistence.Column;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

/**
 * Validates all fields of type {@link String} of the annotated class against the length of their
 * &#64;{@link Column} annotation (if any).
 *
 * <p>
 * The length of <b>byte</b> sequence must be less than or equal to the {@code length} attribute of
 * the &#64;{@link Column} annotation.
 * </p>
 * <p>
 * The elements that are {@code null} are considered valid.
 * </p>
 * <p>
 * This is different than the &#64;{@link javax.validation.constraints.Size} constraint which
 * validates the length of <b>character</b> sequence.
 * </p>
 * <p>
 * Without this validation, the persistence layer would generate a vendor specific exception which
 * is not always caught properly. By using this annotation, any string that is too large (in <code>bytes</code>)
 * will fail as a constraint violation, similar to &#64;{@link Size}.
 * </p>
 */
@Constraint(validatedBy = ValidateColumns.Validator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface ValidateColumns {
	String message() default "{attribute.size.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String charset() default "";

	@NoArgsConstructor
	@AllArgsConstructor
	public class Validator implements ConstraintValidator<ValidateColumns, Object> {
		private Charset charset;
		private String message;

		@Override
		public void initialize(ValidateColumns annotation) {
			this.charset = annotation.charset().isEmpty()
					? Charset.defaultCharset()
					: Charset.forName(annotation.charset());
			this.message = annotation.message();
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			if (value == null) {
				return true;
			}

			return doPrivileged((PrivilegedAction<Boolean>) () -> validateFields(value, context));
		}

		@SneakyThrows
		private boolean validateFields(Object value, ConstraintValidatorContext context) {
			final HibernateConstraintValidatorContext ctx = context.unwrap(HibernateConstraintValidatorContext.class);
			boolean valid = true;

			for (final Field field : getFieldsWithAnnotation(value.getClass(), Column.class)) {
				if (field.getType() == String.class) {
					valid = checkFieldSize(value, field, ctx) && valid;
				}
			}

			context.disableDefaultConstraintViolation();

			return valid;
		}

		@SneakyThrows
		private boolean checkFieldSize(Object object, Field field, HibernateConstraintValidatorContext context) {
			field.setAccessible(true);

			final String value = (String) field.get(object);

			if (value == null) {
				return true;
			}

			final int bytes = value.getBytes(this.charset).length;
			final Column column = field.getAnnotation(Column.class);

			if (bytes <= column.length()) {
				return true;
			}

			context.addMessageParameter("actual", bytes)
					.addMessageParameter("max", column.length())
					.buildConstraintViolationWithTemplate(this.message)
					.addPropertyNode(field.getName())
					.addConstraintViolation();

			return false;
		}
	}
}
