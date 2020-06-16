package ar.edu.unlam.entidades;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Cliente extends Thread {

	private OutputStream outputStream;
    private InputStream inputStream;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public Lobby lobby = new Lobby();
    
    public Cliente(String ip, int puerto, String userName) throws IOException, ClassNotFoundException {

        socket = new Socket(ip, puerto);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();

        objectOutputStream = new ObjectOutputStream(outputStream);
        objectInputStream = new ObjectInputStream(inputStream);

        objectOutputStream.writeObject(userName);
        Command command = (Command)objectInputStream.readObject();

        if(command.getCommandType().equals(CommandType.USER)) {
            Scanner readerFromKB = new Scanner(System.in);
            Usuario user = (Usuario)command.getInfo();

            // Levanto un hilo que va a estar recibiendo constantemente los mensaje del server
            Thread hiloLectura = new Thread(() -> {
                while (socket.isConnected()) {
                	
                    while (!command.getCommandType().equals(CommandType.DISCONNECT)) {
						// Hago un broadcast del mensaje, excluyendo al usuario que lo envia
						// TODO hacer el switch gigante
						
						switch (command.getCommandType()) {
						case MENSAJE:
							Mensaje clientMessage = (Mensaje) command.getInfo();
							
							break;

						case INFO_SALAS:
							 Map<Integer, SalaChat> clientSalas = (Map<Integer, SalaChat>) command.getInfo();
							
							actualizarSalas(clientSalas);
							
							break;
						default:
							break;
						}
						
						//command = (Command) objectInputStream.readObject();
					}
                }
            });

            hiloLectura.start();
        }

    }

    private void closeConnections(Socket socket, Scanner scanner) throws IOException {
        scanner.close();
        socket.close();
    }
    
    public void getSalas (){
    	Command getSalasCommand = new Command(CommandType.INFO_SALAS, null);
    	
    	try {
			objectOutputStream.writeObject(getSalasCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void actualizarSalas(Map<Integer, SalaChat>  salasChat)
    {
    	lobby.setSalas(salasChat);
    }
    

    public static void main(String[] args) {
        try {
            new Cliente("localhost", Integer.valueOf(args[0]), args[1]);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}