package tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.scummbar.modelo.entities.Mesa;
import com.scummbar.modelo.entities.Reserva;
import com.scummbar.modelo.entities.Restaurante;
import com.scummbar.modelo.entities.Turno;
import com.scummbar.modelo.service.NegocioRestauranteServiceImpl;

import com.scummbar.modelo.entities.*;

public class NegocioRestauranteTest {

	private NegocioRestauranteServiceImpl negocioRestaurente;
	
	private Restaurante restaurante;
	//SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");

	@Before
	public void initialize() {
		negocioRestaurente = new NegocioRestauranteServiceImpl();
		sdf = new SimpleDateFormat("ddMMyyyy");
		restaurante = new Restaurante();
		Mesa mesa1 = new Mesa();
		mesa1.setCapacidad(6);
		Mesa mesa2 = new Mesa();
		mesa2.setCapacidad(2);
		restaurante.getMesas().add(mesa1);
		restaurante.getMesas().add(mesa2);
		negocioRestaurente.addRestaurante(restaurante);
	}

	//Debe retornar lista de restaurantes.
	//La disponibilidad de los restaurantes es correcta.
	@Test
	public void comprobarDisponibilidad() throws ParseException {
		// Fecha para realizar la reserva
		Date date = sdf.parse("13072018");

		// Creación del nuevo turno
		Long idTurno1 = 654567L;
		String descripcionTurno1 = "descripcion del turno";
		Turno turno1 = new Turno(idTurno1, descripcionTurno1);

		// Se realiza la reserva
		Reserva reserva = new Reserva();
		reserva.setDia(date);
		reserva.setTurno(turno1);
		reserva.setPersonas(4);
		reserva.setLocalizador("004K");
		Assert.assertTrue(negocioRestaurente.reservar(restaurante, reserva));
		Assert.assertSame(6, restaurante.getPlazasReservadas(date, turno1));
	}

	//Completa la lógica de negocio correspondiente a realizar una reserva
	//Muestra un error si alguno de los parámetros no es correcto
	@Test
	public void reservar() throws ParseException {
		Date fecha1 = sdf.parse("01052018");
		Date fecha2 = sdf.parse("14012018");
		Date fecha3 = sdf.parse("08032018");

		// Creación nuevo turno
		Long idTurno1 = 654523L;
		String descripcionTurno1 = "descripcion del turno";
		Turno turno1 = new Turno(idTurno1, descripcionTurno1);

		// Creación de otro turno nuevo
		Long idTurno2 = 143523L;
		String descripcionTurno2 = "descripcion del turno 2";
		Turno turno2 = new Turno(idTurno2, descripcionTurno2);

		// Creación de otro turno nuevo
		Long idTurno3 = 185923L;
		String descripcionTurno3 = "descripcion del turno 3";
		Turno turno3 = new Turno(idTurno3, descripcionTurno3);

		// Se realiza la primera
		Reserva reserva1 = new Reserva();
		reserva1.setDia(fecha1);
		reserva1.setTurno(turno1);
		reserva1.setPersonas(4);
		reserva1.setLocalizador("001Q");
		Assert.assertTrue(negocioRestaurente.reservar(restaurante, reserva1));

		// Se realiza una segunda reserva
		Reserva reserva2 = new Reserva();
		reserva2.setDia(fecha2);
		reserva2.setTurno(turno2);
		reserva2.setPersonas(2);
		reserva2.setLocalizador("002Q");
		Assert.assertTrue(negocioRestaurente.reservar(restaurante, reserva2));

		// Se realiza una tercera reserva
		Reserva reserva3 = new Reserva();
		reserva3.setDia(fecha3);
		reserva3.setTurno(turno3);
		reserva3.setPersonas(6);
		reserva3.setLocalizador("003Q");
		Assert.assertFalse(negocioRestaurente.reservar(restaurante, reserva3));

	}

	//Completa la lógica de negocio correspondiente a cancelar una reserva.
	//Muestra un error si el identificador de reserva no existe o no es válido.
	@Test
	public void cancelarReserva() throws ParseException {
		Date fecha = sdf.parse("01052018");

		String localizador = "005Q";

		// Creación nuevo turno
		Long idTurno = 654523L;
		String descripcionTurno = "descripcion del turno";
		Turno turno = new Turno(idTurno, descripcionTurno);

		// Se realiza la reserva
		Reserva reserva = new Reserva();
		reserva.setDia(fecha);
		reserva.setTurno(turno);
		reserva.setPersonas(4);
		reserva.setLocalizador("localizador");

		Assert.assertTrue(negocioRestaurente.reservar(restaurante, reserva));
		Assert.assertTrue(negocioRestaurente.cancelarReserva(localizador, fecha, turno));
		Assert.assertFalse(negocioRestaurente.cancelarReserva(localizador, fecha, turno));

	}

}