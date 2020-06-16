package ar.edu.unlam.cliente.archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ManejadorArchivos {

	public void guardarLogMensajes(String logMensajes) {
		
		String pathDefinitivo = File.separator + "registro.csv";
		
		BufferedWriter out;

		try {
			
			out = new BufferedWriter(new FileWriter(pathDefinitivo, true));
			out.write(logMensajes);
			out.close();
			}
		
		catch(IOException e) {
			
		// error processing code

		System.out.println("Error al leer el archivo");

		}
	}
}
