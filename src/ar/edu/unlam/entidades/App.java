package ar.edu.unlam.entidades;
import java.util.ArrayList;

public class App {

	public static void main(String[] args) {

		byte[] a;

		ArrayList<String> mensajes = new ArrayList<String>();

		mensajes.add("Mensaje 1");
		mensajes.add("Mensaje 2");
		mensajes.add("Mensaje 3");

		String linea = "";

		for (String string : mensajes) {
			linea += string += "\n";
		}
		a = linea.getBytes();
		String log = new String(a);

	}

}

