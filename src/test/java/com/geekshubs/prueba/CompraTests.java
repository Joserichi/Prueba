package com.geekshubs.prueba;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.geekshubs.prueba.api.CategoriaController;
import com.geekshubs.prueba.api.ClienteController;
import com.geekshubs.prueba.api.CompraController;
import com.geekshubs.prueba.api.ProductoController;
import com.geekshubs.prueba.model.Categoria;
import com.geekshubs.prueba.model.Cliente;
import com.geekshubs.prueba.model.Compra;
import com.geekshubs.prueba.model.CompraProducto;
import com.geekshubs.prueba.model.Producto;

@SpringBootTest
class CompraTests {
	
	@Autowired
	CompraController compras;
	@Autowired
	ClienteController clientes;
	@Autowired
	ProductoController productos;
	@Autowired
	CategoriaController categorias;

	@Test
	@Transactional
	void testController() {
		populateDB();
		
		//Test save
		Compra compra = generateTestEntity();
		compras.save(compra);
		ResponseEntity<List<Compra>> response = compras.list();
		assertEquals(1, response.getBody().size());
		Compra db = response.getBody().get(0);
		assertEquals(compra.getMetodo_pago(), db.getMetodo_pago());
		assertEquals(compra.getProductos().size(), db.getProductos().size());
		assertEquals(compra.getProductos().get(1).getProducto().getProducto_id(),compra.getProductos().get(1).getProducto().getProducto_id());
		
		//Test update self
		compra.setEstado("Entregado");
		compras.update(compra.getCompra_id(), compra);
		response = compras.list();
		assertEquals(1, response.getBody().size());
		db = response.getBody().get(0);
		assertEquals(compra.getEstado(), db.getEstado());
		
		//Test update child
		CompraProducto item = compra.getProductos().get(0);
		item.setCantidad(1);
		compras.updateProducto(compra.getCompra_id(), item.getProducto().getProducto_id(), item);
		response = compras.list();
		assertEquals(1, response.getBody().size());
		db = response.getBody().get(0);
		assertEquals(compra.getProductos().get(0).getCantidad(), db.getProductos().get(0).getCantidad());
		
		//Test delete child
		compras.deleteProducto(compra.getCompra_id(), item.getProducto().getProducto_id());
		compra.getProductos().remove(item);
		response = compras.list();
		assertEquals(1, response.getBody().size());
		db = response.getBody().get(0);
		assertEquals(compra.getProductos().size(), db.getProductos().size());
		
		//Test delete
		compras.delete(compra.getCompra_id());
		response = compras.list();
		assertEquals(0, response.getBody().size());
	}
	
	private void populateDB() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Alex");
		cliente.setApellidos("");
		cliente.setTelefono("612345789");
		cliente.setEmail("alex@gmail.com");
		clientes.save(cliente);
		
		Categoria categoria = new Categoria();
		categoria.setNombre("Comestibles");
		categoria.setDescripcion("");
		categorias.save(categoria);
		
		Producto producto = new Producto();
		producto.setNombre("Patatas");
		producto.setPrecio(1.99F);
		producto.setCantidad(1000);
		producto.setCategoria(categoria);
		productos.save(producto);
		
		Producto producto2 = new Producto();
		producto2.setNombre("Heura");
		producto2.setPrecio(3.99F);
		producto2.setCantidad(100);
		producto2.setCategoria(categoria);
		productos.save(producto2);
	}
	
	private Compra generateTestEntity() {
		Compra compra = new Compra();
		compra.setFecha(new Date());
		compra.setMetodo_pago("Efectivo");
		compra.setEstado("En reparto");
		Cliente cliente = new Cliente();
		cliente.setCliente_id(1);
		compra.setCliente(cliente);
		List<CompraProducto> items = new ArrayList<>();
		Producto producto = new Producto();
		producto.setProducto_id(1);
		CompraProducto item = new CompraProducto();
		item.setProducto(producto);
		item.setCantidad(2);
		item.setTotal(3.98F);
		items.add(item);
		Producto producto2 = new Producto();
		producto2.setProducto_id(2);
		CompraProducto item2 = new CompraProducto();
		item2.setProducto(producto2);
		item2.setCantidad(3);
		item2.setTotal(7.98F);
		items.add(item2);
		compra.setProductos(items);
		return compra;
	}

}
