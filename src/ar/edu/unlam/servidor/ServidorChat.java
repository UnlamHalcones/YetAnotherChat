package ar.edu.unlam.servidor.entidades;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServidorChat {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    private Set<SalaChat> salas = new HashSet<>();

    public ServidorChat(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        ServidorChat servidorChat = new ServidorChat(9091);
        servidorChat.execute();
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                /*ThreadUsuario newUser = new ThreadUsuario(socket, this);
                userThreads.add(newUser);
                newUser.start();*/
            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
