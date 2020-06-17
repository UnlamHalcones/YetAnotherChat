package ar.edu.unlam.servidor.cor;

import ar.edu.unlam.entidades.Command;
import ar.edu.unlam.entidades.CommandType;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

/**
 * Created by ncailotto
 */
public class ProcesarSalirSala implements Procesable {

    private Procesable siguiente;
    private ServidorChat server;

    @Override
    public void establecerSiguiente(Procesable siguienteProcesable, ServidorChat server) {
        this.siguiente = siguienteProcesable;
        this.server = server;
    }

    @Override
    public void procesar(Command command, ThreadUsuario threadUsuario) {
        if(command.getCommandType().equals(CommandType.SALIR_SALA)) {
            Integer salirSalaId = (Integer)command.getInfo();
            server.getLobby().salirDeSala(salirSalaId, threadUsuario.getUsuario());
        } else {
            siguiente.procesar(command, threadUsuario);
        }
    }
}
