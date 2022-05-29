package com.geekshubs.prueba.db.producto;

import org.springframework.data.jpa.domain.Specification;

import com.geekshubs.prueba.db.SpecificationUtils;
import com.geekshubs.prueba.db.categoria.CategoriaFilter;
import com.geekshubs.prueba.model.Categoria;
import com.geekshubs.prueba.model.Producto;

public class ProductoFilter {

	private Integer categoria_id;
	private CategoriaFilter categoria = new CategoriaFilter();
	private String nombre;
	private Float precio_from;
	private Float precio_to;
	private Integer cantidad_from;
	private Integer cantidad_to;
	
	public Integer getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(Integer categoria_id) {
		this.categoria_id = categoria_id;
	}

	public CategoriaFilter getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaFilter categoria) {
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getPrecio_from() {
		return precio_from;
	}

	public void setPrecio_from(Float precio_from) {
		this.precio_from = precio_from;
	}

	public Float getPrecio_to() {
		return precio_to;
	}

	public void setPrecio_to(Float precio_to) {
		this.precio_to = precio_to;
	}

	public Integer getCantidad_from() {
		return cantidad_from;
	}

	public void setCantidad_from(Integer cantidad_from) {
		this.cantidad_from = cantidad_from;
	}

	public Integer getCantidad_to() {
		return cantidad_to;
	}

	public void setCantidad_to(Integer cantidad_to) {
		this.cantidad_to = cantidad_to;
	}

	public Specification<Producto> toSpecification() {
		Specification<Producto> result = null;
		if (nombre != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.like(producto.get("nombre"), nombre));
		}
		if (precio_from != null) {
			result = SpecificationUtils.and(result,
					(producto, cq, cb) -> cb.ge(producto.get("precio"), precio_from));
		}
		if (precio_to != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.le(producto.get("precio"), precio_to));
		}
		if (cantidad_from != null) {
			result = SpecificationUtils.and(result,
					(producto, cq, cb) -> cb.ge(producto.get("cantidad"), cantidad_from));
		}
		if (cantidad_to != null) {
			result = SpecificationUtils.and(result,
					(producto, cq, cb) -> cb.le(producto.get("cantidad"), cantidad_to));
		}
		if (categoria_id != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.equal(producto.get("categoria"), categoria_id));
		}
		if (categoria.getNombre() != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.like(producto.get("categoria").get("nombre"), categoria.getNombre()));
		}
		if (categoria.getDescripcion() != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.like(producto.get("categoria").get("descripcion"), categoria.getDescripcion()));
		}
		return result;
	}

}
