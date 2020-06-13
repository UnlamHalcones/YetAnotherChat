package ar.edu.unlam.servidor.entidades;

import java.util.ArrayList;

public class SalaChat {

	protected Integer salaId;
	protected String nombreSala;
	private Integer userMax;
	private Integer cantUserConectados;

	private ArrayList<Usuario> usuariosConectados;

	public SalaChat(Integer salaID, String nombreSala, Integer usrMax) {

		this.salaId = salaID;
		this.userMax = usrMax;
		this.nombreSala = nombreSala;
		this.cantUserConectados = 0;
		this.usuariosConectados = new ArrayList<Usuario>();

	}

	public void agregarUsuarioSala(Usuario usr) {
		
		if(cantUserConectados==userMax) {
			System.out.println("Ventana de error ...");
			return;
		}
		
		this.usuariosConectados.add(usr);
		incrementarUsuariosConectados();
		
	}
	
	

	public Integer getSalaId() {
		return salaId;
	}

	public String getNombreSala() {
		return nombreSala;
	}
	
	public void incrementarUsuariosConectados() {
		this.cantUserConectados++;
	}
	
	public void decrementarUsuariosConectados() {
		this.cantUserConectados--;
	}

}
