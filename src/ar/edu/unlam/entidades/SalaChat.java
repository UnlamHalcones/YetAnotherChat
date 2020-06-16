package ar.edu.unlam.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class SalaChat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8741229406764772785L;
	protected Integer salaId;
	protected Usuario creador;
	protected String nombreSala;
	private Integer userMax;
	private FechaChat fechaCreacion;
	private ArrayList<Usuario> usuariosConectados;

	// Constructor para cuando la crea el server
	public SalaChat(Integer salaID, String nombreSala, Integer usrMax) {

		this.salaId = salaID;
		this.userMax = usrMax;
		this.nombreSala = nombreSala;
		this.usuariosConectados = new ArrayList<Usuario>();
		this.fechaCreacion = new FechaChat();
		
	}

	// Constructor para cuando la crea un usuario y no el server
	public SalaChat(Integer salaID, String nombreSala, Integer usrMax, Usuario user) {

		this.salaId = salaID;
		this.userMax = usrMax;
		this.nombreSala = nombreSala;
		this.usuariosConectados = new ArrayList<Usuario>();
		this.usuariosConectados.add(user);
		this.fechaCreacion = new FechaChat();
		
	}

	public String agregarUsuarioSala(Usuario usr) {

		if (estaLlena()) {
			return "La sala esta llena";
		}

		this.usuariosConectados.add(usr);
		return "";

	}
	
	public String salirUsuarioSala(Usuario usr) {

		Optional<Usuario> usuario = usuariosConectados.stream().filter(x -> x.getUserID().equals(usr.getUserID())).findFirst();
		if (usuario.isPresent()) {
			return "El usuario no se encuentra en la sala";
		}

		this.usuariosConectados.remove(usr);
		return "";

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

	public FechaChat getFechaCreacion() {
		return fechaCreacion;
	}
	
}