package com.ingresosygastos.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ingresosygastos.entities.Usuario;
import com.ingresosygastos.enums.Rol;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Usuario guardar(String nombre, String apellido, String email, String clave) throws WebException {

		validar(nombre, apellido, email, clave);
		Usuario usuario = new Usuario();

		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setEmail(email);
		usuario.setClave(new BCryptPasswordEncoder().encode(clave));
		usuario.setRol(Rol.USUARIO);
		usuario.setCreado(new Date());

		return usuarioRepository.save(usuario);
	}

    @Transactional
    public void modificar(String id, String nombre, String apellido, String email, String clave) throws WebException {
        
        validar(nombre, apellido, email, clave);

        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setApellido(apellido);
            usuario.setNombre(nombre);
            usuario.setEmail(email);;
            usuario.setEditado(new Date());
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            usuario.setClave(encriptada);

            usuarioRepository.save(usuario);
        } else {

            throw new WebException("No se encontró el usuario solicitado");
        }

    }

    @Transactional
    public void deshabilitar(String id) throws WebException {

        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            usuarioRepository.save(usuario);
        } else {

            throw new WebException("No se encontró el usuario solicitado");
        }

    }

    @Transactional
    public void habilitar(String id) throws WebException {

        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            usuarioRepository.save(usuario);
        } else {

            throw new WebException("No se encontró el usuario solicitado");
        }

    }
    
    @Transactional
    public void cambiarRol(String id) throws WebException{
    
    	Optional<Usuario> respuesta = usuarioRepository.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(Rol.USUARIO)) {
    			
    		usuario.setRol(Rol.ADMIN);
    		
    		}else if(usuario.getRol().equals(Rol.ADMIN)) {
    			usuario.setRol(Rol.USUARIO);
    		}
    	}
    }
	public void validar(String nombre, String apellido, String email, String clave, String rol) throws WebException {

		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new WebException("Debe tener un nombre valido");
		}

		if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
			throw new WebException("Debe tener un apellido valido");
		}

		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new WebException("Debe tener un email valido");
		}

		if (usuarioRepository.buscarPorEmail(email) != null) {
			throw new WebException("El Email ya esta en uso");
		}

		if (clave == null || clave.isEmpty() || clave.contains("  ") || clave.length() < 8 || clave.length() > 12) {
			throw new WebException("Debe tener una clave valida");
		}

		if (!Rol.ADMIN.toString().equals(rol) && !Rol.USUARIO.toString().equals(rol)) {
			throw new WebException("Debe tener rol valido");
		}
	}
	
	public void validar(String nombre, String apellido, String email, String clave) throws WebException {

		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new WebException("Debe tener un nombre valido");
		}

		if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
			throw new WebException("Debe tener un apellido valido");
		}

		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new WebException("Debe tener un email valido");
		}

		if (usuarioRepository.buscarPorEmail(email) != null) {
			throw new WebException("El Email ya esta en uso");
		}

		if (clave == null || clave.isEmpty() || clave.contains("  ") || clave.length() < 8 || clave.length() > 12) {
			throw new WebException("Debe tener una clave valida");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.buscarPorEmail(email);
		
		if (usuario != null) {
			List<GrantedAuthority> permissions = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
			permissions.add(p);
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", usuario);
			
			User user = new User(usuario.getEmail(), usuario.getClave(), permissions);
			return user;
		}
		return null;

	}

	@Transactional(readOnly=true)
	    public Usuario buscarPorId(String id) throws WebException {
	        Optional<Usuario> respuesta = usuarioRepository.findById(id);
	        if (respuesta.isPresent()) {
	            Usuario usuario = respuesta.get();
	            return usuario;
	        } else {
	            throw new WebException("No se encontró el usuario solicitado");
	        }
	    }
	    
	   @Transactional(readOnly=true)
	    public List<Usuario> todosLosUsuarios(){
	        return usuarioRepository.findAll();	        
	    }

}
