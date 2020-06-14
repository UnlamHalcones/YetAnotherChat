package ar.edu.unlam.servidor;

import ar.edu.unlam.cliente.entidades.Mensaje;
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
        Usuario user = (Usuario)objectInputStream.readObject();

        Scanner readerFromKB = new Scanner(System.in);

        // Levanto un hilo que va a enviar un mensaje al server cada vez que ingreso por teclado
        Thread hiloEscritura = new Thread(() -> {
            try {
                String str = readerFromKB.nextLine();
                Mensaje mensaje = new Mensaje(user.getUserID(), user.getUserID(), str);
                while (!str.equals("DISCONNECT")) {
                    objectOutputStream.writeObject(mensaje);
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
                    Mensaje messageFromServer = (Mensaje)objectInputStream.readObject();
                    System.out.println(messageFromServer);
                    if(messageFromServer.getInformacion().equals("DISCONNECT")) {
                        closeConnections(socket, readerFromKB);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error con el servidor." + e.getMessage());
                }
            }

        });

        hiloEscritura.start();
        hiloLectura.start();

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
