package com.scummbar.modelo.dao;

import java.util.Date;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.transaction.Transaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scummbar.modelo.entities.*;

@Repository
public class NegocioRestauranteDaoImpl implements NegocioRestauranteDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Mesa mesa;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}


	public void addRestaurante(Restaurante restaurante) {
		getCurrentSession().save(restaurante);
	}


	public Restaurante getRestaurante(long id) {
		Restaurante restaurante = (Restaurante) getCurrentSession().get(Restaurante.class, id);
		return restaurante;
	}


	public void updateRestaurante(Restaurante restaurante) {
		Restaurante restauranteUpd = getRestaurante(restaurante.getId());
		restauranteUpd.setNombre(restaurante.getNombre());
		restauranteUpd.setDireccion(restauranteUpd.getDireccion());
		restauranteUpd.setDescripcion(restauranteUpd.getDescripcion());
		getCurrentSession().update(restauranteUpd);
	}


	public void addReserva(Reserva reserva) {
		//if (mesa.getCapacidad()>=reserva.getPersonas()) {
			getCurrentSession().save(reserva);
//		} else {
//			System.out.println("Reserva no disponible. ");
//		}
	}

	public Reserva getReserva(String localizador, Date dia, Turno turno) {
		Reserva reserva = getReservaByLocalizador(localizador, dia, turno);
		return reserva;
	}

	public void cancelarReserva(String localizador, Date dia, Turno turno) {
		try {
			Reserva reserva = getReserva(localizador, dia, turno);
			getCurrentSession().delete(reserva);
		} catch (IllegalArgumentException e) {
			System.out.println("No existe la reserva indicada. Error: " + e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Restaurante> getRestaurantes() {
		return getCurrentSession().createQuery("from Restaurante").list();
	}

	@SuppressWarnings("unchecked")
	public Reserva getReservaByLocalizador(String localizador, Date dia, Turno turno) {
		Query query = getCurrentSession().createQuery(
				"select r from Reserva r where r.localizador = :localizador AND r.dia = :dia AND turno_id = :turno");
		query.setString("localizador", localizador);
		query.setDate("dia", dia);
		query.setLong("turno", turno.getId());
		try {
			return (Reserva) query.list().iterator().next();
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No existe la reserva indicada. Error: " + e);
			Reserva reserva = null;
			return reserva;
		}

		// return (Reserva) getCurrentSession().createQuery("select r from Reserva r
		// where r.localizador=" + localizador)
		// .list().iterator().next();
	}
	
	//Prueba. Obtener una mesa por su capacidad.
	@SuppressWarnings("unchecked")
	public Mesa getMesaByCapacidad(Integer capacidad) {
		Query query = getCurrentSession().createQuery(
				"select m from Mesa m where m.capacidad = :capacidad");
		query.setInteger("capacidad", capacidad);
		try {
			return (Mesa) query.list().iterator().next();
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No existe ninguna mesa con esa capacidad. Error: " + e);
			Mesa mesa = null;
			return mesa;
		}
	}
	
	//Prueba. Actualizar estado mesa.
	@SuppressWarnings("unchecked")
	public Mesa updateDisponibilidadMesa(Integer capacidad, Boolean disponibilidad) {
		Query query = getCurrentSession().createQuery(
				"update Mesa m set m.disponibilidad=:disponibilidad where m.capacidad = :capacidad");
		query.setInteger("capacidad", capacidad);
		query.setBoolean("disponibilidad", disponibilidad);
		try {
			return (Mesa) query.list().iterator().next();
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No existe ninguna mesa con esa capacidad. Error: " + e);
			Mesa mesa = null;
			return mesa;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Turno> getTurnos() {
		return getCurrentSession().createQuery("from Turno").list();
	}

	public Turno getTurno(Long id) {
		Turno turno = (Turno) getCurrentSession().get(Turno.class, id);
		return turno;
	}	
	
	
}