package ar.edu.unlam.servidor.threads;

import ar.edu.unlam.entidades.Command;
import ar.edu.unlam.entidades.CommandType;
import ar.edu.unlam.entidades.Mensaje;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.entidades.SalaChat;
import ar.edu.unlam.entidades.Usuario;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ThreadUsuario extends Thread {
	private Socket socket;
	private ServidorChat server;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutpuStream;
	private Usuario usuario;

	public ThreadUsuario(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socket,
			ServidorChat server, Usuario usuario) {
		this.socket = socket;
		this.server = server;
		this.usuario = usuario;
		this.objectOutpuStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
	}

	public void run() {
		try {
			Command command = (Command) objectInputStream.readObject();

			while (!command.getCommandType().equals(CommandType.DISCONNECT)) {
				// Hago un broadcast del mensaje, excluyendo al usuario que lo envia
				// TODO hacer el switch gigante
				
				switch (command.getCommandType()) {
				case MENSAJE:
					Mensaje clientMessage = (Mensaje) command.getInfo();
					if(clientMessage.getUserDest() == null) {
						server.broadcast(clientMessage, this);
					} else {
						server.sendMessageTo(clientMessage);
					}
					break;

				case UNIRSE_SALA:
					Integer unirseSalaId = (Integer)command.getInfo();
					server.getLobby().unirseASala(unirseSalaId, usuario);
					break;
				case SALIR_SALA:				
					Integer salirSalaId = (Integer)command.getInfo();
					server.getLobby().salirDeSala(salirSalaId, usuario);
					break;

				case CREAR_SALA:
					System.out.println("Me pidieron que cree una sala");
					SalaChat salaChat = (SalaChat) command.getInfo();
					String crearSalaResponse = server.lobby.crearSala(salaChat);
					
					if (crearSalaResponse.isEmpty()) {
						responderSalas();
					}
					else {
						Command errorCommand = new Command(CommandType.ERROR, crearSalaResponse);
						sendCommand(errorCommand);
					}
					break;
				case INFO_SALAS:
					System.out.println("Me pidieron salas");
					responderSalas();
					break;
				default:
					break;
				}

				command = (Command) objectInputStream.readObject();
			}

			server.removeUser(usuario, this);
			socket.close();

			String clientDisconnectMessage = usuario.getUserNickname() + " has quitted.";
			server.broadcast(clientDisconnectMessage, this);

		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Error in ThreadUsuario: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void responderSalas() {

		System.out.println("respondo salas");
		System.out.println("Tengo " + server.lobby.getSalas().size() + " salas en el server");
		Command responseCommand = new Command(CommandType.INFO_SALAS, server.getLobby().getSalas());
		
		
		server.broadcastSalas(responseCommand, null);
	}

	/**
	 * Sends a message to the client.
	 */
	public void sendMessage(String message) {
		try {
			objectOutpuStream.reset();
			objectOutpuStream.writeObject(message);
		} catch (IOException e) {
			System.err.println("Error mandando informacion al servidor." + e.getMessage());
		}
	}

	/**
	 * Sends a message to the client.
	 */
	public void sendMessage(Mensaje message) {
		try {
			Command mensajeCommand = new Command(CommandType.MENSAJE, message);
			objectOutpuStream.reset();
			objectOutpuStream.writeObject(mensajeCommand);
		} catch (IOException e) {
			System.err.println("Error mandando informacion al servidor." + e.getMessage());
		}
	}
	
	public void sendCommand(Command command) {
		try {
			objectOutpuStream.reset();
			objectOutpuStream.writeObject(command);
		} catch (IOException e) {
			System.err.println("Error mandando informacion al servidor." + e.getMessage());
		}
	}

	public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ThreadUsuario that = (ThreadUsuario) o;
        return Objects.equals(socket, that.socket) && Objects.equals(server, that.server)
                && Objects.equals(objectInputStream, that.objectInputStream)
                && Objects.equals(objectOutpuStream, that.objectOutpuStream) && Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, server, objectInputStream, objectOutpuStream, usuario);
    }
}
