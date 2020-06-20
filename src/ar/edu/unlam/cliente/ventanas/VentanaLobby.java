package ar.edu.unlam.cliente.ventanas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ar.edu.unlam.entidades1.*;

public class VentanaLobby extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuario;
	private Lobby lobby;
	private List<VentanaChat> ventanasChat;

	public VentanaLobby(Usuario usuario) {
		super();
		System.out.println("construyo");
		this.usuario = usuario;
		this.lobby = new Lobby();
		this.ventanasChat = new ArrayList<>();
		this.contentPane = new JPanel(null);
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setTitle("Lobby. Usuario: " + this.usuario.getUserName());
		this.mostrarSalas();
		this.setSize(335, 580);
		this.setLocationRelativeTo(null);
	}

	public void mostrarSalas() {
		JButton btnSala;
		JLabel etiqueta;
		JPanel panel;
		JScrollPane scrollPane;

		System.out.println("Tengo " + this.lobby.getSalas().size() + " salas.");

		etiqueta = new JLabel("Sala de chats disponibles:");
		etiqueta.setBounds(10, 10, 200, 15);

		panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.setLayout(new GridLayout(20, 1, 3, 3));

		// Agrega botones para unirse a las salas
		for (SalaChat salaChat : this.lobby.getSalas()) {
			btnSala = new JButton();
			btnSala.setPreferredSize(new Dimension(280, 30));
			btnSala.setText("Sala <" + salaChat.getNombreSala() + ">");
			btnSala.setActionCommand(String.valueOf(salaChat.getId()));
			btnSala.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					unirseAUnaSala(Long.valueOf(e.getActionCommand()));
				}
			});
			panel.add(btnSala);
		}

		// Agrega boton para crear una sala
		btnSala = new JButton();
		btnSala.setPreferredSize(new Dimension(280, 30));
		btnSala.setActionCommand("-1");
		btnSala.setText("Crear una sala");
		btnSala.setIcon(new ImageIcon(new ImageIcon("src/ar/edu/unlam/cliente/Imagen/agregar.png").getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		btnSala.setIconTextGap(1);
		btnSala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearSala();
			}
		});
		panel.add(btnSala);

		scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if (panel.countComponents() > 15) {
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		} else {
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		}
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 30, 300, 500);

		this.contentPane.removeAll();
		this.contentPane.setPreferredSize(new Dimension(335, 580));
		this.contentPane.add(scrollPane);
		this.contentPane.add(etiqueta);
		this.setContentPane(this.contentPane);
	}

	private void crearSala() {
		new VentanaCrearSala(this, this.usuario);
	}

	private void unirseAUnaSala(Long salaId) {
		if (JOptionPane.showConfirmDialog(this, "Desea unirse a la sala", "Confirmar...", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == 0) {
			Cliente.getInstance().unirseASala(salaId);
		}
	}

	public void actualizarSalas(List<SalaChat> salasChat) {
		this.lobby.setSalas(salasChat);
		this.mostrarSalas();
	}

	public void mostrarVentanaError(String errorMessage) {
		JOptionPane.showConfirmDialog(this, errorMessage, "Error", JOptionPane.CLOSED_OPTION,
				JOptionPane.ERROR_MESSAGE);
	}

	public void crearVentanaChat(SalaChat salaChat) {
		VentanaChat ventanaChat = new VentanaChat(salaChat);
		ventanaChat.setVisible(true);
		this.ventanasChat.add(ventanaChat);
	}

	public void actualizarMensajes(Mensaje clientMessage) {
		Long salaOrigenId = clientMessage.getSalaOrigenId();
		SalaChat salaById = this.lobby.getSalaById(salaOrigenId);
		VentanaChat ventanaChat = this.ventanasChat.stream().filter(ventana -> ventana.getSalaChat().equals(salaById))
				.findAny().orElse(null);
		ventanaChat.actualizarMensajes(clientMessage);
	}
}