package com.geekshubs.prueba.db.categoria;

import org.springframework.data.jpa.domain.Specification;

import com.geekshubs.prueba.db.SpecificationUtils;
import com.geekshubs.prueba.model.Categoria;

public class CategoriaFilter {

	private String nombre;
	private String descripcion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Specification<Categoria> toSpecification() {
		Specification<Categoria> result = null;
		if (nombre != null) {
			result = SpecificationUtils.and(result, (categoria, cq, cb) -> cb.like(categoria.get("nombre"), nombre));
		}
		if (descripcion != null) {
			result = SpecificationUtils.and(result,
					(categoria, cq, cb) -> cb.like(categoria.get("descripcion"), descripcion));
		}
		return result;
	}

}
