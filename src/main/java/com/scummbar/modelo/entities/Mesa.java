package com.scummbar.modelo.entities;
import com.scummbar.modelo.*;
import javax.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {
	
	public Mesa() {
		super();
	}
	
	public Mesa(Long id, Integer numero, Integer capacidad, Boolean disponibilidad) {
		super();
		this.id = id;
		this.numero = numero;
		this.capacidad = capacidad;
		this.disponibilidad=disponibilidad;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name="numero", unique=true, nullable=false)
	private Integer numero;
	
	@Column(name="capacidad", unique=true, nullable=false)
	private Integer capacidad;
	
	//Indica si la mesa se encuentra disponible o no (con true o false)
	@Column(name="disponibilidad", nullable=false)
	private Boolean disponibilidad;

	@Transient
	public Long getId() {
		return id;
	}
	
	@Transient
	public void setId(Long id) {
		this.id=id;
	}
	
	@Transient
	public Integer getNumero() {
		return numero;
	}

	@Transient
	public void setNumero(Integer numero) {
		this.numero=numero;
	}
	
	@Transient
	public Integer getCapacidad() {
		return capacidad;
	}
	
//	 public getTotalPlazas() {
//		 return null;
//	 }
	
	@Transient
	public void setCapacidad(Integer capacidad) {
		this.capacidad=capacidad;
	}
	
	@Transient
	public Boolean getDisponibilidad() {
		return disponibilidad;
	}

	@Transient
	public void setDisponibilidad(Boolean disponibilidad) {
		this.disponibilidad=disponibilidad;
	}
}