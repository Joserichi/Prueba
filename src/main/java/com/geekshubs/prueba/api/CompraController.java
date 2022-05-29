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

import com.geekshubs.prueba.db.compra.CompraFilter;
import com.geekshubs.prueba.db.compra.CompraProductoRepository;
import com.geekshubs.prueba.db.compra.CompraRepository;
import com.geekshubs.prueba.model.Compra;
import com.geekshubs.prueba.model.CompraProducto;
import com.geekshubs.prueba.model.CompraProducto.CompraProductoId;

@RestController
@RequestMapping("/venta/compra")
public class CompraController {

	@Autowired
	CompraRepository repository;

	@Autowired
	CompraProductoRepository childRepository;

	@GetMapping("")
	public ResponseEntity<List<Compra>> list() {
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Compra> save(@RequestBody Compra compra) {
		if (compra != null) {
			compra = repository.save(compra);
			if (compra.getProductos() != null) {
				for (CompraProducto child : compra.getProductos()) {
					child.getId().setCompra_id(compra.getCompra_id());
					childRepository.save(child);
				}
			}
			return new ResponseEntity<>(compra, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Compra> update(@PathVariable("id") Integer id, @RequestBody Compra compra) {
		try {
			if (compra != null) {
				Compra db = repository.getById(id);
				db.set(compra);
				db = repository.save(db);
				return new ResponseEntity<>(db, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/{id}/producto/")
	public ResponseEntity<CompraProducto> addProducto(@PathVariable("id") Integer id, @RequestBody CompraProducto producto) {
		if (!repository.existsById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		if (producto != null) {
			producto.getId().setCompra_id(id);
			producto = childRepository.save(producto);
			return new ResponseEntity<>(producto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}/producto/{pid}")
	public ResponseEntity<CompraProducto> updateProducto(@PathVariable("id") Integer id, @PathVariable("pid") Integer pid, @RequestBody CompraProducto producto) {
		if (!repository.existsById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		try {
			if (producto != null) {
				CompraProductoId dbId = new CompraProductoId();
				dbId.setCompra_id(id);
				dbId.setProducto_id(pid);
				CompraProducto db = childRepository.getById(dbId);
				db.set(producto);
				db = childRepository.save(db);
				return new ResponseEntity<>(db, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}/producto/{pid}")
	public ResponseEntity<CompraProducto> deleteProducto(@PathVariable("id") Integer id, @PathVariable("pid") Integer pid) {
		if (!repository.existsById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		CompraProductoId dbId = new CompraProductoId();
		dbId.setCompra_id(id);
		dbId.setProducto_id(pid);
		childRepository.deleteById(dbId);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Compra> delete(@PathVariable("id") Integer id) {
		repository.deleteById(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@PostMapping("/search")
	public ResponseEntity<List<Compra>> search(@RequestBody CompraFilter filter) {
		return new ResponseEntity<>(repository.findAll(filter.toSpecification()), HttpStatus.OK);
	}

}
