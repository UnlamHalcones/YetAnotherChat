package ar.edu.unlam.cliente.ventanas;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaCrearSala extends JFrame{

	private JPanel salaPanel;
	private JButton boton;
	
	public VentanaCrearSala() {
		
		setTitle("Crear Sala de Chat");
		setResizable(false);
		setBounds(100,100,500,300);
		
		ContenedorBotones panelBotones= new ContenedorBotones();
		ContenedorDatosSala panelDatosSala= new ContenedorDatosSala(); 
		ContenedorDescripcion panelDescripcion= new ContenedorDescripcion();
		//Panel datos sala
		/*
		salaPanel = new JPanel();
		salaPanel.setLayout(null);
		
		//Panel botones
		*/
		add(panelBotones, BorderLayout.SOUTH);
		add(panelDatosSala, BorderLayout.CENTER);
		add(panelDescripcion, BorderLayout.NORTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		
	}
	
	public class ContenedorBotones extends JPanel{
		
		public ContenedorBotones() {
			
			setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JButton botonCrearSala= new JButton("Crear Sala");
			JButton botonCancelar= new JButton("Cancelar");
			
			add(botonCrearSala);
			add(botonCancelar);
		}
		
	}
	
	public class ContenedorDatosSala extends JPanel{
		
		public ContenedorDatosSala() {
			
			setLayout(new GridLayout(2, 2, 10, 60));
			
			JLabel etiquetaSala= new JLabel("Nombre de la Sala");
			JTextField ingresoSala= new JTextField();
			
			JLabel etiquetaSalaCant= new JLabel("Cantidad de Participantes");
			JTextField ingresoSalaCant= new JTextField();
			
			add(etiquetaSala);
			add(ingresoSala);
			
			add(etiquetaSalaCant);
			add(ingresoSalaCant);
		}
		
	}
	
	public class ContenedorDescripcion extends JPanel{
		
		public ContenedorDescripcion() {
			
			setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			JLabel etiquetaDescripcion= new JLabel("Ingrese el nombre de la sala a crear, como tambien la cantidad de participantes");
			
			add(etiquetaDescripcion);
			
		}
	}

	public static void main(String[] args) {
		
		new VentanaCrearSala().setVisible(true);
	}
}

