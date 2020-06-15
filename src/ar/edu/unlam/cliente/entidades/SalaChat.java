package ar.edu.unlam.cliente.entidades;

import java.util.ArrayList;

public class SalaChat {

	protected Integer salaId;
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