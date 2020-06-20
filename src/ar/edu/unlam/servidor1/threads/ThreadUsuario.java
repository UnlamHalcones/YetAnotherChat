package ar.edu.unlam.servidor1.threads;


import ar.edu.unlam.entidades1.Command;
import ar.edu.unlam.entidades1.Mensaje;
import ar.edu.unlam.entidades1.Usuario;
import ar.edu.unlam.servidor1.ServidorChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class ThreadUsuario extends Thread {
	private final ServidorChat server;
	private final ObjectInputStream objectInputStream;
	private final ObjectOutputStream objectOutputStream;
	private final Usuario usuario;

	public ThreadUsuario(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream,
                         ServidorChat server, Usuario usuario) {
		this.server = server;
		this.usuario = usuario;
		this.objectOutputStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
	}

	public void run() {
		try {
			boolean isConnected = true;
			Command command = (Command) objectInputStream.readObject();

			while (isConnected) {
				switch (command.getCommandType()) {
					case INFO_SALAS:
						command = server.getInformacionSalas();
						break;
					case CREAR_SALA:
						String nombreSala = (String) command.getInfo();
						command = server.crearSala(nombreSala, this.usuario);
						break;
					case UNIRSE_SALA:
						Long salaId = (Long)command.getInfo();
						command = server.unirserASala(salaId, this.usuario);
						break;
					case SALIR_SALA:
						salaId = (Long)command.getInfo();
						server.salirDeSala(salaId, this.usuario);
						break;

					case MENSAJE:
						System.out.println("Mensaje recibido");
						Mensaje clientMessage = (Mensaje) command.getInfo();
						command = server.procesarMensaje(clientMessage);
						break;
						
						/*if(clientMessage.getUserDest() == null) {
						server.broadcast(clientMessage, this);
						} else {
						server.sendMessageTo(clientMessage);
						}
						break;*/
						/*case DISCONNECT:
				  		server.desconectarUsuario();
				    	isConnected = false;
				  		break;*/
						
					case EXPORT_LOG:
						Long salaID = (Long) command.getInfo();
						command = server.exportarLog(salaID,this.usuario.getId());
						break;
						

					default:
						break;
				}
				if (command != null) {
					this.sendCommand(command);
				}
				command = (Command) objectInputStream.readObject();
			}
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Error in ThreadUsuario: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public synchronized void sendCommand(Command command) {
		try {
			objectOutputStream.reset();
			objectOutputStream.writeObject(command);
		} catch (IOException e) {
			System.err.println("Error mandando informacion al servidor." + e.getMessage());
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ThreadUsuario that = (ThreadUsuario) o;
		return Objects.equals(server, that.server) &&
				Objects.equals(objectInputStream, that.objectInputStream) &&
				Objects.equals(objectOutputStream, that.objectOutputStream) &&
				Objects.equals(usuario, that.usuario);
	}

	@Override
	public int hashCode() {
		return Objects.hash(server, objectInputStream, objectOutputStream, usuario);
	}

	public boolean hasUsuario(Usuario userDestino) {
		return this.usuario.equals(userDestino);
	}
}
