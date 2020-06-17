package ar.edu.unlam.servidor.cor;

import ar.edu.unlam.entidades.Command;
import ar.edu.unlam.servidor.ServidorChat;
import ar.edu.unlam.servidor.threads.ThreadUsuario;

/**
 * Created by ncailotto
 */
public interface Procesable {

    public void establecerSiguiente(Procesable siguienteProcesable);

    public void procesar(Command command, ThreadUsuario threadUsuario);
}
