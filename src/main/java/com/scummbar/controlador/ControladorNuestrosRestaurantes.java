package com.scummbar.controlador;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.scummbar.modelo.entities.Restaurante;
import com.scummbar.modelo.service.*;

@Controller
public class ControladorNuestrosRestaurantes {
	@Autowired
	NegocioRestauranteService negocioRestaurante;
	
	@RequestMapping(value = "/restaurantes", method = RequestMethod.GET)
	public ModelAndView restaurantes() {
		ModelAndView model = new ModelAndView("restaurantes");
		Collection<Restaurante> listaRestaurantes = (Collection<Restaurante>) negocioRestaurante.getRestaurantes();
		//Collection<Restaurante> listaRestaurantes = NegocioRestaurante.getRestaurantes();
		model.addObject("listaRestaurantes", listaRestaurantes);
		return model;
	}
}