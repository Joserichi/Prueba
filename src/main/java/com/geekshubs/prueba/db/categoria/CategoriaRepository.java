package com.geekshubs.prueba.db.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.geekshubs.prueba.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>, JpaSpecificationExecutor<Categoria>{

}
