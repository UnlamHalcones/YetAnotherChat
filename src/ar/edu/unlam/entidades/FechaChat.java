package ar.edu.unlam.entidades;

import java.util.Date;

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

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

}