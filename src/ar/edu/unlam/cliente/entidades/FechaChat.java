package ar.edu.unlam.cliente.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
	
	public static String mostrarTiempoTranscurrido(long segEntry) {
		
		Date d = new Date(segEntry*1000L);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.format(d);
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}
	
	
	
}