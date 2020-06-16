package ar.edu.unlam.entidades;

import java.util.ArrayList;
import java.util.Map;

public class Chat {
	
	private ArrayList<String> usuarios; //= new ArrayList<String>(2);
	
	
	public Chat (String usr1, String usr2) {
		
		this.usuarios= new ArrayList<String>(2);
		this.usuarios.add(usr1);
		this.usuarios.add(usr2);
		
	}
	
	public String get_usr1() {
		
		return this.usuarios.get(0);
	}
	
	public String get_usr2() {
		
		return this.usuarios.get(1);
	}
}
