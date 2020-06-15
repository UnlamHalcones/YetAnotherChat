package ar.edu.unlam.servidor.entidades;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ar.edu.unlam.cliente.entidades.FechaChat;

public class SalaChat {

	protected Integer salaId;
	protected String nombreSala;
	private Integer userMax;

	private ArrayList<Usuario> usuariosConectados;
	
	 private Map<Integer, Instant> tiempoUsuarioConectado;
	    
	//Constructor para cuando la crea el server
	public SalaChat(Integer salaID, String nombreSala, Integer usrMax) {

		this.salaId = salaID;
		this.userMax = usrMax;
		this.nombreSala = nombreSala;
		this.usuariosConectados = new ArrayList<Usuario>();
		this.tiempoUsuarioConectado = new HashMap<Integer, Instant>(); // Hay que borrar al usuario cuando se desconecta

	}
	
	//Constructor para cuando la crea un usuario y no el server
	public SalaChat(Integer salaID, String nombreSala, Integer usrMax, Usuario user) {

		this.salaId = salaID;
		this.userMax = usrMax;
		this.nombreSala = nombreSala;
		this.usuariosConectados = new ArrayList<Usuario>();
		this.usuariosConectados.add(user);
	}

	public String agregarUsuarioSala(Usuario usr) {

		if (estaLlena()) {
			return "La sala esta llena";
		}

		this.usuariosConectados.add(usr);
		iniciarTiempoConectado(usr.getUserID());
		return "";

	}
	
	 private void iniciarTiempoConectado(Integer usrId) {
		    
	    	this.tiempoUsuarioConectado.put(usrId, Instant.now());   	
	    }
	    
	    public String getTiempoConectado(Integer userId) {
	    	
	    	Duration timeElapsed = 
	    				Duration.between(tiempoUsuarioConectado.get(userId),Instant.now());
	    	
	    	return FechaChat.mostrarTiempoTranscurrido(timeElapsed.getSeconds());
	    }
	public Integer getSalaId() {
		return salaId;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public Integer getUsuariosConectados() {
		return this.usuariosConectados.size();
	}

	public boolean estaLlena() {
		return this.getUsuariosConectados() == this.userMax;
	}
}
