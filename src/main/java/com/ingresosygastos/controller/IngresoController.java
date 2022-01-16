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
import com.ingresosygastos.entities.Ingreso;
import com.ingresosygastos.errores.WebException;
import com.ingresosygastos.services.AbuelaService;
import com.ingresosygastos.services.IngresoService;
import com.ingresosygastos.services.NotificacionService;

@Controller
@RequestMapping("/ingreso")
public class IngresoController {

	@Autowired
	private IngresoService ingresoService;

	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private AbuelaService abuelaService;

	@GetMapping("/lista")
	public String lista(ModelMap modelo) throws WebException {

		List<Ingreso> todos = ingresoService.listarTodos();

		modelo.addAttribute("ingresos", todos);

		return "list-ingreso";
	}

	@GetMapping("/cobro")
	public String formulario(ModelMap modelo) throws WebException {
//		List<Abuela> abuelas = abuelaService.listarActivos();
		modelo.put("abuelas", abuelaService.listarActivos());
		return "form-ingreso";
	}

	@PostMapping("/cobro")
	public String registrar(ModelMap modelo, @RequestParam Integer montoCobrado, @RequestParam Abuela abuela) {

		try {
			enviarMail(ingresoService.guardar(montoCobrado, abuela).getId());
			modelo.put("exito", "Registro exitoso");
			return "form-ingreso";
		} catch (Exception e) {
			modelo.put("error", "Falto algun dato");
			return "form-ingreso";
		}
	}

	@GetMapping("/eliminar/{id}")
	public String baja(ModelMap modelo, @PathVariable String id) {

		try {
			ingresoService.eliminar(id);
			return "redirect:/ingreso/lista";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@GetMapping("/alta/{id}")
	public String alta(ModelMap modelo, @PathVariable String id) {

		try {
			ingresoService.alta(id);
			return "redirect:/ingreso/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@PostMapping("/mailsender/{id}")
	public void enviarMail(@PathVariable String id) throws WebException {

		try {
			notificacionService.sendSimpleEmail(id);
		} catch (Exception e) {
			throw new WebException("Error enviando mail");
		}
	}

}
