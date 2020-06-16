package ar.edu.unlam.entidades;

import java.io.Serializable;
import java.util.Objects;


public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userID;
	private String userNickname;
	private Integer cantidadSalasConectadas;

	public Usuario(Integer userID, String nickname) {

		this.userID = userID;
		this.userNickname = nickname;
		this.cantidadSalasConectadas = 0;

	}

	public Integer getUserID() {
		return userID;
	}

	public String getUserNickname() {
		return userNickname;
	}

	@Override
	protected Usuario clone() throws CloneNotSupportedException {

		return new Usuario(this.userID, this.userNickname);
	}

	public Integer getCantidadSalasConectadas() {
		return cantidadSalasConectadas;
	}
	
	  @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Usuario usuario = (Usuario) o;
	        return Objects.equals(userID, usuario.userID) &&
	                Objects.equals(userNickname, usuario.userNickname) &&
	                Objects.equals(cantidadSalasConectadas, usuario.cantidadSalasConectadas);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(userID, userNickname, cantidadSalasConectadas);
	    }

}