package com.ingresosygastos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ingresosygastos.entities.Abuela;
import com.ingresosygastos.entities.Ingreso;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.repositories.IngresoRepository;

@Service
public class IngresoService {

	@Autowired
	private IngresoRepository ingresoRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Ingreso guardar(Integer montoCobrado, Abuela abuela) throws WebException {

		validar(montoCobrado);

		Ingreso ingreso = new Ingreso();

		ingreso.setMontoCobrado(montoCobrado);
		ingreso.setAbuela(abuela);
		ingreso.setCreado(new Date());

		ingresoRepository.save(ingreso);

		return ingreso;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Ingreso modificar(String id, Integer montoCobrado, Abuela abuela) throws WebException {


		validar(montoCobrado);
		
		Ingreso ingreso = findById(id);

		ingreso.setMontoCobrado(montoCobrado);
		ingreso.setAbuela(abuela);
		ingreso.setCreado(new Date());

		return ingreso;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Ingreso eliminar(String id) throws WebException {
		Ingreso ingreso = findById(id);
		ingreso.setActivo(false);
		return ingreso;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Ingreso alta(String id) throws WebException {
		Ingreso ingreso = findById(id);
		ingreso.setActivo(true);
		return ingreso;
	}

	@Transactional(readOnly = true)
	public List<Ingreso> listarActivos() throws WebException {
		try {
			return ingresoRepository.buscarActivos();
		} catch (Exception e) {
			throw new WebException("No se pudo listar las ingresos.");
		}
	}

	@Transactional(readOnly = true)
	public List<Ingreso> listarTodos() throws WebException {
		try {
			return ingresoRepository.findAll();
		} catch (Exception e) {
			throw new WebException("No se pudo listar las ingresos.");
		}
	}

	public void validar(Integer montoCobrado)
			throws WebException {

		if (montoCobrado == null || montoCobrado.equals(0)) {
			throw new WebException("Debe tener un nombre valido");
		}
	}

	public Ingreso findById(String id) throws WebException {
		Optional<Ingreso> respuesta = ingresoRepository.findById(id);
		if (respuesta.isPresent()) {
			Ingreso ingreso = respuesta.get();
			return ingreso;
		} else {
			throw new WebException("No se pudo encontrar la ingreso");
		}
	}

}
