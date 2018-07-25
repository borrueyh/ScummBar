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
		getCurrentSession().save(reserva);
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
	}

	// COPIA DE SEGURIDAD. Funciona bien, falla en la disponibilidad de las mesas.
	// Comprueba si una reserva existe o no. Si no existe, devuelve true para dar
	// luz verde a realizar la reserva.
	// @SuppressWarnings("unchecked")
	// public Boolean getReservaLibre(Date dia, Turno turno, Integer capacidad, Long
	// idrestaurante) {
	// boolean disponibilidad;
	// Query query = getCurrentSession().createQuery(
	// "select r from Reserva r where r.dia = :dia and r.turno=:turno and
	// r.mesa.capacidad >= :capacidad and r.restaurante.id=:idrestaurante");
	// query.setDate("dia", dia);
	// query.setLong("turno", turno.getId());
	// query.setInteger("capacidad", capacidad);
	// query.setLong("idrestaurante", idrestaurante);
	// try {
	// query.list().iterator().next();
	// return disponibilidad = false;
	// } catch (NoSuchElementException | NullPointerException e) {
	// System.out.println("Reserva libre. " + e);
	// return disponibilidad = true;
	// }
	// }

	// MIRAR NOT IN!!!!!!!!!!!!!!!!!
	// Funciona bien, falla en la disponibilidad de las mesas.
	// Comprueba si una reserva existe o no. Si no existe, devuelve true para dar
	// luz verde a realizar la reserva.
	@SuppressWarnings("unchecked")
	public Boolean getReservaLibre(Date dia, Turno turno, Integer capacidad, Long idrestaurante) {
		boolean disponibilidad;
		Query query = getCurrentSession().createQuery(
				"select r from Reserva r where r.dia = :dia and r.mesa.capacidad >= :capacidad and r.restaurante.id=:idrestaurante not in (select re from Reserva re where re.mesa.capacidad >= :capacidad)");
		//select r from Reserva r where r.dia = :dia r.mesa.capacidad >= :capacidad and r.restaurante.id=:idrestaurante not in (select re from Reserva re where re.mesa.capacidad >= :capacidad)");
		query.setDate("dia", dia);
		query.setLong("turno", turno.getId());
		query.setInteger("capacidad", capacidad);
		query.setLong("idrestaurante", idrestaurante);
		try {
			query.list().iterator().next();
			return disponibilidad = true;
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("Reserva libre. " + e);
			return disponibilidad = false;
		}
	}

	//PRUEBA. Devuelve una mesa libree. Asigna una mesa libre a una reserva (dentro del if del Service)
	@SuppressWarnings("unchecked")
	public Mesa getMesaLibrePrueba(Date dia, Turno turno, Integer capacidad, Long idrestaurante) {
		Query query = getCurrentSession().createQuery(
				"select z from Reserva z where z.dia = :dia z.mesa.capacidad >= :capacidad and z.restaurante.id=:idrestaurante not in (select re from Reserva re where re.mesa.capacidad >= :capacidad)");
		query.setDate("dia", dia);
		query.setLong("turno", turno.getId());
		query.setInteger("capacidad", capacidad);
		query.setLong("idrestaurante", idrestaurante);
		try {
			return (Mesa) query.list().iterator().next();
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No existe ninguna mesa con esa capacidad. Error: " + e);
			Mesa mesa = null;
			return mesa;
		}
	}

	// CONTINUARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
	// PRUEBA
	// Aquesta consulta tindria que anarr dins d'un NOT IN de la consulta de
	// getReservaLibre
	@SuppressWarnings("unchecked")
	public Mesa getMesaPosible(Integer capacidad) {
		Query query = getCurrentSession().createQuery("select r from Reserva r where r.mesa.capacidad >= r.personas");
		query.setInteger("capacidad", capacidad);

		// Obtener ocupadas
		List<Mesa> ocupadas = null;
		ocupadas.addAll(query.list());

		// Obtener disponibles, eliminando las que están ocupadas
		List<Mesa> disponibles = null;
		disponibles.addAll(query.list());
		disponibles.remove(ocupadas);

		try {
			return (Mesa) query.list().iterator().next();
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No existe ninguna mesa con esa capacidad. Error: " + e);
			return null;
		}
	}

	// PRUEBA. Obtiene el número máximo de personas que puede contener una mesa de
	// entre todas las existentes.
	@SuppressWarnings("unchecked")
	public Integer getMaxCapacidadMesas() {
		Query query = getCurrentSession().createQuery("select max(r.mesa.capacidad) from Reserva r");
		try {
			return (Integer) query.list().iterator().next();
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No existe ninguna mesa con esa capacidad. Error: " + e);
			return null;
		}
	}

	// Obtener una mesa por su capacidad.
	@SuppressWarnings("unchecked")
	public Mesa getMesaDisponible(Date dia, Turno turno, Integer capacidad, Long idrestaurante) {
		try {
			Query query = null;
			for (int i = 1; i <= getMaxCapacidadMesas(); i++) {
				query = getCurrentSession().createQuery(
						"select r from Reserva r where r.dia = :dia and r.turno=:turno and r.mesa.capacidad >= :capacidad and r.restaurante.id=:idrestaurante");
				query.setDate("dia", dia);
				query.setLong("turno", turno.getId());
				query.setInteger("capacidad", capacidad);
				query.setLong("idrestaurante", idrestaurante);

				query.list().iterator().remove();
				capacidad++;
			}
			return (Mesa) query.list().iterator().next();
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No existe ninguna mesa con esa capacidad. Error: " + e);
			Mesa mesa = null;
			return mesa;
		}
	}

	// COPIA SEGURIDADDDDD DEL DE ARRIBA!!!!
	// Obtener una mesa por su capacidad.
	// @SuppressWarnings("unchecked")
	// public Mesa getMesaDisponible(Date dia, Turno turno, Integer capacidad, Long
	// idrestaurante) {
	// Query query = getCurrentSession().createQuery(
	// "select r from Reserva r where r.dia = :dia and r.turno=:turno and
	// r.mesa.capacidad >= :capacidad and r.restaurante.id=:idrestaurante");
	// query.setDate("dia", dia);
	// query.setLong("turno", turno.getId());
	// query.setInteger("capacidad", capacidad);
	// query.setLong("idrestaurante", idrestaurante);
	// try {
	// return (Mesa) query.list().iterator().next();
	// } catch (NoSuchElementException | NullPointerException e) {
	// System.out.println("No existe ninguna mesa con esa capacidad. Error: " + e);
	// Mesa mesa = null;
	// return mesa;
	// }
	// }

	// Obtener una mesa por su capacidad
	@SuppressWarnings("unchecked")
	public Mesa getMesaByCapacidad(Integer capacidad) {
		Query query = getCurrentSession().createQuery("select m from Mesa m where m.capacidad = :capacidad");
		query.setInteger("capacidad", capacidad);
		try {
			return (Mesa) query.list().iterator().next();
		} catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No existe ninguna mesa con esa capacidad. Error: " + e);
			Mesa mesa = null;
			return mesa;
		}
	}

	// En principi es pot eliminar
	// Devuelve true si encuentra una mesa con el id pasado por parámetro
	// @SuppressWarnings("unchecked")
	// public boolean getMesaDisponibilidad(Long id) {
	// boolean disponibilidad;
	//
	// Query query = getCurrentSession().createQuery("select m from Mesa m where
	// m.id = :id");
	// query.setLong("id", id);
	// query.list().iterator().next();
	// // Si lo encuentra devuelve false, si no encuentra ninguna mesa con esa id
	// // devuelve true.
	// try {
	// return disponibilidad = false;
	// } catch (NoSuchElementException | NullPointerException e) {
	// System.out.println("Mesa disponible!: " + e);
	// return disponibilidad = true;
	// }
	// }

	@SuppressWarnings("unchecked")
	public List<Turno> getTurnos() {
		return getCurrentSession().createQuery("from Turno").list();
	}

	public Turno getTurno(Long id) {
		Turno turno = (Turno) getCurrentSession().get(Turno.class, id);
		return turno;
	}

}