package com.geekshubs.prueba.db.compra;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.geekshubs.prueba.db.SpecificationUtils;
import com.geekshubs.prueba.db.cliente.ClienteFilter;
import com.geekshubs.prueba.model.Compra;

public class CompraFilter {

	private Integer cliente_id;
	private ClienteFilter cliente;

	private Date fecha_to;
	private Date fecha_from;
	private String metodo_pago;
	private String estado;

	private CompraProductoFilter producto;

	public Integer getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Integer cliente_id) {
		this.cliente_id = cliente_id;
	}

	public ClienteFilter getCliente() {
		return cliente;
	}

	public void setCliente(ClienteFilter cliente) {
		this.cliente = cliente;
	}

	public Date getFecha_to() {
		return fecha_to;
	}

	public void setFecha_to(Date fecha_to) {
		this.fecha_to = fecha_to;
	}

	public Date getFecha_from() {
		return fecha_from;
	}

	public void setFecha_from(Date fecha_from) {
		this.fecha_from = fecha_from;
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

	public CompraProductoFilter getProducto() {
		return producto;
	}

	public void setProductos(CompraProductoFilter producto) {
		this.producto = producto;
	}

	public Specification<Compra> toSpecification() {
		Specification<Compra> result = null;
		if (cliente_id != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.equal(compra.get("cliente"), cliente_id));
		}
		
		if (cliente != null) {
			result = SpecificationUtils.and(result, getClientSubFilter());
		}
		
		if (fecha_from != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.greaterThanOrEqualTo(compra.<Date>get("fecha"), fecha_from));
		}
		if (fecha_to != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.lessThanOrEqualTo(compra.<Date>get("fecha"), fecha_to));
		}
		
		if (metodo_pago != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.get("metodo_pago"), metodo_pago));
		}
		
		if (estado != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.get("estado"), estado));
		}
		
		if (producto != null) {
			result = SpecificationUtils.and(result, getProductoSubFilter());
		}
		
		return result;
	}
	
	private Specification<Compra> getClientSubFilter() {
		Specification<Compra> result = null;
		
		if (cliente.getNombre() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.get("cliente").get("nombre"), this.cliente.getNombre()));
		}
		if (cliente.getApellidos() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.get("cliente").get("apellidos"), this.cliente.getApellidos()));
		}
		if (cliente.getTelefono() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.get("cliente").get("telefono"), this.cliente.getTelefono()));
		}
		if (cliente.getFecha_nacimiento_from() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.greaterThanOrEqualTo(compra.get("cliente").<Date>get("fecha_nacimiento"), this.cliente.getFecha_nacimiento_from()));
		}
		if (cliente.getFecha_nacimiento_to() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.lessThanOrEqualTo(compra.get("cliente").<Date>get("fecha_nacimiento"), this.cliente.getFecha_nacimiento_to()));
		}
		if (cliente.getEmail() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.get("cliente").get("email"), this.cliente.getEmail()));
		}
		
		return result;
	}
	
	private Specification<Compra> getProductoSubFilter() {
		Specification<Compra> result = null; 
		
		if (producto.getProducto_id() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.equal(compra.join("productos").get("producto"), producto.getProducto_id()));
		}
		
		if (producto.getProducto() != null) {
			result = SpecificationUtils.and(result, getProductoSubSubFilter());
		}

		if (producto.getCantidad_from() != null) {
			result = SpecificationUtils.and(result,
					(compra, cq, cb) -> cb.ge(compra.join("productos").get("cantidad"), producto.getCantidad_from()));
		}
		if (producto.getCantidad_to() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.le(compra.join("productos").get("cantidad"), producto.getCantidad_to()));
		}
		
		if (producto.getTotal_from() != null) {
			result = SpecificationUtils.and(result,
					(compra, cq, cb) -> cb.ge(compra.join("productos").get("total"), producto.getTotal_from()));
		}
		if (producto.getTotal_to() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.le(compra.join("productos").get("total"), producto.getTotal_to()));
		}
		
		return result;
	}
	
	private Specification<Compra> getProductoSubSubFilter() {
		Specification<Compra> result = null; 
		
		if (producto.getProducto().getNombre() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.join("productos").get("producto").get("nombre"), this.producto.getProducto().getNombre()));
		}
		if (producto.getProducto().getPrecio_from() != null) {
			result = SpecificationUtils.and(result,
					(compra, cq, cb) -> cb.ge(compra.join("productos").get("producto").get("precio"), this.producto.getProducto().getPrecio_from()));
		}
		if (producto.getProducto().getPrecio_to() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.le(compra.join("productos").get("producto").get("precio"), this.producto.getProducto().getPrecio_to()));
		}
		if (producto.getProducto().getCantidad_from() != null) {
			result = SpecificationUtils.and(result,
					(compra, cq, cb) -> cb.ge(compra.join("productos").get("producto").get("cantidad"), this.producto.getProducto().getCantidad_from()));
		}
		if (producto.getProducto().getCantidad_to() != null) {
			result = SpecificationUtils.and(result,
					(compra, cq, cb) -> cb.le(compra.join("productos").get("producto").get("cantidad"), this.producto.getProducto().getCantidad_to()));
		}
		if (producto.getProducto().getCategoria_id() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.equal(compra.join("productos").get("producto").get("categoria"), this.producto.getProducto().getCategoria_id()));
		}
		if (producto.getProducto().getCategoria().getNombre() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.join("productos").get("producto").get("categoria").get("nombre"), this.producto.getProducto().getCategoria().getNombre()));
		}
		if (producto.getProducto().getCategoria().getDescripcion() != null) {
			result = SpecificationUtils.and(result, (compra, cq, cb) -> cb.like(compra.join("productos").get("producto").get("categoria").get("descripcion"), this.producto.getProducto().getCategoria().getDescripcion()));
		}
		
		return result;
	}

}
