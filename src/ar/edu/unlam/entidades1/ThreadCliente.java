package ar.edu.unlam.entidades1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

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
			Command comandoRecibido = (Command) objectInputStream.readObject();

			while (!comandoRecibido.getCommandType().equals(CommandType.DISCONNECT)) {
				switch (comandoRecibido.getCommandType()) {
					case INFO_SALAS:
						List<SalaChat> clientSalas = (List<SalaChat>) comandoRecibido.getInfo();
						System.out.println("Recib� respuesta. " + clientSalas.size() + " salas.");
						this.cliente.actualizarSalas(clientSalas);
						break;
					case CREAR_SALA:
						break;
					case UNIRSE_SALA:
						SalaChat salaChat =(SalaChat)comandoRecibido.getInfo();
						this.cliente.crearVentanaChat(salaChat);
						break;
					case ERROR:
						String errorMessage = (String)comandoRecibido.getInfo();
						this.cliente.mostrarError(errorMessage);
						break;
					case MENSAJE:
						Mensaje clientMessage = (Mensaje) comandoRecibido.getInfo();
						this.cliente.actualizarMensajes(clientMessage);
						break;
					case EXPORT_LOG:
						byte[] log = (byte[]) comandoRecibido.getInfo();
						System.out.println(new String(log));
						this.cliente.exportarLog(log);
						break;
					default:
						break;
				}
				
				comandoRecibido = (Command) objectInputStream.readObject();
			}

			socket.close();

		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Error in ThreadUsuario: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
