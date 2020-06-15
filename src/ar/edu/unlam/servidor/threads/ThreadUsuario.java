package ar.edu.unlam.servidor.threads;

import ar.edu.unlam.cliente.entidades.Command;
import ar.edu.unlam.cliente.entidades.CommandType;
import ar.edu.unlam.cliente.entidades.Mensaje;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.servidor.entidades.SalaChat;
import ar.edu.unlam.servidor.entidades.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            Command command = (Command) objectInputStream.readObject();

            while (!command.getCommandType().equals(CommandType.DISCONNECT)) {
                switch (command.getCommandType()) {

                    case MENSAJE:
                        Mensaje clientMessage = (Mensaje) command.getInfo();
                        if(clientMessage.getUserDest() == null) {
                            server.broadcast(clientMessage, this);
                        } else {
                            server.sendMessageTo(clientMessage, clientMessage.getUserDest());
                        }
                        break;
                    case UNIRSE_SALA:
                        break;
                    default:
                        break;
                }

                command = (Command) objectInputStream.readObject();
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
            Command mensajeCommand = new Command(CommandType.MENSAJE, message);
            objectOutpuStream.writeObject(mensajeCommand);
        } catch (IOException e) {
            System.err.println("Error mandando informacion al servidor." + e.getMessage());
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ThreadUsuario that = (ThreadUsuario) o;
        return Objects.equals(socket, that.socket) && Objects.equals(server, that.server)
                && Objects.equals(objectInputStream, that.objectInputStream)
                && Objects.equals(objectOutpuStream, that.objectOutpuStream) && Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, server, objectInputStream, objectOutpuStream, usuario);
    }
}