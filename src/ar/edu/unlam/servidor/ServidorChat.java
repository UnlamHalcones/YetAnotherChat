package ar.edu.unlam.servidor;

import ar.edu.unlam.cliente.entidades.Command;
import ar.edu.unlam.cliente.entidades.Mensaje;
import ar.edu.unlam.cliente.entidades.CommandType;
import ar.edu.unlam.servidor.entidades.SalaChat;
import ar.edu.unlam.servidor.entidades.Usuario;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServidorChat {

    private int port;
    private Set<ThreadUsuario> userThreads = new HashSet<>();
    private Set<Usuario> usersInServer = new HashSet<>();
    private Set<SalaChat> salasInServer = new HashSet<>();
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
        // Busco la sala desde la que se quiere mandar el mensaje
        SalaChat sala = this.getSalaWithId(message.getSalaId());
        if(sala == null) {
            sendErrorToUser(message.getUserId(), "No existe la sala.");
            return;
        }
        Usuario userFromSala = sala.getUserFromSala(message.getUserId());
        if(userFromSala == null) {
            sendErrorToUser(message.getUserId(), "El usuario no esta en la sala.");
            return;
        }
        // Mando el mensaje a todos los hilos de los usuarios conectados a esa sala
        for (ThreadUsuario userThread : userThreads) {
            if(sala.hasUser(userThread.getUsuario()) && !userThread.equals(excludeUser)) {
                userThread.sendMessage(message);
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
     * Delivers a message from one user to another
     */
    public void sendMessageTo(Mensaje message, Integer userDest) {
        // Busco la sala desde la que se quiere mandar el mensaje
        SalaChat sala = this.getSalaWithId(message.getSalaId());
        if(sala == null) {
            sendErrorToUser(message.getUserId(), "No existe la sala.");
            return;
        }
        Usuario userFromSala = sala.getUserFromSala(message.getUserId());
        if(userFromSala == null) {
            sendErrorToUser(message.getUserId(), "El usuario no esta en la sala.");
            return;
        }
        for(ThreadUsuario userThread : userThreads) {
            if(userThread.getUsuario().equals(userFromSala)) {
                userThread.sendMessage(message);
            }
        }
    }

    private void sendErrorToUser(Integer userId, String errorMessage) {
        Mensaje mensajeParaCliente = new Mensaje(userId, null, errorMessage);
        new Command(CommandType.ERROR, mensajeParaCliente);
        userThreads.forEach(threadUsuario -> {
            if(threadUsuario.getUsuario().getUserID().equals(userId)) {
                threadUsuario.sendMessage(mensajeParaCliente);
            }
        });
    }

    private SalaChat getSalaWithId(Integer id) {
        SalaChat desiredSala = null;
        for(SalaChat sala : salasInServer) {
            if(sala.getSalaId().equals(id)) {
                desiredSala = sala;
                break;
            }
        }
        return desiredSala;
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

    /*public static void main(String[] args) {
        ServidorChat servidorChat = new ServidorChat(Integer.valueOf(args[0]));
        servidorChat.execute();
    }*/
}
