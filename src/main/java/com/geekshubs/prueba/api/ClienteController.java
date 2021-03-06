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

import com.geekshubs.prueba.db.cliente.ClienteFilter;
import com.geekshubs.prueba.db.cliente.ClienteRepository;
import com.geekshubs.prueba.model.Cliente;


@RestController
@RequestMapping("/venta/cliente")
public class ClienteController {
	
	@Autowired
	ClienteRepository repository;
	
	@GetMapping("")
	public ResponseEntity<List<Cliente>> list() {
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
		if (cliente != null) {
			return new ResponseEntity<>(repository.save(cliente), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update(@PathVariable("id") Integer id, @RequestBody Cliente cliente) {
		try {
			if (cliente != null) {
				Cliente db = repository.getById(id);
				db.set(cliente);
				return new ResponseEntity<>(repository.save(db), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> delete(@PathVariable("id") Integer id) {
			repository.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<Cliente>> search(@RequestBody ClienteFilter filter) {
		return new ResponseEntity<>(repository.findAll(filter.toSpecification()), HttpStatus.OK);
		
	}
	

}
