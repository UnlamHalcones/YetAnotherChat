package ar.edu.unlam.servidor.cor;

import ar.edu.unlam.entidades.Command;
import ar.edu.unlam.entidades.CommandType;
import ar.edu.unlam.entidades.Mensaje;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

public class ProcesarMensaje implements Procesable {

    private Procesable siguiente;
    private ServidorChat server;

    public ProcesarMensaje(ServidorChat server) {
        this.server = server;
    }

    @Override
    public void establecerSiguiente(Procesable siguienteProcesable) {
        this.siguiente = siguienteProcesable;
    }

    @Override
    public void procesar(Command command, ThreadUsuario threadUsuario) {
        if(command.getCommandType().equals(CommandType.MENSAJE)) {
            Mensaje clientMessage = (Mensaje) command.getInfo();
            if(clientMessage.getUserDest() == null) {
                server.broadcast(clientMessage, threadUsuario);
            } else {
                server.sendMessageTo(clientMessage);
            }
        } else {
            siguiente.procesar(command, threadUsuario);
        }
    }
}
