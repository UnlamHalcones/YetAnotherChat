package ar.edu.unlam.entidades;

import java.util.ArrayList;
import java.util.Map;

public class Lobby {

		private ArrayList<SalaChat> salas;
		private Map<Integer,ArrayList <Integer>> usuariosPorSala; //= new HashMap<Integer, String>();
		
		public Lobby () {
			
			this.salas= new ArrayList<SalaChat>();
			generadorSalasDefault();
			
		}
		
		private void generadorSalasDefault() {
			
			salas.add(new SalaChat(0,"General", 15));
			salas.add(new SalaChat(1,"Sala 1", 10));
			salas.add(new SalaChat(2,"Sala 2", 10));
			salas.add(new SalaChat(3,"Sala 3", 10));
			
		}
}
