package ar.edu.unlam.cliente.ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import ar.edu.unlam.entidades1.*;

public class VentanaLobby extends JFrame {

	private static final long serialVersionUID = 716619560190543188L;
	private JPanel panel;
	private JPanel panelLista;
	private JScrollPane scrollPane;
	private JLabel etiqueta;
	private JList<JButton> lista;
	private Usuario usuario;
	public Lobby lobby;
	private List<VentanaChat> ventanasChat;

	public VentanaLobby(Usuario usuario) {
		
		super();
		System.out.println("construyo");
		this.usuario = usuario;
		this.panel = new JPanel();
		this.scrollPane = new JScrollPane();
		this.etiqueta = new JLabel();
		this.lista = new JList<JButton>();
		this.panelLista = new JPanel();
		this.lobby = new Lobby();
		this.ventanasChat = new ArrayList<>();
		initialize();
	}

	public List<VentanaChat> getVentanasChat() {
		return ventanasChat;
	}

	private void initialize() {
		setResizable(false);
		setTitle("Lobby");

		this.panel.setLayout(null);
		this.panel.setPreferredSize(new Dimension(300, 580));

		this.etiqueta.setText("Sala de chats disponibles:");
		this.etiqueta.setBounds(10, 10, 200, 15);

//		this.lista.removeAll();
		this.lista.setBounds(10, 30, 300, 500);
		this.lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.lista.setLayoutOrientation(JList.VERTICAL);

		mostrarSalas();

		System.out.println("AGregado el boton de crear sala");
		this.panelLista.setLayout(null);
		this.panelLista.setPreferredSize(new Dimension(300, 580));
		this.panelLista.setBounds(10, 30, 300, 500);

		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setViewportView(this.lista);
		this.panelLista.add(this.scrollPane, BorderLayout.CENTER);

		this.panel.add(this.etiqueta);
		this.panel.add(this.lista);
		this.panel.add(this.panelLista);
		this.add(this.panel);

		this.setSize(335, 580);
		this.setLocationRelativeTo(null);
	}

	public void mostrarSalas() {
		int y = 10;

		System.out.println("Tengo " + this.lobby.getSalas().size() + " salas.");
		lista.removeAll();
		for(SalaChat salaChat : this.lobby.getSalas()) {
			System.out.println("pasando por la sala " + salaChat.getId());

			JButton btnSala = new JButton();
			btnSala.setText("Sala <" + salaChat.getNombreSala() + ">");
			btnSala.setActionCommand(String.valueOf(salaChat.getId()));
			btnSala.setHorizontalAlignment(SwingConstants.CENTER);
			btnSala.setVerticalAlignment(SwingConstants.CENTER);
			btnSala.setHorizontalTextPosition(SwingConstants.RIGHT);
			btnSala.setVerticalTextPosition(SwingConstants.CENTER);
			btnSala.setBounds(10, y, 280, 30);
			btnSala.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					unirseAUnaSala(Long.valueOf(e.getActionCommand()));
				}
			});
			lista.add(btnSala);
			y += 35;
		}
		JButton btnCrearSala = new JButton();
		btnCrearSala.setActionCommand("-1");
		btnCrearSala.setText("Crear una sala");
		btnCrearSala.setIcon(new ImageIcon(new ImageIcon("src/ar/edu/unlam/cliente/Imagen/agregar.png").getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		btnCrearSala.setIconTextGap(1);
		btnCrearSala.setHorizontalAlignment(SwingConstants.CENTER);
		btnCrearSala.setVerticalAlignment(SwingConstants.CENTER);
		btnCrearSala.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnCrearSala.setVerticalTextPosition(SwingConstants.CENTER);
		btnCrearSala.setBounds(10, y, 280, 30);
		btnCrearSala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearSala();
			}
		});
		lista.add(btnCrearSala);
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
		VentanaChat ventanaChat = this.ventanasChat.stream()
				.filter(ventana -> ventana.getSalaChat().equals(salaById))
				.findAny()
				.orElse(null);
		ventanaChat.actualizarMensajes(clientMessage);
	}
}