package ar.edu.unlam.entidades;

import java.util.ArrayList;

public class SalaChat {
	
	protected Integer salaId;
	protected String nombreSala;
	private Integer userMax;
	private Integer cantUserConectados;
	
	private ArrayList<Integer> idUsuariosConectados;
	
	
	public SalaChat (Integer salaID, String nombreSala, Integer usrMax) {
		
		this.salaId=salaID;
		this.userMax=usrMax;
		this.nombreSala=nombreSala;
		this.cantUserConectados=0;
		
		this.idUsuariosConectados= new ArrayList<Integer>();
		
		
	}
	

	public Integer getSalaId() {
		return salaId;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	
}
