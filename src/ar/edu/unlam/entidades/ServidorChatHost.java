package ar.edu.unlam.entidades;

import java.util.ArrayList;

public class ServidorChatHost {
	
	private ArrayList<Usuario> usuarios;
	
	
	public ServidorChatHost (String nombreUsuarioHost) {
		
		this.usuarios= new ArrayList<Usuario>();
		this.usuarios.add(new Usuario(0, nombreUsuarioHost));
	}
	
	public void ingresarUsuario(String nombreUsuario) {
		
		usuarios.add(new Usuario(usuarios.get(usuarios.size()-1).getUserID()+1, nombreUsuario));
		 
	}
}


