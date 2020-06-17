package ar.edu.unlam.servidor.cor;

import ar.edu.unlam.entidades.Command;
import ar.edu.unlam.entidades.CommandType;
import ar.edu.unlam.entidades.SalaChat;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

/**
 * Created by ncailotto
 */
public class ProcesarCrearSala implements Procesable {

    private Procesable siguiente;
    private ServidorChat server;

    public ProcesarCrearSala(ServidorChat server) {
        this.server = server;
    }

    @Override
    public void establecerSiguiente(Procesable siguienteProcesable) {
        this.siguiente = siguienteProcesable;
    }

    @Override
    public void procesar(Command command, ThreadUsuario threadUsuario) {
        if(command.getCommandType().equals(CommandType.CREAR_SALA)) {
            SalaChat salaChat = (SalaChat) command.getInfo();
            String crearSalaResponse = server.lobby.crearSala(salaChat);

            if (crearSalaResponse.isEmpty()) {
//                threadUsuario.responderSalas();
            }
            else {
                Command errorCommand = new Command(CommandType.ERROR, crearSalaResponse);
//                sendCommand(errorCommand);
            }
        } else {
            siguiente.procesar(command, threadUsuario);
        }
    }
}
