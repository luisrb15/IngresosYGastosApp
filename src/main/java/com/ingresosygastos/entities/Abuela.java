package com.ingresosygastos.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Abuela {
	
	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "ABUELAS_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Integer documento;
	private String nombre;
	private String apellido;
	private String apodo;
	private String email;
	private Integer precio; 
	private Integer pagado;
	
	@Temporal(TemporalType.DATE)
	private Date creado;

	@Temporal(TemporalType.DATE)
	private Date editado;

	private boolean activo;

	public Abuela() {
		activo = true;
	}

	public Abuela(Long id, Integer documento, String nombre, String apellido, String apodo, String email,
			Integer precio, Integer pagado, Date creado, Date editado, boolean activo) {
		this.id = id;
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.apodo = apodo;
		this.email = email;
		this.precio = precio;
		this.pagado = pagado;
		this.creado = creado;
		this.editado = editado;
		this.activo = activo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDocumento() {
		return documento;
	}

	public void setDocumento(Integer documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Integer getPagado() {
		return pagado;
	}

	public void setPagado(Integer pagado) {
		this.pagado = pagado;
	}

	public Date getCreado() {
		return creado;
	}

	public void setCreado(Date creado) {
		this.creado = creado;
	}

	public Date getEditado() {
		return editado;
	}

	public void setEditado(Date editado) {
		this.editado = editado;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
}	
