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
	private Usuario user;
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
			this.user = (Usuario) command.getInfo();
			
			
			ThreadCliente threadCliente = new ThreadCliente(objectInputStream, socket,this);
			threadCliente.start();

			// Levanto un hilo que va a estar recibiendo constantemente los mensaje del
			// server
			/*Thread hiloLectura = new Thread(() -> {

				while (socket.isConnected()) {
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

								System.out.println("Soy " + this.user.getUserNickname() + " y recibí respuesta. "
										+ clientSalas.size() + " salas.");
								actualizarSalas(clientSalas);

								break;
							default:
								break;
							}
							
							//newCommand = (Command) objectInputStream.readObject();

						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});*/

			
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

	public void crearSalaEnServer(SalaChat sala) {
		Command crearSalaCommand = new Command(CommandType.CREAR_SALA, sala);

		System.out.println("Voy a pedir crear sala");

		try {
			objectOutputStream.writeObject(crearSalaCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void actualizarSalas(Map<Integer, SalaChat> salasChat) {
		//System.out.println("Soy " + user.getUserNickname() + " y me mandaron a actualizar salas.");
		this.ventanaLobby.lobby.setSalas(salasChat);

		this.ventanaLobby.mostrarSalas(salasChat);
	}

	public void mostrarLobby() {
		ventanaLobby = new VentanaLobby(this.user);
		ventanaLobby.setVisible(true);
		this.getSalas();
	}

}