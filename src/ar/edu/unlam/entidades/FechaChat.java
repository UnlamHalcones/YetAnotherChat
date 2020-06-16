package ar.edu.unlam.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FechaChat extends Date {

	private static final long serialVersionUID = -8393515858837689902L;
	private String fecha;
	private String hora;

	public FechaChat() {
		super();
		separarFechayHora();
	}

	@SuppressWarnings("deprecation")
	private void separarFechayHora() {
		this.hora = String.format("%02d", this.getHours()) + ":" + String.format("%02d", this.getMinutes()) + ":" + String.format("%02d", this.getSeconds());
		this.fecha = String.format("%02d", this.getDay()) + "/" + String.format("%02d", this.getMonth()) + "/" + String.format("%02d", this.getYear() - 100);
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