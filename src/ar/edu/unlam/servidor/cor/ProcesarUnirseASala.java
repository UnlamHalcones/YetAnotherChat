package ar.edu.unlam.servidor.cor;

import ar.edu.unlam.entidades.Command;
import ar.edu.unlam.entidades.CommandType;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

/**
 * Created by ncailotto
 */
public class ProcesarUnirseASala implements Procesable {

    private Procesable siguiente;
    private ServidorChat server;

    public ProcesarUnirseASala(ServidorChat server) {
        this.server = server;
    }

    @Override
    public void establecerSiguiente(Procesable siguienteProcesable) {
        this.siguiente = siguienteProcesable;
    }

    @Override
    public void procesar(Command command, ThreadUsuario threadUsuario) {
        if(command.getCommandType().equals(CommandType.UNIRSE_SALA)) {
            Integer unirseSalaId = (Integer)command.getInfo();
            server.getLobby().unirseASala(unirseSalaId, threadUsuario.getUsuario());
        } else {
            siguiente.procesar(command, threadUsuario);
        }
    }
}
