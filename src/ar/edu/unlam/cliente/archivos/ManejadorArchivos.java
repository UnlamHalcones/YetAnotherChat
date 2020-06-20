package ar.edu.unlam.cliente.archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ManejadorArchivos {

	public static void exportarLogs(byte[] data) {
		
		String log = new String(data);
		
		String fileName = log.substring(0, log.indexOf('\n'));
		
		String path = "Logs/" + fileName + ".log";
		
		BufferedWriter out;

		try {
			
			out = new BufferedWriter(new FileWriter(path, true));
			out.write(new String(log.substring('\n'-3)));
			out.close();
			}
		
		catch(IOException e) {
			
		// error processing code

		System.out.println("Error al leer el archivo. ex: " + e.getMessage());

		}
	}
}
