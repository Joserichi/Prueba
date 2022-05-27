package com.geekshubs.prueba.api;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geekshubs.prueba.db.producto.ProductoFilter;
import com.geekshubs.prueba.db.producto.ProductoRepository;
import com.geekshubs.prueba.model.Producto;

@RestController
@RequestMapping("/venta/producto")
public class ProductoController {

	@Autowired
	ProductoRepository repository;

	@GetMapping("")
	public ResponseEntity<List<Producto>> list() {
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Producto> save(@RequestBody Producto producto) {
		if (producto != null) {
			producto = repository.save(producto);
			//Hibernate no los actualiza, correcto, pero la respuesta los muestra como se enviaron, null para no confundir al usuario de la api
			producto.getCategoria().setNombre(null);
			producto.getCategoria().setDescripcion(null);
			return new ResponseEntity<>(producto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Producto> update(@PathVariable("id") Integer id, @RequestBody Producto producto) {
		try {
			if (producto != null) {
				Producto db = repository.getById(id);
				db.set(producto);
				return new ResponseEntity<>(repository.save(db), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Producto> delete(@PathVariable("id") Integer id) {
		repository.deleteById(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@PostMapping("/search")
	public ResponseEntity<List<Producto>> search(@RequestBody ProductoFilter filter) {
		return new ResponseEntity<>(repository.findAll(filter.toSpecification()), HttpStatus.OK);

	}

}
