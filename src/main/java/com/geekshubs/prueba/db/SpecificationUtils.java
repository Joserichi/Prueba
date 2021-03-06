package com.geekshubs.prueba.db;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {
	
	public static <T> Specification<T> and(Specification<T> one, Specification<T> other) {
		if (one != null) {
			return one.and(other);
		} else {
			return other;
		}
	}

}
