package ar.edu.unlam.servidor.entidades;

import java.util.ArrayList;

public class SalaChat {

	protected Integer salaId;
	protected Usuario creador;
	protected String nombreSala;
	private Integer userMax;

	private ArrayList<Usuario> usuariosConectados;

	//Constructor para cuando la crea el server
	public SalaChat(Integer salaID, String nombreSala, Integer usrMax) {

		this.salaId = salaID;
		this.userMax = usrMax;
		this.nombreSala = nombreSala;
		this.usuariosConectados = new ArrayList<Usuario>();

	}
	
	//Constructor para cuando la crea un usuario y no el server
	public SalaChat(String nombreSala, Integer usrMax, Usuario user) {
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
}
