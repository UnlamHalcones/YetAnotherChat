package ar.edu.unlam.servidor;

import ar.edu.unlam.servidor.threads.ThreadUsuario;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServidorChat {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<ThreadUsuario> userThreads = new HashSet<>();

    public ServidorChat(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        ServidorChat servidorChat = new ServidorChat(Integer.valueOf(args[0]));
        servidorChat.execute();
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);
            while (true) {
                // Acepto conexion
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                ThreadUsuario threadUsuario = new ThreadUsuario(socket, this);
                userThreads.add(threadUsuario);
                threadUsuario.start();
            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Delivers a message from one user to others (broadcasting)
     */
    public synchronized void broadcast(String message, ThreadUsuario excludeUser) {
        for (ThreadUsuario aUser : userThreads) {
            if (!aUser.equals(excludeUser)) {
                aUser.sendMessage(message);
            }
        }
    }

    /**
     * Stores username of the newly connected client.
     */
    public void addUserName(String userName) {
        userNames.add(userName);
    }

    /**
     * When a client is disconneted, removes the associated username and ThreadUsuario
     */
    public void removeUser(String userName, ThreadUsuario aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    public Set<String> getUserNames() {
        return this.userNames;
    }

    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    public boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
