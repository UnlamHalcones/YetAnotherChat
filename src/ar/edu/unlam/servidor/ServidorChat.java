package ar.edu.unlam.servidor;

import ar.edu.unlam.entidades.*;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServidorChat {

    private int port;
    private Set<ThreadUsuario> userThreads = new HashSet<>();
    private ServerSocket serverSocket;
    private Set<Usuario> usuariosInServer;
    private List<SalaChat> salasInServer;

    public ServidorChat(int port) {
        this.port = port;
        this.usuariosInServer = new HashSet<>();
        this.salasInServer = new ArrayList<>();        
    }

	public void execute() {
		try {
			serverSocket = new ServerSocket(port);
			while (!serverSocket.isClosed()) {
				// Acepto conexion
				Socket socket = serverSocket.accept();

				InputStream inputStream = socket.getInputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                // Creacion del usuario que se conecta
                String userNickName = (String)objectInputStream.readObject();
                Command command;
                if(!existeUserWithNickName(userNickName)) {
                    Usuario newUser = new Usuario(Long.valueOf(usuariosInServer.size()), userNickName);
                    usuariosInServer.add(newUser);

                    command = new Command(CommandType.USER, newUser);

                    // Creacion del hilo que va a manejar la comunicacion con el usuario
                    ThreadUsuario threadUsuario = new ThreadUsuario(objectInputStream, objectOutputStream, this, newUser);
                    userThreads.add(threadUsuario);
                    threadUsuario.start();
                } else {
                    command = new Command(CommandType.ERROR, "Ya existe un usuario con ese nombre");
                }

                objectOutputStream.reset();
                objectOutputStream.writeObject(command);

            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private boolean existeUserWithNickName(String userNickName) {
        return this.usuariosInServer.stream()
                .anyMatch(usuario -> usuario.getUserName().equalsIgnoreCase(userNickName));
    }

    public synchronized void broadcast(Command command) {
        for (ThreadUsuario aUser : userThreads) {
            aUser.sendCommand(command);
        }
    }

    /*public void disconnect() {
        cortarConexionesDeUsuarios();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cortarConexionesDeUsuarios() {
        this.userThreads.forEach(threadUsuario -> {
            threadUsuario.sendMessage("DISCONNECT");
        });
        this.userThreads = new HashSet<>();
    }*/

    /**
     * When a client is disconneted, removes the associated username and ThreadUsuario
     */
    public void removeUser(Usuario user, ThreadUsuario aUser) {
        boolean isRemoved = this.usuariosInServer.remove(user);
        if(isRemoved) {
            userThreads.remove(aUser);
        }
    }

    public Command getInformacionSalas() {
        return new Command(CommandType.INFO_SALAS, this.salasInServer);
    }

    public Command crearSala(String nombreSala, Usuario usuario) {
        Command comando = null;
        Optional<SalaChat> optSalaConMismoNombre = this.salasInServer.stream()
                .filter(salaChat -> salaChat.getNombreSala().equalsIgnoreCase(nombreSala))
                .findAny();
        if(!optSalaConMismoNombre.isPresent()) {
            SalaChat nuevaSalaChat = new SalaChat(salasInServer.isEmpty() ? 1L : salasInServer.get(salasInServer.size()-1).getId() + 1L,
                    nombreSala, usuario);
            comando = this.getInformacionSalas();
            this.salasInServer.add(nuevaSalaChat);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    broadcast(getInformacionSalas());
                }
            }).start();
        } else {
            comando = generarComandoError("Ya existe una sala con este nombre.");
        }
        return comando;
    }

    public Command unirserASala(Long salaId, Usuario usuario) {
        Command comando = null;
        if(!usuario.puedeUnirseASala()) {
            comando = generarComandoError("El usuario no puede pertenecer a mas de 3 salas.");
        } else {
            SalaChat salaChat = existeSalaConId(salaId);
            if(salaChat != null) {
                if(salaChat.hasUser(usuario)) {
                    comando = generarComandoError("Ya pertence a esta sala");
                } else {
                    salaChat.addUsuario(usuario);
                    usuario.aumentarCantSalasConectadas();
                    notificarUsuariosDeSala(salaChat);
                    comando = new Command(CommandType.UNIRSE_SALA, salaChat);
                }
            } else {
                comando = generarComandoError("No existe la sala a la que desea unirse.");
            }
        }
        return comando;
    }

	public void notificarUsuariosDeSala(SalaChat salaChat) {
		Command usuariosEnSalaCommand = new Command(CommandType.USUARIOS_SALA, salaChat);

		for (Usuario user : salaChat.getUsuariosInSala()) {
			sendCommandTo(user, usuariosEnSalaCommand);
		}
	}

	public Command getUsusariosEnSala(SalaChat sala) {
		return new Command(CommandType.USUARIOS_SALA, sala.getUsuariosInSala());
	}


    public Command salirDeSala(Long salaId, Usuario usuario) {
        Command comando = null;
        SalaChat salaChat = existeSalaConId(salaId);
        if(salaChat != null) {
            if(salaChat.hasUser(usuario)) {
                salaChat.removeUsuario(usuario);
                comando = new Command(CommandType.SALIR_SALA, salaChat);
		        notificarUsuariosDeSala(salaChat);
                // Si la sala quedo vacía la elimino
                if(!salaChat.hasUsersConnected()) {
                    this.salasInServer.remove(salaChat);
                }
                new Thread(() -> {
                    this.broadcast(getInformacionSalas());
                }).start();
            } else {
                comando = generarComandoError("El usuario no pertenece a la sala.");
            }
        } else {
            comando = generarComandoError("No existe la sala de la que quiere salir.");
        }
        return comando;
    }

    public Command procesarMensaje(Mensaje clientMessage) {
        Command comando = null;
        Long salaOrigenId = clientMessage.getSalaOrigenId();
        Usuario userCreadorId = clientMessage.getUserCreador();
        Usuario userDestinoId = clientMessage.getUserDestino();
        SalaChat salaChat = existeSalaConId(salaOrigenId);

        if(salaChat != null) {
            if(clientMessage.getUserDestino() != null) {
                if(usuariosInServer.contains(userDestinoId)) {
                    if(!salaChat.hasUser(userDestinoId)) {
                        comando = generarComandoError("El usuario al que se quiere enviar no esta en la sala.");
                    } else {
                        if(usuariosInServer.contains(userCreadorId)) {
                            if(salaChat.hasUser(userCreadorId)) {
                                salaChat.agregarMensaje(clientMessage);
                                this.sendCommandTo(userDestinoId, new Command(CommandType.MENSAJE, clientMessage));
                                this.sendCommandTo(userCreadorId, new Command(CommandType.MENSAJE, clientMessage));
                            } else {
                                comando = generarComandoError("El usuario desde el se quiere enviar no esta en la sala.");
                            }
                        } else {
                            comando = generarComandoError("No existe el usuario al que se quiere enviar.");
                        }
                    }
                } else {
                    comando = generarComandoError("No existe el usuario destino.");
                }
            } else {
                salaChat.agregarMensaje(clientMessage);
                this.sendCommandToUserInSala(salaChat, new Command(CommandType.MENSAJE, clientMessage));
            }

        } else {
            comando = generarComandoError("No existe la sala de la que quiere salir.");
        }
        return comando;
    }

    private void sendCommandToUserInSala(SalaChat salaChat, Command comando) {
        for(ThreadUsuario threadUsuario : userThreads) {
            if(salaChat.hasUser(threadUsuario.getUsuario())) {
                threadUsuario.sendCommand(comando);
            }
        }
    }

    private Command generarComandoError(String mensajeError) {
        System.err.println(mensajeError);
        return new Command(CommandType.ERROR, mensajeError);
    }
    
    
    public Command exportarLog(Long salaID, Long userID) {
    	
    	SalaChat sala = existeSalaConId(salaID);
    	
    	if(sala==null)
    		return generarComandoError("No se encontro la sala");
    	    	
    	String log = salaID+"_"+sala.getNombreSala()+"\n"+"Mensajes de la sala: " + sala.getNombreSala() + "["+ salaID + "]\n";
    	
    	for (Mensaje mensaje : sala.getMensajes()) {
			
    		Date msjDate = new Date(mensaje.getInstantCreacion().toEpochMilli());
    		
    		
    		if(mensaje.getUserDestino() == null) {
    			log+=msjDate.getHours()+":"+msjDate.getMinutes()+":"+msjDate.getSeconds()+". Usuario: "+mensaje.getUserNameCreador()+". Mensaje: "+mensaje.getData()+"\n";    			
    		}
    		else if(mensaje.getUserDestinoId() != null && (mensaje.getUserCreadorId() == userID || mensaje.getUserDestinoId() == userID )) {
    			log+=msjDate.getHours()+":"+msjDate.getMinutes()+":"+msjDate.getSeconds()+". Usuario Origen: "+mensaje.getUserNameCreador()+" a Usuario Destino: "+ mensaje.getUserNameDestino() 
    			+ ". Mensaje: " + mensaje.getData()+"\n";
    		}
		}
    	log+= "\n";
    	
    	return new Command(CommandType.EXPORT_LOG,log.getBytes());
    }

    private Usuario existeUsuarioConId(Long id) {
        if(id == null) {
            return null;
        }
        Usuario user = null;
        for(Usuario usuario : this.usuariosInServer) {
            if(usuario.getId().equals(id)) {
                user = usuario;
                break;
            }
        }
        return user;
    }

    private SalaChat existeSalaConId(Long id) {
        Optional<SalaChat> salaChat = this.salasInServer.stream()
                .filter(sala -> sala.getId().equals(id))
                .findAny();
        return salaChat.orElse(null);
    }

    private void sendCommandTo(Usuario userDestino, Command command) {
        this.userThreads.forEach(threadUsuario -> {
            if(threadUsuario.hasUsuario(userDestino)) {
                threadUsuario.sendCommand(command);
            }
        });
    }

    public void enviarMensaje(Mensaje clientMessage) {

        this.userThreads.forEach(threadUsuario -> {

        });
    }
}
