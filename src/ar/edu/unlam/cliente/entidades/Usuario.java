package ar.edu.unlam.cliente.entidades;

public class Usuario {

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

}