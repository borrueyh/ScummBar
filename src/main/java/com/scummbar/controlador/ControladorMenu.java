package com.scummbar.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scummbar.modelo.entities.*;
import com.scummbar.modelo.service.NegocioRestauranteServiceImpl;

@Controller
public class ControladorMenu {
	@RequestMapping(value = "/menu")
	public String menu(Model model) {
		model.addAttribute("homeUrl","reservas");
		model.addAttribute("bookingUrl","reservar");

		return "menu";
	}

}