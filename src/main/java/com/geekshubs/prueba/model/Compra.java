package com.geekshubs.prueba.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "COMPRA")
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer compra_id;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	private Date fecha;
	private String metodo_pago;
	private String estado;

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "compra_id", nullable=false, updatable=false, insertable=false)
	private List<CompraProducto> productos;

	public Integer getCompra_id() {
		return compra_id;
	}

	public void setCompra_id(Integer compra_id) {
		this.compra_id = compra_id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<CompraProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<CompraProducto> productos) {
		this.productos = productos;
	}

	public void set(Compra other) {
		this.cliente = other.cliente;
		this.fecha = other.fecha;
		this.metodo_pago = other.metodo_pago;
		this.estado = other.estado;
	}
}
