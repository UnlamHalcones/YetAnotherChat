package ar.edu.unlam.entidades1;

import ar.edu.unlam.cliente.ventanas.VentanaChat;
import ar.edu.unlam.cliente.ventanas.VentanaLobby;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
			ThreadCliente threadCliente = new ThreadCliente(objectInputStream, socket, this);
			threadCliente.start();
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

	public Usuario getUser() {
		return user;
	}

	public void getSalas() {
		System.out.println("Voy a pedir salas");
		Command getSalasCommand = new Command(CommandType.INFO_SALAS, null);
		this.sendCommand(getSalasCommand);
	}

	public void crearSalaEnServer(String nombreSala) {
		System.out.println("Voy a pedir crear sala");
		Command crearSalaCommand = new Command(CommandType.CREAR_SALA, nombreSala);
		this.sendCommand(crearSalaCommand);
	}

	public void actualizarSalas(List<SalaChat> salasChat) {
		System.out.println("Soy " + user.getUserName() + " y me mandaron a actualizar salas.");
		this.ventanaLobby.actualizarSalas(salasChat);
	}

	public void mostrarLobby() {
		ventanaLobby = new VentanaLobby(this.user);
		ventanaLobby.setVisible(true);
		this.getSalas();
	}

	public void unirseASala(Long salaId) {
		System.out.println("Voy a pedir unirme a sala id: " + salaId.toString());
		Command unirseASala = new Command(CommandType.UNIRSE_SALA, salaId);
		this.sendCommand(unirseASala);
	}

	private void sendCommand(Command command) {
		try {
			objectOutputStream.reset();
			objectOutputStream.writeObject(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crearVentanaChat(SalaChat salaChat) {
		this.ventanaLobby.crearVentanaChat(salaChat);
	}

	public void mostrarError(String errorMessage) {
		this.ventanaLobby.mostrarVentanaError(errorMessage);
	}

	public void enviarMensaje(Usuario usuarioSeleccionado, SalaChat salaChat, String mensaje) {
		System.out.println("Enviando mensae desde cleinte");
		Mensaje clientMessage = new Mensaje(this.user.getId(), usuarioSeleccionado.getId(), salaChat.getId(), mensaje);
		Command command = new Command(CommandType.MENSAJE, clientMessage);
		this.sendCommand(command);
	}

	public void enviarMensaje(SalaChat salaChat, String mensaje) {
		System.out.println("Enviando mensae desde cleinte");
		Mensaje clientMessage = new Mensaje(this.user.getId(), salaChat.getId(), mensaje);
		Command command = new Command(CommandType.MENSAJE, clientMessage);
		this.sendCommand(command);
	}

	public void actualizarMensajes(Mensaje clientMessage) {
		this.ventanaLobby.actualizarMensajes(clientMessage);
	}

	public void actualizarUsuariosEnSala(SalaChat sala) {
		VentanaChat ventanaChat = this.ventanaLobby.getVentanaPorSalaChat(sala);

		if (ventanaChat != null) {
			ventanaChat.actualizarUsuarios(sala.getUsuariosInSala());
		}else
		{
			System.out.println("Tengo que actualizar usuarios pero no encontre la ventana");
		}
		
	}
}