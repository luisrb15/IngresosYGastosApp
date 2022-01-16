package com.ingresosygastos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ingresosygastos.entities.Ingreso;

public interface IngresoRepository extends JpaRepository<Ingreso, String>{

	@Query("SELECT a from Ingreso a WHERE a.activo = true ")
	public List<Ingreso> buscarActivos();

	
}
