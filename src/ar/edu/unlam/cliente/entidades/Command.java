package ar.edu.unlam.cliente.entidades;

import java.io.Serializable;

public class Command<T> implements Serializable {

    private CommandType commandType;
    private T info;

    public Command(CommandType commandType, T info) {
        this.commandType = commandType;
        this.info = info;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
