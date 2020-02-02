package ascelion.kalah.shared.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

/**
 * The annotated element needs to contain valid attribute name of a JPA entity.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@code String}</li>
 * <li>{@code Collection<String>}</li>
 * <li>Array of {@code String})</li>
 * </ul>
 * </p>
 * <p>
 * {@code null} elements are considered valid.
 * </p>
 */
@Constraint(validatedBy = AttributePath.Validator.class)
@Target({ PARAMETER, FIELD })
@Retention(RUNTIME)
public @interface AttributePath {
	Class<?> value();

	String message() default "{attribute.path.invalid}";

	Class<?>[] groups() default {};

	Class<?>[] payload() default {};

	@RequestScoped
	@RequiredArgsConstructor(onConstructor_ = @Inject)
	public class Validator implements ConstraintValidator<AttributePath, Object> {

		private final EntityManager em;
		private String message = "{attribute.path.invalid}";

		private String entityName;
		private Root<?> root;

		@Override
		public void initialize(AttributePath annotation) {
			this.message = annotation.message();

			final Class<?> entityType = annotation.value();

			initialize(entityType);
		}

		public void initialize(Class<?> entityType) {
			this.entityName = this.em.getMetamodel().entity(entityType).getName();
			this.root = this.em.getCriteriaBuilder().createQuery().from(entityType);
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			if (value == null) {
				return true;
			}

			if (value instanceof String[]) {
				return validate(asList((String[]) value), context);
			}
			if (value instanceof Collection) {
				return validate((Collection<String>) value, context);
			}

			return validate(asList((String) value), context);
		}

		private boolean validate(Collection<String> values, ConstraintValidatorContext context) {
			final HibernateConstraintValidatorContext ctx = context.unwrap(HibernateConstraintValidatorContext.class);
			boolean valid = true;

			for (final String value : values) {
				valid = checkValue(value, ctx) && valid;
			}

			context.disableDefaultConstraintViolation();

			return valid;
		}

		private boolean checkValue(String value, HibernateConstraintValidatorContext ctx) {
			final String[] items = value.split("\\.");

			if (items.length == 0) {
				return false;
			}

			Path<?> next = this.root;

			for (int k = 0; k < items.length; k++) {
				try {
					next = next.get(items[k]);
				} catch (IllegalArgumentException | IllegalStateException e) {
					final String invalid = stream(items).limit(k + 1).collect(joining("."));

					ctx.addMessageParameter("entity", this.entityName)
							.addMessageParameter("path", invalid)
							.buildConstraintViolationWithTemplate(this.message)
							.addConstraintViolation();

					return false;
				}
			}

			return true;
		}

	}
}
