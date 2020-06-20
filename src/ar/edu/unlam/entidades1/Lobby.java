package ar.edu.unlam.entidades1;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

	private ArrayList<Usuario> usuarios;
	private List<SalaChat> salas;

	public Lobby() {

		this.usuarios = new ArrayList<Usuario>();
		this.salas = new ArrayList<SalaChat>();
		generadorSalasDefault();

	}

	private void generadorSalasDefault() {
	}

	public List<SalaChat> getSalas() {
		return salas;
	}

	public void setSalas(List<SalaChat> salas) {
		this.salas = salas;
	}

	public SalaChat getSalaById(Long salaOrigenId) {
		return this.salas.stream()
				.filter(salaChat -> salaChat.getId().equals(salaOrigenId))
				.findAny().orElse(null);
	}
}
