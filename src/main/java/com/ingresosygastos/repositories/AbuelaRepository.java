package com.ingresosygastos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ingresosygastos.entities.Abuela;

public interface AbuelaRepository extends JpaRepository<Abuela, Long>{

	@Query("SELECT a from Abuela a WHERE a.activo = true ")
	public List<Abuela> buscarActivos();
	
}
