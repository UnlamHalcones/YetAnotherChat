package ar.edu.unlam.cliente.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Lobby {

	private ArrayList<Usuario> usuarios;
	private Map<Integer, SalaChat> salas;

	public Lobby() {

		this.usuarios = new ArrayList<Usuario>();
		this.salas = new HashMap<Integer, SalaChat>();
		generadorSalasDefault();

	}

	private void generadorSalasDefault() {

		salas.put(0, new SalaChat(0, "General", 15));
		salas.put(1, new SalaChat(1, "Sala 1", 10));
		salas.put(2, new SalaChat(2, "Sala 2", 10));
		salas.put(3, new SalaChat(3, "Sala 3", 10));
		salas.put(4, new SalaChat(4, "Sala 4", 10));
		salas.put(5, new SalaChat(5, "Sala 5", 10));
		salas.put(6, new SalaChat(6, "Sala 6", 10));
		
//		salas.put(7, new SalaChat(7, "Sala 7", 10));
//		salas.put(8, new SalaChat(8, "Sala 8", 10));
//		salas.put(9, new SalaChat(9, "Sala 9", 10));
//		salas.put(10, new SalaChat(10, "Sala 10", 10));
//		salas.put(11, new SalaChat(11, "Sala 11", 10));
//		salas.put(12, new SalaChat(12, "Sala 12", 10));
//		
//		salas.put(13, new SalaChat(13, "Sala 13", 10));
//		salas.put(14, new SalaChat(14, "Sala 14", 10));
//		salas.put(15, new SalaChat(15, "Sala 15", 10));
//		salas.put(16, new SalaChat(16, "Sala 16", 10));
//		salas.put(17, new SalaChat(17, "Sala 17", 10));
//		salas.put(18, new SalaChat(18, "Sala 18", 10));
		
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

		if (sala == null) {
			return "La sala no existe";
		}

		return sala.agregarUsuarioSala(user);
	}

	public Map<Integer, SalaChat> getSalas() {
		return salas;
	}

}
