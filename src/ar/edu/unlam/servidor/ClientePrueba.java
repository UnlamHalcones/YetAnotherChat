package ar.edu.unlam.servidor;

import ar.edu.unlam.cliente.entidades.Command;
import ar.edu.unlam.cliente.entidades.Mensaje;
import ar.edu.unlam.cliente.entidades.CommandType;
import ar.edu.unlam.servidor.entidades.Usuario;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientePrueba extends Thread {

    public ClientePrueba(String ip, int puerto, String userName) throws IOException, ClassNotFoundException {

        Socket socket = new Socket(ip, puerto);

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        objectOutputStream.writeObject(userName);
        Command command = (Command)objectInputStream.readObject();

        if(command.getCommandType().equals(CommandType.USER)) {
            Scanner readerFromKB = new Scanner(System.in);
            Usuario user = (Usuario)command.getInfo();
            // Levanto un hilo que va a enviar un mensaje al server cada vez que ingreso por teclado
            Thread hiloEscritura = new Thread(() -> {
                try {
                    String str = readerFromKB.nextLine();
                    Mensaje mensaje = new Mensaje(user.getUserID(), user.getUserID(), str);
                    while (!str.equals("DISCONNECT")) {
                        Command mensajeCommand = new Command(CommandType.MENSAJE, mensaje);
                        mensaje = new Mensaje(user.getUserID(), user.getUserID(), str);
                        objectOutputStream.writeObject(mensajeCommand);
                        objectOutputStream.reset();
                        str = readerFromKB.nextLine();
                    }
                    // Envio el comando de desconexion, cierro los streams y el socker
                    objectOutputStream.writeObject(mensaje);
                    objectOutputStream.reset();
                    closeConnections(socket, readerFromKB);
                    System.out.println("disconnect from server");
                    System.exit(0);
                } catch (IOException e) {
                    System.err.println("Error con el servidor." + e.getMessage());
                }
            });

            // Levanto un hilo que va a estar recibiendo constantemente los mensaje del server
            Thread hiloLectura = new Thread(() -> {
                while (socket.isConnected()) {
                    try {
                        Command command1 = (Command)objectInputStream.readObject();
                        if(command1.getCommandType().equals(CommandType.DISCONNECT)) {
                            closeConnections(socket, readerFromKB);
                        }
                        Mensaje serverMessage = (Mensaje)command1.getInfo();
                        System.out.println(serverMessage);
                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("Error con el servidor." + e.getMessage());
                    }
                }
            });

            hiloEscritura.start();
            hiloLectura.start();
        }

    }

    private void closeConnections(Socket socket, Scanner scanner) throws IOException {
        scanner.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            new ClientePrueba("localhost", Integer.valueOf(args[0]), args[1]);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
