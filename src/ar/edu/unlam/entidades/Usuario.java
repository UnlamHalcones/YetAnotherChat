package ar.edu.unlam.entidades;

public class Usuario {

	private Integer userID;
	private String userNickname;
	
	public Usuario(Integer userID ,String nickname) {
		
		this.userID=userID;
		this.userNickname=nickname;
		
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
	
}
