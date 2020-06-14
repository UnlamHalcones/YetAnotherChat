package ar.edu.unlam.servidor.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Lobby {

	private ArrayList<Usuario> usuarios;
	private Map<Integer, SalaChat> salas;
	private Map<Integer, ArrayList<Integer>> usuariosPorSala; // = new HashMap<Integer, String>();

	public Lobby() {

		this.usuarios = new ArrayList<Usuario>();
		this.salas = new HashMap<Integer, SalaChat>();
		this.usuariosPorSala = new HashMap<Integer, ArrayList<Integer>>();
		generadorSalasDefault();

	}

	private void generadorSalasDefault() {

		salas.put(0, new SalaChat(0, "General", 15));
		salas.put(1, new SalaChat(1, "Sala 1", 10));
		salas.put(2, new SalaChat(2, "Sala 2", 10));
		salas.put(3, new SalaChat(3, "Sala 3", 10));

	}

	public String agregarUsuarioSala(Usuario user, Integer salaId) {

		// verificar si ya esta conectado a 3 salas el usuario
		if (user.getCantidadSalasConectadas() >= 3)
			return "Superó el maximo de salas conectado";
		
		this.salas.get(salaId).agregarUsuarioSala(user);
		
		return "";
	}

	public void ingresarUsuario(String nombreUsuario) {

		if (!usuarios.isEmpty())
			usuarios.add(new Usuario(usuarios.get(usuarios.size() - 1).getUserID() + 1, nombreUsuario));
		else
			usuarios.add(new Usuario(0, nombreUsuario));
	}

	public ArrayList<Usuario> getUsuarios() {
		return (ArrayList<Usuario>) usuarios.clone();
	}
	
	public String crearSala(String nombreSala, Integer cantidadUsuarios, Usuario user) {
		
		if (salas.values().stream().filter(s -> s.nombreSala == nombreSala).count() > 0)
			return "Ya existe una sala con ese nombre";
		
		if (user.getCantidadSalasConectadas() >= 3)
			return "Superó el maximo de salas conectado";
		
		Integer maxKey = Collections.max(salas.keySet()) + 1;
		
		salas.put(maxKey, new SalaChat(maxKey, nombreSala, cantidadUsuarios, user));
		
		return "";
	}
	
	public String unirseASala(Integer salaId, Usuario user) {
		
		SalaChat sala = salas.get(salaId);
		
		if (sala == null)
		{
			return "La sala no existe";
		}
		
		return sala.agregarUsuarioSala(user);
	}
	
	public Map<Integer, SalaChat> getSalas(){
		return salas;
	}
	
}
