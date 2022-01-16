package com.ingresosygastos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ingresosygastos.entities.Abuela;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.services.AbuelaService;

@Controller
@RequestMapping("/abuela")
public class AbuelaController {

	@Autowired
	private AbuelaService abuelaService;

	@GetMapping("/lista")
	public String lista(ModelMap modelo) throws WebException {

		List<Abuela> todos = abuelaService.listarTodos();

		modelo.addAttribute("abuelas", todos);

		return "list-abuela";
	}

	@GetMapping("/registrar")
	public String formulario(ModelMap modelo) throws WebException {
//		List<Abuela> abuelas = abuelaService.listarActivos();
		modelo.put("abuelas", abuelaService.listarActivos());
		return "form-abuela";
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, @RequestParam Integer documento, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam String apodo, @RequestParam String email,
			@RequestParam Integer precio) {

		try {
			abuelaService.guardar(documento, nombre, apellido, apodo, email, precio);
			modelo.put("exito", "Registro exitoso");
			return "form-abuela";
		} catch (Exception e) {
			modelo.put("error", "Falto algun dato");
			return "form-abuela";
		}
	}
	
	@GetMapping("/modificar/{id}")
	public String formularioModificar(ModelMap modelo) throws WebException {
//		List<Abuela> abuelas = abuelaService.listarActivos();
		modelo.put("abuelas", abuelaService.listarActivos());
		return "form-abuela";
	}

	@PostMapping("/modificar/{id}")
	public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam Integer documento, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam String apodo, @RequestParam String email,
			@RequestParam Integer precio) {

		try {
			abuelaService.modificar(Long.valueOf(id), documento, nombre, apellido, apodo, email, precio);
			modelo.put("exito", "Registro exitoso");
			return "form-abuela";
		} catch (Exception e) {
			modelo.put("error", "Falto algun dato");
			return "form-abuela";
		}
	}
	
	@GetMapping("/eliminar/{id}")
	public String baja(ModelMap modelo, @PathVariable String id) {

		try {
			abuelaService.eliminar(Long.valueOf(id));
			return "redirect:/abuela/lista";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@GetMapping("/alta/{id}")
	public String alta(ModelMap modelo, @PathVariable String id) {

		try {
			abuelaService.alta((Long.valueOf(id)));
			return "redirect:/abuela/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

}
