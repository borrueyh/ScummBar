package com.scummbar.modelo.entities;
import com.scummbar.modelo.*;
import javax.persistence.*;

@Entity
@Table(name = "turnos")
public class Turno {
	
	public Turno() {
		super();
	}
	
	public Turno(Long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "descripcion", nullable = false, length = 500)
	private String descripcion;

	@Transient
	public Long getId() {
		return id;
	}

	@Transient
	public void setId(Long id) {
		this.id=id;
	}
	
	@Transient
	public String getDescripcion() {
		return descripcion;
	}
	
	@Transient
	public void setDescripcion(String descripcion) {
		this.descripcion=descripcion;
	}
}