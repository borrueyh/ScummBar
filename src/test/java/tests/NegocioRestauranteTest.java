package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.scummbar.modelo.entities.Mesa;
import com.scummbar.modelo.entities.Reserva;
import com.scummbar.modelo.entities.Restaurante;
import com.scummbar.modelo.entities.Turno;
import com.scummbar.modelo.service.NegocioRestauranteService;
import com.scummbar.modelo.dao.NegocioRestauranteDao;
import com.scummbar.modelo.entities.*;

public class NegocioRestauranteTest {

	// Según el pdf el dao no se usa
	 @Mock
	 NegocioRestauranteDao negocioDAO;

	@Mock
	NegocioRestauranteService negocioService;
	@Mock
	Restaurante restaurante;
	@Mock
	Mesa mesa1;
	@Mock
	Mesa mesa2;
	@Mock
	Turno turno1;
	@Mock
	Turno turno2;
	@Mock
	Turno turno3;
	@Mock
	Reserva reserva;
	@Mock
	Reserva reserva2;
	@Mock
	Reserva reserva3;

	// SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	@Before
	public void initialize() {
		// Se inicializan service,dao,... usando mockito.
		negocioService = mock(NegocioRestauranteService.class);
		negocioDAO = mock(NegocioRestauranteDao.class);
		restaurante = mock(Restaurante.class);

		Long id=1L;
		sdf = new SimpleDateFormat("ddMMyyyy");
		mesa1 = mock(Mesa.class);
		mesa1.setCapacidad(6);
		mesa2 = mock(Mesa.class);
		mesa2.setCapacidad(2);
		restaurante.getMesas().add(mesa1);
		restaurante.getMesas().add(mesa2);
		restaurante.setDescripcion("asdasd");
		restaurante.setDireccion("calle falsa");
		restaurante.setId(id);
		restaurante.setNombre("Ristorante jajja");
		negocioService.addRestaurante(restaurante);

	}

	// Debe retornar lista de restaurantes.
	// La disponibilidad de los restaurantes es correcta.
	@Test
	public void comprobarDisponibilidad() throws ParseException {
		// Fecha para realizar la reserva
		Date date = sdf.parse("13072018");

		// Creación del nuevo turno
		Long idTurno1 = 654567L;
		String descripcionTurno1 = "descripcion del turno";
		turno1 = mock(Turno.class);
		turno1.setId(idTurno1);
		turno1.setDescripcion(descripcionTurno1);

		// Se realiza la reserva
		reserva = mock(Reserva.class);
		reserva.setDia(date);
		reserva.setTurno(turno1);
		reserva.setPersonas(4);
		reserva.setLocalizador("004K");
		
		when(negocioDAO.getReservaLibre(reserva.getDia(), reserva.getTurno(), reserva.getPersonas(),restaurante.getId())).thenReturn(true);
		Assert.assertTrue(negocioService.reservar(restaurante, reserva));
		Assert.assertSame(6, restaurante.getPlazasReservadas(date, turno1));
		//verify(negocioDAO).
	}

	// Completa la lógica de negocio correspondiente a realizar una reserva
	// Muestra un error si alguno de los parámetros no es correcto
	@Test
	public void reservar() throws ParseException {
		Date fecha1 = sdf.parse("01052018");
		Date fecha2 = sdf.parse("07012018");
		Date fecha3 = sdf.parse("08032018");

		// Creación nuevo turno
		Long idTurno1 = 654523L;
		String descripcionTurno1 = "descripcion del turno";
		turno1 = mock(Turno.class);
		turno1.setId(idTurno1);
		turno1.setDescripcion(descripcionTurno1);

		// Creación de otro turno nuevo
		Long idTurno2 = 143523L;
		String descripcionTurno2 = "descripcion del turno 2";
		turno2 = mock(Turno.class);
		turno2.setId(idTurno2);
		turno2.setDescripcion(descripcionTurno2);

		// Creación de otro turno nuevo
		Long idTurno3 = 185923L;
		String descripcionTurno3 = "descripcion del turno 3";
		turno3 = mock(Turno.class);
		turno3.setId(idTurno3);
		turno3.setDescripcion(descripcionTurno3);

		// Se realiza la primera reserva
		reserva = mock(Reserva.class);
		reserva.setDia(fecha1);
		reserva.setTurno(turno1);
		reserva.setPersonas(4);
		reserva.setLocalizador("001Q");
		Assert.assertTrue(negocioService.reservar(restaurante, reserva));

		// Se realiza una segunda reserva
		reserva2 = mock(Reserva.class);
		reserva2.setDia(fecha2);
		reserva2.setTurno(turno2);
		reserva2.setPersonas(2);
		reserva2.setLocalizador("002Q");
		Assert.assertTrue(negocioService.reservar(restaurante, reserva2));

		// Se realiza una tercera reserva
		reserva3 = mock(Reserva.class);
		reserva3.setDia(fecha3);
		reserva3.setTurno(turno3);
		reserva3.setPersonas(6);
		reserva3.setLocalizador("003Q");
		Assert.assertFalse(negocioService.reservar(restaurante, reserva3));

	}

	// Completa la lógica de negocio correspondiente a cancelar una reserva.
	// Muestra un error si el identificador de reserva no existe o no es válido.
	@Test
	public void cancelarReserva() throws ParseException {
		Date fecha = sdf.parse("01052018");

		String localizador = "005Q";

		// Creación nuevo turno
		Long idTurno = 654523L;
		String descripcionTurno = "descripcion del turno";
		turno1 = mock(Turno.class);
		turno1.setId(idTurno);
		turno1.setDescripcion(descripcionTurno);

		// Se realiza la reserva
		reserva = mock(Reserva.class);
		reserva.setDia(fecha);
		reserva.setTurno(turno1);
		reserva.setPersonas(4);
		reserva.setLocalizador("localizador");

		Assert.assertTrue(negocioService.reservar(restaurante, reserva));
		Assert.assertTrue(negocioService.cancelarReserva(localizador, fecha, turno1));
		Assert.assertFalse(negocioService.cancelarReserva(localizador, fecha, turno1));

	}

}