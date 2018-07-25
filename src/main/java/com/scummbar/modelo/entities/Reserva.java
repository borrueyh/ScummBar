package com.scummbar.modelo.entities;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "reservas")
public class Reserva {
	
	
	public Reserva() {
		super();
	}

	public Reserva(Long id, Date dia, Integer persona, String localizador, Turno turno) {
		super();
		this.id = id;
		this.dia = dia;
		this.persona = persona;
		this.localizador = localizador;
		this.turno = turno;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name="dia", nullable=false)
	private Date dia;
	
	@Column(name="personas", nullable=false)
	private Integer persona;
	
	@Column(name="localizador", unique=true, nullable=false)
	private String localizador;
	
	@ManyToOne
	private Turno turno;
	
	@ManyToOne
	private Mesa mesa;

	//Pruebaaaaaaaaaaaaaaaaaa
	@OneToOne
	@JoinColumn(name = "restaurante_Id", nullable = false, insertable=true)
	private Restaurante restaurante; 
	
	@Transient
	public Long getId() {
		return id;
	}
	
	@Transient
	public void setId(Long id) {
		this.id=id;
	}
	
	@Transient
	public Date getDia() {
		return dia;
	}
	
	@Transient
	public void setDia(Date dia) {
		this.dia=dia;
	}
	
	@Transient
	public Integer getPersonas() {
		return persona;
	}
	
	@Transient
	public void setPersonas(Integer persona) {
		this.persona=persona;
	}
	
	@Transient
	public String getLocalizador() {
		return localizador;
	}
	
	@Transient
	public void setLocalizador(String localizador) {
		this.localizador=localizador;
	}
	
	@Transient
	public Turno getTurno() {
		return turno;
	}
	
	@Transient
	public void setTurno(Turno turno) {
		this.turno=turno;
	}
	
	@Transient
	public Mesa getMesa() {
		return mesa;
	}
	
	@Transient
	public void setMesa(Mesa mesa) {
		this.mesa=mesa;
	}
	
	@Transient
	public Restaurante getRestaurante() {
		return restaurante;
	}
	
	@Transient
	public void setRestaurante(Restaurante restaurante) {
		this.restaurante=restaurante;
	}
	
}