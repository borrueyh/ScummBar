package com.scummbar.modelo.dao;

import java.util.Date;
import java.util.List;

import java.util.Map;

import com.scummbar.modelo.entities.Mesa;
import com.scummbar.modelo.entities.Reserva;
import com.scummbar.modelo.entities.Restaurante;
import com.scummbar.modelo.entities.Turno;

public interface NegocioRestauranteDao {

	public Turno getTurno(Long id);
	
	void addRestaurante(Restaurante restaurante);

	Restaurante getRestaurante(long id);

	void updateRestaurante(Restaurante restaurante);

	void addReserva(Reserva reserva);

	Reserva getReserva(String localizador, Date dia, Turno turno);

	void cancelarReserva(String localizador, Date dia, Turno turno);

	// Con List en vez de Map, en el pdf está mal
	List<Restaurante> getRestaurantes();

	List<Turno> getTurnos();
	
	//PRUEBA. Devuelve una mesa según su capacidad.
	public Mesa getMesaByCapacidad(Integer capacidad);
	
	//PRUEBA. Para cambiar la disponibilidad de una mesa.
	public Mesa updateDisponibilidadMesa(Integer capacidad, Boolean disponibilidad);
}