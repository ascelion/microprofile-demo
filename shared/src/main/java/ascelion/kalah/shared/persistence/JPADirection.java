package ascelion.kalah.shared.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

public enum JPADirection {
	ASC {

		@Override
		public Order order(CriteriaBuilder cb, Expression<?> expression) {
			return cb.asc(expression);
		}
	},
	DESC {

		@Override
		public Order order(CriteriaBuilder cb, Expression<?> expression) {
			return cb.desc(expression);
		}
	};

	static public JPADirection valueOf(Enum<?> e) {
		return valueOf(e.name().toUpperCase());
	}

	static public Order orderBy(CriteriaBuilder cb, Expression<?> expression, Enum<?> e) {
		return valueOf(e).order(cb, expression);
	}

	public abstract Order order(CriteriaBuilder cb, Expression<?> expression);
}
