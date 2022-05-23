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

import com.geekshubs.prueba.db.categoria.CategoriaFilter;
import com.geekshubs.prueba.db.categoria.CategoriaRepository;
import com.geekshubs.prueba.model.Categoria;

@RestController
@RequestMapping("/venta/categoria")
public class CategoriaController {

	@Autowired
	CategoriaRepository repository;

	@GetMapping("")
	public ResponseEntity<List<Categoria>> list() {
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Categoria> save(@RequestBody Categoria client) {
		if (client != null) {
			return new ResponseEntity<>(repository.save(client), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> update(@PathVariable("id") Integer id, @RequestBody Categoria client) {
		try {
			if (client != null) {
				Categoria db = repository.getById(id);
				db.set(client);
				return new ResponseEntity<>(repository.save(db), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Categoria> delete(@PathVariable("id") Integer id) {
		repository.deleteById(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@PostMapping("/search")
	public ResponseEntity<List<Categoria>> search(@RequestBody CategoriaFilter filter) {
		return new ResponseEntity<>(repository.findAll(filter.toSpecification()), HttpStatus.OK);

	}

}
