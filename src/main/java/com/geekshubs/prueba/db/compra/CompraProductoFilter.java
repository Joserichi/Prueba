package com.geekshubs.prueba.db.compra;

import org.springframework.data.jpa.domain.Specification;

import com.geekshubs.prueba.db.SpecificationUtils;
import com.geekshubs.prueba.db.producto.ProductoFilter;
import com.geekshubs.prueba.model.CompraProducto;

public class CompraProductoFilter {

	private Integer producto_id;
	private ProductoFilter producto;

	private Integer cantidad_to;
	private Integer cantidad_from;
	private Float total_to;
	private Float total_from;

	public Integer getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(Integer producto_id) {
		this.producto_id = producto_id;
	}

	public ProductoFilter getProducto() {
		return producto;
	}

	public void setProducto(ProductoFilter producto) {
		this.producto = producto;
	}

	public Integer getCantidad_to() {
		return cantidad_to;
	}

	public void setCantidad_to(Integer cantidad_to) {
		this.cantidad_to = cantidad_to;
	}

	public Integer getCantidad_from() {
		return cantidad_from;
	}

	public void setCantidad_from(Integer cantidad_from) {
		this.cantidad_from = cantidad_from;
	}

	public Float getTotal_to() {
		return total_to;
	}

	public void setTotal_to(Float total_to) {
		this.total_to = total_to;
	}

	public Float getTotal_from() {
		return total_from;
	}

	public void setTotal_from(Float total_from) {
		this.total_from = total_from;
	}

	public Specification<CompraProducto> toSpecification() {
		Specification<CompraProducto> result = null;
		if (producto_id != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.equal(producto.get("producto"), producto_id));
		}
		
		if (producto != null) {
			result = SpecificationUtils.and(result, getSubFilter());
		}

		if (cantidad_from != null) {
			result = SpecificationUtils.and(result,
					(producto, cq, cb) -> cb.ge(producto.get("cantidad"), cantidad_from));
		}
		if (cantidad_to != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.le(producto.get("cantidad"), cantidad_to));
		}
		
		if (total_from != null) {
			result = SpecificationUtils.and(result,
					(producto, cq, cb) -> cb.ge(producto.get("total"), total_from));
		}
		if (total_to != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.le(producto.get("total"), total_from));
		}
		return result;
	}
	
	private Specification<CompraProducto> getSubFilter() {
		Specification<CompraProducto> result = null;
		
		if (producto.getNombre() != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.like(producto.get("producto").get("nombre"), this.producto.getNombre()));
		}
		if (producto.getPrecio_from() != null) {
			result = SpecificationUtils.and(result,
					(producto, cq, cb) -> cb.ge(producto.get("producto").get("precio"), this.producto.getPrecio_from()));
		}
		if (producto.getPrecio_to() != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.le(producto.get("producto").get("precio"), this.producto.getPrecio_to()));
		}
		if (producto.getCantidad_from() != null) {
			result = SpecificationUtils.and(result,
					(producto, cq, cb) -> cb.ge(producto.get("producto").get("cantidad"), this.producto.getCantidad_from()));
		}
		if (producto.getCantidad_to() != null) {
			result = SpecificationUtils.and(result,
					(producto, cq, cb) -> cb.le(producto.get("producto").get("cantidad"), this.producto.getCantidad_to()));
		}
		if (producto.getCategoria_id() != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.equal(producto.get("producto").get("categoria"), this.producto.getCategoria_id()));
		}
		if (producto.getCategoria().getNombre() != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.like(producto.get("producto").get("categoria").get("nombre"), this.producto.getCategoria().getNombre()));
		}
		if (producto.getCategoria().getDescripcion() != null) {
			result = SpecificationUtils.and(result, (producto, cq, cb) -> cb.like(producto.get("producto").get("categoria").get("descripcion"), this.producto.getCategoria().getDescripcion()));
		}
		
		return result;
	}

}
