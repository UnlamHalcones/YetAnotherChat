package ar.edu.unlam.entidades;

import java.util.Date;

public class Mensaje {
	
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


	
	
}

	