package ar.edu.unlam.servidor.cor;

import ar.edu.unlam.entidades.Command;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

/**
 * Created by ncailotto
 */
public class Default implements Procesable {

    private Procesable siguiente;
    private ServidorChat server;

    public Default(ServidorChat server) {
        this.server = server;
    }

    @Override
    public void establecerSiguiente(Procesable siguienteProcesable) {
        this.siguiente = siguienteProcesable;
    }

    @Override
    public void procesar(Command command, ThreadUsuario threadUsuario) {
        throw new RuntimeException("\"" + command.getCommandType() + "\" no es un tipo de operación válida\n");
    }
}
