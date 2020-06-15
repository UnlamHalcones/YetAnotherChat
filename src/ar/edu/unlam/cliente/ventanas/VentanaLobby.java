package ar.edu.unlam.cliente.ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import ar.edu.unlam.cliente.entidades.Lobby;
import ar.edu.unlam.cliente.entidades.SalaChat;
import ar.edu.unlam.cliente.entidades.Usuario;

public class VentanaLobby extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel panelLista;
	private JScrollPane scrollPane;
	private JLabel etiqueta;
	private JList<JButton> lista;
	private Usuario usuario;
	private Lobby lobby;

	public VentanaLobby() {
		super();
		this.lobby = new Lobby();
		initialize();
	}

	public VentanaLobby(Usuario usuario) {
		super();
		this.lobby = new Lobby();
		this.usuario = usuario;
		initialize();
	}

	public VentanaLobby(Lobby lobby, Usuario usuario) {
		super();
		this.lobby = lobby;
		this.usuario = usuario;
		initialize();
	}

	private void initialize() {
		setResizable(false);
		setTitle("Lobby");

		this.panel = new JPanel();
		this.panel.setLayout(null);
		this.panel.setPreferredSize(new Dimension(300, 580));

		this.etiqueta = new JLabel();
		this.etiqueta.setText("Sala de chats disponibles:");
		this.etiqueta.setBounds(10, 10, 200, 15);

		this.lista = new JList<JButton>();
		this.lista.setBounds(10, 30, 300, 500);
		this.lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.lista.setLayoutOrientation(JList.VERTICAL);

		int y = 10;

		for (Entry<Integer, SalaChat> entry : this.lobby.getSalas().entrySet()) {
			SalaChat sala = entry.getValue();
			JButton btnSala = new JButton();
			btnSala.setActionCommand(entry.getKey().toString());
			btnSala.setText("Sala <" + sala.getNombreSala() + "> [" + sala.getFechaCreacion().getFecha() + " "
					+ sala.getFechaCreacion().getHora() + "]");
			btnSala.setHorizontalAlignment(SwingConstants.CENTER);
			btnSala.setVerticalAlignment(SwingConstants.CENTER);
			btnSala.setHorizontalTextPosition(SwingConstants.RIGHT);
			btnSala.setVerticalTextPosition(SwingConstants.CENTER);
			btnSala.setBounds(10, y, 280, 30);
			btnSala.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					unirseAUnaSala(Integer.parseInt(e.getActionCommand().toString()));
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
				int idUsuario = Integer.parseInt(e.getActionCommand().toString());
				VentanaCrearSala otraSala = new VentanaCrearSala();
				otraSala.setVisible(true);
				crearSala(idUsuario, otraSala);
			}
		});
		this.lista.add(btnCrearSala);

		this.panelLista = new JPanel();
		this.panelLista.setLayout(null);
		this.panelLista.setPreferredSize(new Dimension(300, 580));
		this.panelLista.setBounds(10, 30, 300, 500);

		this.scrollPane = new JScrollPane();
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

	private void crearSala(int idUsuario, VentanaCrearSala otraSala) {
		// Aca va el codigo para crear la sala
	}

	private void unirseAUnaSala(int idSala) {
		if (this.lobby.unirseASala(idSala, this.usuario).isEmpty()) {
			new VentanaChat().setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		new VentanaLobby().setVisible(true);
	}

}