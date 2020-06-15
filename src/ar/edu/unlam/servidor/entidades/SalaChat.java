package ar.edu.unlam.servidor.entidades;

import java.util.ArrayList;
import java.util.List;

public class SalaChat {

	protected Integer salaId;
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
		return "";

	}

	public boolean hasUser(Usuario usuario) {
		return usuariosConectados.contains(usuario);
	}

	public boolean hasUser(Integer userId) {
		for(Usuario usuario : usuariosConectados) {
			if(usuario.getUserID().equals(userId)) {
				return true;
			}
		}
		return false;
	}

	public Usuario getUserFromSala(Integer userId) {
		Usuario user = null;
		for(Usuario usuario : usuariosConectados) {
			if(usuario.getUserID().equals(userId)) {
				user = usuario;
				break;
			}
		}
		return user;
	}

	public void setUsuariosConectados(ArrayList<Usuario> usuariosConectados) {
		this.usuariosConectados = usuariosConectados;
	}

	public List<Usuario> getUsuarios() {
		return (List<Usuario>)usuariosConectados.clone();
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
