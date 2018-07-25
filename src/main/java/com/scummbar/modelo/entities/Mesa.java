package com.scummbar.modelo.entities;
import com.scummbar.modelo.*;
import javax.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {
	
	public Mesa() {
		super();
	}
	
	public Mesa(Long id, Integer numero, Integer capacidad) {
		super();
		this.id = id;
		this.numero = numero;
		this.capacidad = capacidad;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name="numero", unique=true, nullable=false)
	private Integer numero;
	
	@Column(name="capacidad", unique=true, nullable=false)
	private Integer capacidad;

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
	
	@Transient
	public void setCapacidad(Integer capacidad) {
		this.capacidad=capacidad;
	}
}