package ar.edu.unlam.cliente.ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

import ar.edu.unlam.entidades1.*;

public class VentanaLobby extends JFrame {

	private static final long serialVersionUID = 716619560190543188L;
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panelLista;
	private JScrollPane scrollPane;
	private JLabel etiqueta;
	private JList<JButton> lista;
	private Usuario usuario;
	private Lobby lobby;
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
		this.contentPane = new JPanel(null);
		initialize();
	}


	private void initialize () {
		this.setResizable(false);
		this.setTitle("Lobby. Usuario: " + this.usuario.getUserName());
		JMenuBar jMenuBar = new JMenuBar();
		JMenu loginMenu = new JMenu("Login");
		JMenuItem loginItem = new JMenuItem("Login");
		loginItem.setToolTipText("Login server");

		loginItem.addActionListener(e -> {
			new VentanaIngresoCliente();
			dispose();
		});

		loginMenu.add(loginItem);
		jMenuBar.add(loginMenu);

		setJMenuBar(jMenuBar);
		this.mostrarSalas();
		this.setSize(335, 580);
		this.setLocationRelativeTo(null);
	}

	public void mostrarSalas () {
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
		btnSala.setEnabled(Cliente.getInstance().isLogged());
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

	private void crearSala () {
		new VentanaCrearSala(this, this.usuario);
	}

	private void unirseAUnaSala (Long salaId){
		if (JOptionPane.showConfirmDialog(this, "Desea unirse a la sala", "Confirmar...", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == 0) {
			Cliente.getInstance().unirseASala(salaId);
		}
	}

	public void actualizarSalas (List < SalaChat > salasChat) {
		this.lobby.setSalas(salasChat);
		this.mostrarSalas();
	}

	public void mostrarVentanaError (String errorMessage){
		JOptionPane.showConfirmDialog(this, errorMessage, "Error", JOptionPane.CLOSED_OPTION,
				JOptionPane.ERROR_MESSAGE);
	}

	public void crearVentanaChat (SalaChat salaChat){
		VentanaChat ventanaChat = new VentanaChat(salaChat);

		ventanaChat.setVisible(true);
		this.ventanasChat.add(ventanaChat);

		System.out.println("Tengo " + this.ventanasChat.size() + " ventanasChat en crearVentanaChat");
	}

	public void actualizarMensajes (Mensaje clientMessage){
		Long salaOrigenId = clientMessage.getSalaOrigenId();
		SalaChat salaById = this.lobby.getSalaById(salaOrigenId);
		List<VentanaChat> ventanasDeChatAActualizar = this.ventanasChat.stream()
				.filter(ventana -> ventana.getSalaChat().getId().equals(salaById.getId()))
				.collect(Collectors.toList());
		ventanasDeChatAActualizar.forEach(ventanaChat -> ventanaChat.actualizarMensajes(clientMessage));
	}


	public VentanaChat getVentanaPorSalaChat (SalaChat salaChat){
		System.out.println("Tengo " + this.ventanasChat.size() + " ventanasChat en getVentanaPorSalaChat");

		return this.ventanasChat.stream().filter(ventana -> ventana.getSalaChat().getId().equals(salaChat.getId()))
				.findAny().orElse(null);
	}

	public void removerVentanaChat(Long salaId) {
		VentanaChat ventanaARemover = this.ventanasChat.stream()
				.filter(ventanaChat -> ventanaChat.getSalaChat().getId().equals(salaId))
				.findAny()
				.orElse(null);
		this.ventanasChat.remove(ventanaARemover);
	}
}

