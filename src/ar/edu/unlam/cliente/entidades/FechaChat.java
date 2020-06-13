package ar.edu.unlam.cliente.entidades;

import java.util.Date;

public class FechaChat extends Date{
	
	
	private String fecha;
	private String hora;
	
	public FechaChat() {
		
		super();
		separarFechayHora();
	}
	
	private  void separarFechayHora() {
		
		this.hora=this.getHours()+":"+this.getMinutes()+":"+this.getSeconds();
		this.fecha=this.getDay()+"/"+this.getMonth()+"/"+(this.getYear()-100);
		
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}
	
	
	
}