package com.geekshubs.prueba.db.cliente;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.geekshubs.prueba.model.Cliente;

public class ClienteFilter {

	private String nombre;
	private String apellidos;
	private String telefono;
	private Date fecha_nacimiento_from;
	private Date fecha_nacimiento_to;
	private String email;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFecha_nacimiento_from() {
		return fecha_nacimiento_from;
	}

	public void setFecha_nacimiento_from(Date fecha_nacimiento_from) {
		this.fecha_nacimiento_from = fecha_nacimiento_from;
	}

	public Date getFecha_nacimiento_to() {
		return fecha_nacimiento_to;
	}

	public void setFecha_nacimiento_to(Date fecha_nacimiento_to) {
		this.fecha_nacimiento_to = fecha_nacimiento_to;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Specification<Cliente> toSpecification() {
		Specification<Cliente> result = null;
		if (nombre != null) {
			result = and(result, (cliente, cq, cb) -> cb.like(cliente.get("nombre"), nombre));
		}
		if (apellidos != null) {
			result = and(result, (cliente, cq, cb) -> cb.like(cliente.get("apellidos"), apellidos));
		}
		if (telefono != null) {
			result = and(result, (cliente, cq, cb) -> cb.like(cliente.get("telefono"), telefono));
		}
		if (fecha_nacimiento_from != null) {
			result = and(result, (cliente, cq, cb) -> cb.greaterThanOrEqualTo(cliente.<Date>get("fecha_nacimiento"), fecha_nacimiento_from));
		}
		if (fecha_nacimiento_to != null) {
			result = and(result, (cliente, cq, cb) -> cb.lessThanOrEqualTo(cliente.<Date>get("fecha_nacimiento"), fecha_nacimiento_to));
		}
		if (email != null) {
			result = and(result, (cliente, cq, cb) -> cb.like(cliente.get("email"), email));
		}
		return result;
	}
	
	//XXX Clase de utils?
	private Specification<Cliente> and(Specification<Cliente> one, Specification<Cliente> other) {
		if (one != null) {
			return one.and(other);
		} else {
			return other;
		}
	}

}
