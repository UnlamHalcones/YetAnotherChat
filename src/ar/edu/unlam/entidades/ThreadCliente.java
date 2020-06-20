package ar.edu.unlam.entidades;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;

public class ThreadCliente extends Thread {
	private Socket socket;
	private Cliente cliente;
	private ObjectInputStream objectInputStream;

	public ThreadCliente(ObjectInputStream objectInputStream, Socket socket, Cliente cliente) {
		this.socket = socket;
		this.cliente = cliente;
		this.objectInputStream = objectInputStream;
	}

	public void run() {
		try {
			Command newCommand = (Command) objectInputStream.readObject();

			while (!newCommand.getCommandType().equals(CommandType.DISCONNECT)) {
				// Hago un broadcast del mensaje, excluyendo al usuario que lo envia
				// TODO hacer el switch gigante
				switch (newCommand.getCommandType()) {
				case MENSAJE:
					Mensaje clientMessage = (Mensaje) newCommand.getInfo();

					break;

				case INFO_SALAS:
					Map<Integer, SalaChat> clientSalas = (Map<Integer, SalaChat>) newCommand.getInfo();

					
					  System.out.println("Recibí respuesta. " + clientSalas.size() + " salas.");
					 
					this.cliente.actualizarSalas(clientSalas);

					break;
				default:
					break;
				}

				newCommand = (Command) objectInputStream.readObject();
			}

			socket.close();

		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Error in ThreadUsuario: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
