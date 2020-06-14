package ar.edu.unlam.servidor.threads;

import ar.edu.unlam.servidor.ServidorChat;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ThreadUsuario extends Thread {
    private Socket socket;
    private ServidorChat server;
    private DataOutputStream writer;

    public ThreadUsuario(Socket socket, ServidorChat server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            DataInputStream reader = new DataInputStream(input);
//            BufferedReader reader = new DataInputStream(input);

            OutputStream output = socket.getOutputStream();
            writer = new DataOutputStream(output);
//            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readUTF();
            server.addUserName(userName);

            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readUTF();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);

            } while (!clientMessage.equals("DISCONNECT"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);

        } catch (IOException ex) {
            System.out.println("Error in ThreadUsuario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() {
        try {
            if (server.hasUsers()) {
                writer.writeUTF("Connected users: " + server.getUserNames());
            } else {
                writer.writeUTF("No other users connected");
            }
        } catch (IOException e) {
            System.err.println("Error mandando informacion al servidor." + e.getMessage());
        }
    }

    /**
     * Sends a message to the client.
     */
    public void sendMessage(String message) {
        try {
            writer.writeUTF(message);
        } catch (IOException e) {
            System.err.println("Error mandando informacion al servidor." + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadUsuario that = (ThreadUsuario) o;
        return Objects.equals(socket, that.socket) &&
                Objects.equals(server, that.server) &&
                Objects.equals(writer, that.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, server, writer);
    }
}