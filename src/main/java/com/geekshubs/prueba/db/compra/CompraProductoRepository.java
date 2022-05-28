package com.geekshubs.prueba.db.compra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.geekshubs.prueba.model.CompraProducto;
import com.geekshubs.prueba.model.CompraProducto.CompraProductoId;

@Repository
public interface CompraProductoRepository extends JpaRepository<CompraProducto, CompraProductoId>, JpaSpecificationExecutor<CompraProducto>{

}
