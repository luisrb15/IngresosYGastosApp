package com.ingresosygastos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ingresosygastos.entities.Abuela;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.services.AbuelaService;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private AbuelaService abuelaService;
	
	@RequestMapping
	public String generador(ModelMap model) throws WebException {
		
		List<Abuela> abuelasActivos = abuelaService.listarActivos();
		
		model.addAttribute("abuelas", abuelasActivos);
		
		return "mail-template";

	}

}