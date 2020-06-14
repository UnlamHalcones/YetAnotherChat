package ar.edu.unlam.servidor.threads;

import ar.edu.unlam.cliente.entidades.Mensaje;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.servidor.entidades.Usuario;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ThreadUsuario extends Thread {
    private Socket socket;
    private ServidorChat server;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutpuStream;
    private Usuario usuario;

    public ThreadUsuario(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream,
                         ServidorChat server, Usuario usuario) {
        this.socket = socket;
        this.server = server;
        this.usuario = usuario;
        this.objectOutpuStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public void run() {
        try {
            Mensaje clientMessage = (Mensaje) objectInputStream.readObject();

            while(!clientMessage.getInformacion().equals("DISCONNECT")) {
                // Hago un broadcast del mensaje, excluyendo al usuario que lo envia
                server.broadcast(clientMessage, this);
                clientMessage = (Mensaje) objectInputStream.readObject();
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

    /**
     * Sends a message to the client.
     */
    public void sendMessage(String message) {
        try {
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
            objectOutpuStream.writeObject(message);
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
                Objects.equals(objectInputStream, that.objectInputStream) &&
                Objects.equals(objectOutpuStream, that.objectOutpuStream) &&
                Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, server, objectInputStream, objectOutpuStream, usuario);
    }
}