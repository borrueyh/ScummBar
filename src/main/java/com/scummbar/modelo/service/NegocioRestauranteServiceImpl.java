package com.scummbar.modelo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scummbar.modelo.dao.NegocioRestauranteDao;
import com.scummbar.modelo.entities.Mesa;
import com.scummbar.modelo.entities.Reserva;
import com.scummbar.modelo.entities.Restaurante;
import com.scummbar.modelo.entities.Turno;

@Service
@Transactional
public class NegocioRestauranteServiceImpl implements NegocioRestauranteService {

	@Autowired
	NegocioRestauranteDao negociodao;

	private Mesa mesa;

	// Devuelve el Turno coincidente con la id pasada por parámetro
	public Turno getTurno(Long id) {
		return negociodao.getTurno(id);
	}

	// Prueba con list en vez de map
	public List<Restaurante> getRestaurantes() {
		return negociodao.getRestaurantes();
	}

	public void addRestaurante(Restaurante restaurante) {
		negociodao.addRestaurante(restaurante);
	}

	public List<Turno> getTurnos() {
		return negociodao.getTurnos();
	}

	// Método para añadir una nueva reserva. Devuelve true si se añade una reserva,
	// en caso contrario false.
	public boolean reservar(Restaurante restaurante, Reserva reserva) {
		boolean resultado;

		// Creación del localizador de la nueva reserva. Se crea a partir de la fecha
		// actual.
		String localizador = Long.toString((new Date()).getTime());

		try {
			// Si la reserva no es null y la reserva está libre...
			if (reserva != null & negociodao.getReservaLibre(reserva.getDia(), reserva.getTurno(),
					reserva.getPersonas(), restaurante.getId()) == true) {
				reserva.setLocalizador(localizador);
				// Asigna el id de la mesa disponible a la reserva.
				// reserva.setMesa(negociodao.getMesaByCapacidad(reserva.getPersonas()));

				// PRUEBA. Substituye la linea de arriba (getMesaByCapacidad) que funciona bien
				// pero no comprueba la disponibilidad correctamente.
				reserva.setMesa(negociodao.getMesaLibrePrueba(reserva.getDia(), reserva.getTurno(),
						reserva.getPersonas(), restaurante.getId()));

				// Se asigna el restaurante a la reserva.
				reserva.setRestaurante(restaurante);
				negociodao.addReserva(reserva);
				resultado = true;

			} else {
				resultado = false;
			}

		} catch (NullPointerException e) {
			System.out.println("Valor vacío en restaurante o reserva: " + e);
			resultado = false;
		}
		return resultado;
	}

	// Método para cancelar una reserva.
	public boolean cancelarReserva(String localizador, Date dia, Turno turno) {
		boolean resultado;

		try {
			Reserva reserva = negociodao.getReserva(localizador, dia, turno);
			// Si localizador no está vacío ni los valores de reserva son nulos (existe el
			// día indicado,...)
			// se cancela la reserva y resultado pasa a ser true, para que el EL de
			// cancelado.jsp muestre
			// el mensaje adecuado del fichero messages_es_properties. Además la mesa pasa a
			// estar libre
			if (localizador != null & localizador != "" & !reserva.equals(null)) {
				negociodao.cancelarReserva(localizador, dia, turno);
				resultado = true;
			} else {
				// Resultado pasa a ser false, para que se muestre por pantalla el mensaje
				// localizador no encontrado.
				resultado = false;
			}
		} catch (IllegalArgumentException e) {
			System.out.println("La reserva indicada no existe: " + e);
			resultado = false;
		} catch (NullPointerException e) {
			System.out.println("Localizador vacío: " + e);
			resultado = false;
		}
		return resultado;
	}

}