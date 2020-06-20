package ar.edu.unlam.entidades1;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import ar.edu.unlam.cliente.ventanas.*;

public class App {

	public static void main(String[] args) {

		byte[] a ;
		
		ArrayList<String> mensajes = new ArrayList<String>();
		
		mensajes.add("Mensaje 1");
		mensajes.add("Mensaje 2");
		mensajes.add("Mensaje 3");
		
		String linea="";
		
		for (String string : mensajes) {
			
			linea+=string+="\n";
			
		}
		
		a=linea.getBytes();
		
		String log = new String(a);
		System.out.println(linea);
		System.out.println(log);
		
	}
	
	/*public static byte[] StringToBytes(String cadena){
	   System.Text.ASCIIEncoding codificador = new System.Text.ASCIIEncoding();
	   return codificador.GetBytes(cadena);
	}*/

}

