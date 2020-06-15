package ar.edu.unlam.cliente.entidades;

import java.io.Serializable;

public class Mensaje implements Serializable {

	private Integer userId;
	private Integer userDest;
	private String informacion;
	private Integer sala;
	private FechaChat fecha;

	public Mensaje(Integer userID, Integer salaID, String informacion) {
		
		this.informacion=informacion;
		this.sala=salaID;
		this.userId =userID;
		
		this.fecha= new FechaChat();
		
	}

	public String getInformacion() {
		return informacion;
	}

	public String getFecha() {
		return this.fecha.getFecha();
	}

	public String getHora() {
		return this.fecha.getHora();
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setSala(Integer sala) {
		this.sala = sala;
	}

	public void setFecha(FechaChat fecha) {
		this.fecha = fecha;
	}
	
	public Integer getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "[" + fecha.getFecha() + ":" + userId + "] " + informacion;
	}
}

	