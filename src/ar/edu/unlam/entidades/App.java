package ar.edu.unlam.entidades;
import ar.edu.unlam.ventanas.*;

public class App {

	public static void main(String[] args) {
	
		/*	
		try {
			VentanaChat frame = new VentanaChat();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			Mensaje mensjTest = new Mensaje(01, 1, "Es el primer Mensaje de Prueba");
			
			System.out.println(mensjTest.getFecha());
			
			*/
		
		
		ServidorChatHost servidorLocalTest = new ServidorChatHost("Francisco Pretto");
		servidorLocalTest.ingresarUsuario("Profesor de Programacion");
		
		
		
	}

}
