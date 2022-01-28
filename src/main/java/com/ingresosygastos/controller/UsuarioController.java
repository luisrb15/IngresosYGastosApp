package com.ingresosygastos.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ingresosygastos.entities.Usuario;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.services.UsuarioService;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/registro")
	public String formulario() {
		return "form-usuario";
	}

	@PostMapping("/registro")
	public String guardar(
			ModelMap modelo, 
			@RequestParam String nombre, 
			@RequestParam String apellido,
			@RequestParam String email, 
			@RequestParam String clave) {
		try {
			usuarioService.guardar(nombre, apellido, email, clave);

			modelo.put("exito", "registro exitoso");
			return "form-usuario";
		} catch (WebException e) {
			modelo.put("error", e.getMessage());
			return "form-usuario";
		}
	}

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/editar-perfil")
    public String editarPerfil(
    		HttpSession session, 
    		@RequestParam String id, 
    		ModelMap model) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/";
        }

        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            model.addAttribute("perfil", usuario);
        } catch (WebException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "perfil";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @PostMapping("/actualizar-perfil")
    public String registrar(
    		ModelMap modelo, 
    		HttpSession session, 
    		@RequestParam String id, 
    		@RequestParam String nombre, 
    		@RequestParam String apellido, 
    		@RequestParam String email, 
    		@RequestParam String clave) {

        Usuario usuario = null;
        try {

            Usuario login = (Usuario) session.getAttribute("usuariosession");
            
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }

            usuario = usuarioService.buscarPorId(id);
            usuarioService.modificar(id, nombre, apellido, email, clave);
            session.setAttribute("usuariosession", usuario);

            return "redirect:/inicio";
        } catch (WebException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("perfil", usuario);

            return "perfil.html";
        }

    }
}
