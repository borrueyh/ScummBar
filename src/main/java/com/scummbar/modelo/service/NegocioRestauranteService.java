package com.scummbar.modelo.service;

import java.util.Date;
import java.util.List;

import java.util.Map;

import com.scummbar.modelo.entities.Reserva;
import com.scummbar.modelo.entities.Restaurante;
import com.scummbar.modelo.entities.Turno;

public interface NegocioRestauranteService {

	//Map, en teoría ¿incorrecto? Se prueba con List a ver si funciona
	//public Map<Long, Restaurante> getRestaurantes();
	
	public Turno getTurno(Long id);
	//List en vez de Map
	public List<Restaurante> getRestaurantes();
	public void addRestaurante(Restaurante restaurante);
	public List<Turno> getTurnos();
	public boolean reservar(Restaurante restaurante, Reserva reserva);
	public boolean cancelarReserva(String localizador, Date dia, Turno turno);
}