package ar.edu.unlam.cliente.entidades;

import java.io.Serializable;

public class Mensaje implements Serializable {
	
	private String informacion;
	private Integer user;
	private Integer sala;
	private FechaChat fecha; 
	
	
	public Mensaje(Integer userID, Integer salaID, String informacion) {
		
		this.informacion=informacion;
		this.sala=salaID;
		this.user=userID;
		
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

	@Override
	public String toString() {
		return "[" + fecha.getFecha() + ":" + user + "] " + informacion;
	}
}

	