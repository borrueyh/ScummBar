package com.scummbar.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.scummbar.modelo.dto.ReservarDto;
import com.scummbar.modelo.entities.*;
import com.scummbar.modelo.service.*;

@Controller
public class ControladorReservas {
	@Autowired
	NegocioRestauranteService negocioRestaurante;

	@RequestMapping(value = "/reservar", method = RequestMethod.GET)
	public ModelAndView verFormulario() {
		ModelAndView model = new ModelAndView("reservar");
		ReservarDto dto = new ReservarDto();
		dto.setRestaurantes(negocioRestaurante.getRestaurantes());
		dto.setTurnos(negocioRestaurante.getTurnos());
		model.addObject("command", dto);
		return model;
	}
	
	@RequestMapping(value = "/reservar", method = RequestMethod.POST)
	public ModelAndView submitFormulario(ReservarDto dto) {
		ModelAndView model = new ModelAndView("reservado");
		Reserva reserva = new Reserva();
		reserva.setDia(dto.getDia());
		reserva.setPersonas(dto.getPersonas());
		//Se substituye valueof por: buscar turno en base a su ID)
		reserva.setTurno(negocioRestaurante.getTurno(dto.getTurnoId()));
		Restaurante restaurante = new Restaurante();
		restaurante.setId(dto.getRestauranteId());
		model.addObject("localizador", negocioRestaurante.reservar(restaurante, reserva));
		return model;
	}
}