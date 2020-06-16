package ar.edu.unlam.servidor;

import ar.edu.unlam.entidades.Command;
import ar.edu.unlam.entidades.CommandType;
import ar.edu.unlam.entidades.Mensaje;
import ar.edu.unlam.entidades.Lobby;
import ar.edu.unlam.entidades.Usuario;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServidorChat {
    private int port;
    private Set<ThreadUsuario> userThreads = new HashSet<>();
    private Set<Usuario> usersInServer = new HashSet<>();
    private ServerSocket serverSocket;
    public Lobby lobby;

    
    public ServidorChat(int port) {
        this.port = port;
        this.lobby = new Lobby();
    }

	public void execute() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Chat Server is listening on port " + port);
            while (!serverSocket.isClosed()) {
                // Acepto conexion
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                // Creacion del usuario que se conecta
                String userNickName = (String)objectInputStream.readObject();
                Usuario usuario = new Usuario(usersInServer.size(), userNickName);
                Command usuarioCommand = new Command(CommandType.USER, usuario);
                usersInServer.add(usuario);
                objectOutputStream.writeObject(usuarioCommand);
                System.out.println("User: " + usuario.getUserNickname() + " connected");

                // Creacion del hilo que va a manejar la comunicacion con el usuario
                ThreadUsuario threadUsuario = new ThreadUsuario(objectInputStream, objectOutputStream, this, usuario);
                userThreads.add(threadUsuario);
                threadUsuario.start();
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private Usuario addUserToServer(Socket socket) {
        Usuario usuario = null;
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            String userNickName = (String)objectInputStream.readObject();
            usuario = new Usuario(usersInServer.size(), userNickName);
            usersInServer.add(usuario);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuario;
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
     * Delivers a message from one user to others (broadcasting)
     */
    public synchronized void broadcast(Mensaje message, ThreadUsuario excludeUser) {
        for (ThreadUsuario aUser : userThreads) {
            if (!aUser.equals(excludeUser)) {
                aUser.sendMessage(message);
            }
        }
    }
    
    public synchronized void broadcast(Command command, ThreadUsuario excludeUser) {
        for (ThreadUsuario aUser : userThreads) {
            if (!aUser.equals(excludeUser)) {
                aUser.sendCommand(command);
            }
        }
    }

    /**
     * When a client is disconneted, removes the associated username and ThreadUsuario
     */
    public void removeUser(Usuario user, ThreadUsuario aUser) {
        boolean removed = usersInServer.remove(user);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + user + " quitted");
        }
    }

    public void disconnect() {
        cortarConexionesDeUsuarios();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cortarConexionesDeUsuarios() {
        this.usersInServer = new HashSet<>();
        this.userThreads.forEach(threadUsuario -> {
            threadUsuario.sendMessage("DISCONNECT");
        });
        this.userThreads = new HashSet<>();
    }

    public Lobby getLobby() {
		return lobby;
	}
    /*public static void main(String[] args) {
        ServidorChat servidorChat = new ServidorChat(Integer.valueOf(args[0]));
        servidorChat.execute();
    }*/
}
