package ar.edu.unlam.cliente.entidades;
import java.time.Duration;
import java.time.Instant;

import ar.edu.unlam.cliente.ventanas.*;

public class App {

	public static void main(String[] args) {
	
	//	Map<Integer, StopWatch> nombreMap = new HashMap<Integer, StopWatch>();

		
		Instant start = Instant.now();
		
		for(int i=0; i<1000000; i++) {
			for(int j=0; j<1000; j++)
			System.currentTimeMillis();
		}
		Duration timeElapsed = Duration.between(start,Instant.now());
		
		String duracion = FechaChat.mostrarTiempoTranscurrido(timeElapsed.getSeconds());		
		
		System.out.println(duracion);
	}

}
