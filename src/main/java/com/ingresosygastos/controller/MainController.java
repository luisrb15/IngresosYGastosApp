package com.ingresosygastos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ingresosygastos.entities.Abuela;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.services.AbuelaService;
import com.ingresosygastos.services.UsuarioService;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AbuelaService abuelaService;
	
	@GetMapping("/")
	public String index(ModelMap modelo) throws WebException{
		
		List<Abuela> abuelasActivos = abuelaService.listarActivos();
		
		modelo.addAttribute("abuelas", abuelasActivos);
	
		return "index";
	}

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente.");
        }
        return "login.html";
    }

    @GetMapping("/registro")
    public String registro() {
    	return "registro.html";
    }

    @PostMapping("/registrar")
    public String registrar(
    		ModelMap modelo, 
    		@RequestParam String nombre, 
    		@RequestParam String apellido, 
    		@RequestParam String email, 
    		@RequestParam String clave) {

        try {
            usuarioService.guardar(nombre, apellido, email, clave);
        } catch (WebException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("clave", clave);
            return "registro.html";
        }
        modelo.put("titulo", "Bienvenido a Flor del Bambu");
        modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria. Por favor contactarse con el administrador.");
        return "exito.html";
    }
}