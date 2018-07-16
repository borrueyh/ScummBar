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

	// Devuelve el Turno coincidente con la id pasada por par�metro
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

	// public Mesa getMesa(Long id) {
	// return negociodao.getMesa(id);
	// }

	// M�todo para a�adir una nueva reserva. Devuelve true si se a�ade una reserva,
	// en caso contrario false.
	public boolean reservar(Restaurante restaurante, Reserva reserva) {
		boolean resultado;
		boolean disponibilidad = true;

		// Creaci�n del localizador de la nueva reserva. Se crea a partir de la fecha
		// actual.
		String localizador = Long.toString((new Date()).getTime());

		try {
			// Pruebas
			// Si reserva no es nulo y existe una mesa con esa capacidad...
			if (reserva != null & negociodao.getMesaByCapacidad(reserva.getPersonas()) != null) {
				disponibilidad = false;
				negociodao.updateDisponibilidadMesa(reserva.getPersonas(), disponibilidad);
				reserva.setLocalizador(localizador);
				negociodao.addReserva(reserva);
				resultado = true;

			} else {
				resultado = false;
			}

		} catch (NullPointerException e) {
			System.out.println("Valor vac�o en restaurante o reserva: " + e);
			resultado = false;
		}
		return resultado;
	}

	// M�todo para cancelar una reserva.
	public boolean cancelarReserva(String localizador, Date dia, Turno turno) {
		boolean resultado;
		boolean disponibilidad=true;

		try {
			Reserva reserva = negociodao.getReserva(localizador, dia, turno);
			// Si localizador no est� vac�o ni los valores de reserva son nulos (existe el
			// d�a indicado,...)
			// se cancela la reserva y resultado pasa a ser true, para que el EL de
			// cancelado.jsp muestre
			// el mensaje adecuado del fichero messages_es_properties. Adem�s la mesa pasa a estar libre
			if (localizador != null & localizador != "" & !reserva.equals(null)) {
				disponibilidad = true;
				negociodao.updateDisponibilidadMesa(reserva.getPersonas(), disponibilidad);
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
			System.out.println("Localizador vac�o: " + e);
			resultado = false;
		}
		return resultado;
	}
}