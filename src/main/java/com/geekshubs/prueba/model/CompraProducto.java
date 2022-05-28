package com.geekshubs.prueba.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "COMPRA_PRODUCTO")
public class CompraProducto {

	@Embeddable
	public static class CompraProductoId implements Serializable {
		private static final long serialVersionUID = -6951450243742367109L;

		@Column(nullable = false)
		private Integer producto_id;

		@Column(nullable = false)
		private Integer compra_id;

		public Integer getProducto_id() {
			return producto_id;
		}

		public void setProducto_id(Integer producto_id) {
			this.producto_id = producto_id;
		}

		public Integer getCompra_id() {
			return compra_id;
		}

		public void setCompra_id(Integer compra_id) {
			this.compra_id = compra_id;
		}

	}

	@EmbeddedId
	private CompraProductoId id = new CompraProductoId();

	@MapsId("producto_id")
	@ManyToOne
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;

	private Integer cantidad;
	private Float total;

	@JsonIgnore
	public CompraProductoId getId() {
		return id;
	}

	@JsonIgnore
	public void setId(CompraProductoId id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public void set(CompraProducto other) {
		this.cantidad = other.cantidad;
		this.total = other.total;
	}

}
