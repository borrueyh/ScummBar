package com.scummbar.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.scummbar.modelo.dao.NegocioRestauranteDao;
import com.scummbar.modelo.dto.CancelarDto;
import com.scummbar.modelo.entities.Reserva;
import com.scummbar.modelo.entities.Restaurante;
import com.scummbar.modelo.entities.Turno;
import com.scummbar.modelo.service.*;

@Controller
public class ControladorCancelaciones {

	@Autowired
	NegocioRestauranteService service;

	@RequestMapping(value = "/cancelar", method = RequestMethod.GET)
	public ModelAndView verFormulario() {
		ModelAndView model = new ModelAndView("cancelar");
		CancelarDto dto = new CancelarDto();
		dto.setRestaurantes(service.getRestaurantes());
		dto.setTurnos(service.getTurnos());
		model.addObject("command", dto);
		return model;
	}

	@RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public ModelAndView submitFormulario(CancelarDto dto) {
		ModelAndView model = new ModelAndView("cancelado");
		Reserva reserva = new Reserva();
		reserva.setDia(dto.getDia());
		// Se substituye valueof por: buscar turno en base a su ID)
		reserva.setTurno(service.getTurno(dto.getTurnoId()));

		reserva.setLocalizador(dto.getLocalizador());
		
		boolean cancelarReserva = service.cancelarReserva(reserva.getLocalizador(), reserva.getDia(), reserva.getTurno());
		model.addObject("cancelado",cancelarReserva);
		// Versión correcta según el pdf (el pdf está mal)
		// model.addObject("cancelado", service.cancelarReserva(restaurante, reserva));
		return model;
	}
}