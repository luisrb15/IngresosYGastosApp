package com.ingresosygastos.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ingresosygastos.entities.Abuela;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.services.AbuelaService;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private AbuelaService abuelaService;
	
	@GetMapping("/")
	public String index(ModelMap modelo) throws WebException{
		
		List<Abuela> abuelasActivos = abuelaService.listarActivos();
		
		modelo.addAttribute("abuelas", abuelasActivos);
	
		return "index";
	}
	@GetMapping("/login")
	public String login(HttpSession session, Authentication usuario, ModelMap modelo, @RequestParam(required = false) String error) {
		try {
			if (usuario.getName() != null) {
				return "redirect:/";
			} else {
				
				if (error != null && !error.isEmpty()) {
					modelo.addAttribute("error", "La dirección de mail o la contraseña que ingresó son incorrectas.");
				}
				return "login";
			}
			
		} catch (Exception e) {
			if (error != null && !error.isEmpty()) {
				modelo.addAttribute("error", "La dirección de mail o la contraseÃ±a que ingresó son incorrectas.");
			}
			return "login";
		}
	}
	
	@GetMapping("/loginsuccess")
	public String loginresolver() {
				
		return "redirect:/";
	}

			
}