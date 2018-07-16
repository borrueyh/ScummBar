package com.scummbar.modelo.entities;

import javax.persistence.*;
import com.scummbar.modelo.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurantes")
public class Restaurante {
	
	public Restaurante() {
		super();
	}

	public Restaurante(Long id, String nombre, String direccion, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.descripcion = descripcion;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@Column(name = "direccion", nullable = false, length = 100)
	private String direccion;

	@Column(name = "descripcion", nullable = false, length = 500)
	private String descripcion;

	@OneToMany
	private List<Mesa> mesas;

	@OneToMany
	private List<Reserva> reservas;

	@Transient
	public Long getId() {
		return id;
	}
	
	@Transient
	public void setId(Long id) {
		this.id=id;
	}
	
	@Transient
	public String getNombre() {
		return nombre;
	}
	
	@Transient
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	@Transient
	public String getDireccion() {
		return direccion;
	}
	
	@Transient
	public void setDireccion(String direccion) {
		this.direccion=direccion;
	}
	
	@Transient
	public String getDescripcion() {
		return descripcion;
	}
	
	@Transient
	public void setDescripcion(String descripcion) {
		this.descripcion=descripcion;
	}
	
	@Transient
	public List<Reserva> getReservas() {
		return reservas;
	}
	
	@Transient
	public List<Mesa> getMesas() {
		return mesas;
	}
	
	@Transient
	public Integer getTotalPlazas() {
		int numMesas=0;
		for (int i = 0; i < mesas.size(); i++){
		    numMesas++;
		}
		return numMesas;
	}

	@Transient
	public Integer getPlazasReservadas(Date fecha, Turno turno) {
		return null;
	}
}