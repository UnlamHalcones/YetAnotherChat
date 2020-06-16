package ar.edu.unlam.cliente.ventanas;

import javax.swing.JFrame;

import ar.edu.unlam.entidades.SalaChat;

public class VentanaCrearSala extends JFrame{

	private static final long serialVersionUID = 6073064375808550601L;
	private SalaChat salaChat;
	
	public VentanaCrearSala() {
		this.salaChat = new SalaChat(0, "Sala de Mingo", 5);
	}

	public SalaChat getSalaChat() {
		return salaChat;
	}
	
}
