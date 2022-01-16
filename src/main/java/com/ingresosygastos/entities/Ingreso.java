package com.ingresosygastos.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Ingreso {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private Integer montoCobrado;
	
	@OneToOne
	private Abuela abuela;
	
	@Temporal(TemporalType.DATE)
	private Date creado;

	private boolean activo;

	public Ingreso () {
		activo = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMontoCobrado() {
		return montoCobrado;
	}

	public void setMontoCobrado(Integer montoCobrado) {
		this.montoCobrado = montoCobrado;
	}

	public Abuela getAbuela() {
		return abuela;
	}

	public void setAbuela(Abuela abuela) {
		this.abuela = abuela;
	}

	public Date getCreado() {
		return creado;
	}

	public void setCreado(Date creado) {
		this.creado = creado;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}	
