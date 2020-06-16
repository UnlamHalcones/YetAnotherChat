package ar.edu.unlam.entidades;

import java.io.Serializable;

public class Command implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -472654729849128580L;
	private CommandType commandType;
    private Object info;

    public Command(CommandType commandType, Object info) {
        this.commandType = commandType;
        this.info = info;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
