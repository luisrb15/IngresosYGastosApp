package com.ingresosygastos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ingresosygastos.entities.Abuela;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.repositories.AbuelaRepository;


@Service
public class AbuelaService implements UserDetailsService{

	@Autowired
	private AbuelaRepository abuelaRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Abuela guardar(Integer documento, String nombre, String apellido, String apodo, String email, Integer precio)
			throws WebException {

		validar(documento, nombre, apellido, apodo, email, precio);

		Abuela abuela = new Abuela();

		abuela.setDocumento(documento);
		abuela.setNombre(nombre);
		abuela.setApellido(apellido);
		abuela.setApodo(apodo);
		abuela.setEmail(email);
		abuela.setPrecio(precio);
		abuela.setCreado(new Date());

		abuelaRepository.save(abuela);

		return abuela;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Abuela modificar(Long id, Integer documento, String nombre, String apellido, String apodo, String email,
			Integer precio) throws WebException {

		validar(documento, nombre, apellido, apodo, email, precio);

		Abuela abuela = findById(id);

		abuela.setDocumento(documento);
		abuela.setNombre(nombre);
		abuela.setApellido(apellido);
		abuela.setApodo(apodo);
		abuela.setEmail(email);
		abuela.setPrecio(precio);
		abuela.setEditado(new Date());

		return abuela;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Abuela eliminar(Long id) throws WebException {
		Abuela abuela = findById(id);
		abuela.setActivo(false);
		return abuela;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Abuela alta (Long id) throws WebException {
		Abuela abuela = findById(id);
		abuela.setActivo(true);
		return abuela;
	}
	
	
	@Transactional(readOnly = true)
	public List<Abuela> listarActivos() throws WebException {
		try {
		return abuelaRepository.buscarActivos();
		} catch (Exception e) {
			throw new WebException("No se pudo listar las abuelas.");
		}
	}

	@Transactional(readOnly = true)
	public List<Abuela> listarTodos () throws WebException {
		try {
			return abuelaRepository.findAll();
		} catch (Exception e) {
			throw new WebException("No se pudo listar las abuelas.");
		}
	}

	public void validar(Integer documento, String nombre, String apellido, String apodo, String email, Integer precio)
			throws WebException {

		if (documento == null || documento.equals(0)) {
			throw new WebException("Debe tener un nombre valido");
		}
		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new WebException("Debe tener un nombre valido");
		}
		if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
			throw new WebException("Debe tener un nombre valido");
		}
		if (apodo == null || apodo.isEmpty() || apodo.contains("  ")) {
			throw new WebException("Debe tener un nombre valido");
		}
		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new WebException("Debe tener un nombre valido");
		}
		if (precio == null || precio.equals(0)) {
			throw new WebException("Debe tener un nombre valido");
		}

	}

	public Abuela findById(Long id) throws WebException {
		Optional<Abuela> respuesta = abuelaRepository.findById(id);
		if (respuesta.isPresent()) {
			Abuela abuela = respuesta.get();
			return abuela;
		} else {
			throw new WebException("No se pudo encontrar la abuela");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
