package ar.edu.unlam.cliente.archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ManejadorArchivos {

	public static void exportarLogs(byte[] log) {
		
		String path ="logs"+File.separator+"test.log";
		
		BufferedWriter out;

		try {
			
			out = new BufferedWriter(new FileWriter(path, true));
			out.write(new String(log));
			out.close();
			}
		
		catch(IOException e) {
			
		// error processing code

		System.out.println("Error al leer el archivo");

		}
	}
}
