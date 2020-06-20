package ar.edu.unlam.entidades;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import ar.edu.unlam.cliente.ventanas.VentanaLobby;

public class Cliente extends Thread {

	private OutputStream outputStream;
	private InputStream inputStream;
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private static Cliente instance;

	public VentanaLobby ventanaLobby;

	public Cliente() {

	}

	public void init(String ip, int puerto, String userName) throws IOException, ClassNotFoundException {

		socket = new Socket(ip, puerto);
		outputStream = socket.getOutputStream();
		inputStream = socket.getInputStream();

		objectOutputStream = new ObjectOutputStream(outputStream);
		objectInputStream = new ObjectInputStream(inputStream);

		objectOutputStream.writeObject(userName);
		Command command = (Command) objectInputStream.readObject();

		if (command.getCommandType().equals(CommandType.USER)) {
			Scanner readerFromKB = new Scanner(System.in);
			Usuario user = (Usuario) command.getInfo();
			ventanaLobby = new VentanaLobby(user);
			ventanaLobby.setVisible(true);
		

			// Levanto un hilo que va a estar recibiendo constantemente los mensaje del
			// server
			Thread hiloLectura = new Thread(() -> {
				Command newCommand = command;
				while (socket.isConnected()) {

					while (!newCommand.getCommandType().equals(CommandType.DISCONNECT)) {
						// Hago un broadcast del mensaje, excluyendo al usuario que lo envia
						// TODO hacer el switch gigante
						switch (newCommand.getCommandType()) {
						case MENSAJE:
							Mensaje clientMessage = (Mensaje) newCommand.getInfo();

							break;

						case INFO_SALAS:
							Map<Integer, SalaChat> clientSalas = (Map<Integer, SalaChat>) newCommand.getInfo();

							System.out.println("Recibí respuesta");
							actualizarSalas(clientSalas);

							break;
						default:
							break;
						}

						try {
							newCommand = (Command) objectInputStream.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});

			hiloLectura.start();
		}
	}

	public static Cliente getInstance() {

		if (instance == null) {
			instance = new Cliente();
		}

		return instance;
	}

	private void closeConnections(Socket socket, Scanner scanner) throws IOException {
		scanner.close();
		socket.close();
	}

	public void getSalas() {
		Command getSalasCommand = new Command(CommandType.INFO_SALAS, null);

		System.out.println("Voy a pedir salas");

		try {
			objectOutputStream.writeObject(getSalasCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void actualizarSalas(Map<Integer, SalaChat> salasChat) {
		System.out.println("Actualizo salas");
		this.ventanaLobby.lobby.setSalas(salasChat);
		
		this.ventanaLobby.mostrarSalas();
	}

}