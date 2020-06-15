package ar.edu.unlam.cliente.ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

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

	public VentanaLobby(Usuario usuario) {
		super();
		this.lobby = new Lobby();
		this.usuario = usuario;
		this.panel = new JPanel();
		this.scrollPane = new JScrollPane();
		this.etiqueta = new JLabel();
		this.lista = new JList<JButton>();
		this.panelLista = new JPanel();
		initialize();
	}

	public VentanaLobby(Lobby lobby, Usuario usuario) {
		super();
		this.lobby = lobby;
		this.usuario = usuario;
		this.panel = new JPanel();
		this.scrollPane = new JScrollPane();
		this.etiqueta = new JLabel();
		this.lista = new JList<JButton>();
		this.panelLista = new JPanel();
		initialize();
	}

	private void initialize() {
		setResizable(false);
		setTitle("Lobby");

		this.panel.setLayout(null);
		this.panel.setPreferredSize(new Dimension(300, 580));

		this.etiqueta.setText("Sala de chats disponibles:");
		this.etiqueta.setBounds(10, 10, 200, 15);

		this.lista.removeAll();
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
				crearSala();
			}
		});
		this.lista.add(btnCrearSala);

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

	private void crearSala() {
		if (JOptionPane.showConfirmDialog(this, "Desea crear una sala", "Confirmar...", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == 0) {
			VentanaCrearSala ventanaCrearSala = new VentanaCrearSala();
			ventanaCrearSala.setVisible(true);
			SalaChat nuevaSala = ventanaCrearSala.getSalaChat();
			if (!nuevaSala.getNombreSala().isEmpty()) {
				String mensaje = this.lobby.crearSala(nuevaSala.getNombreSala(), nuevaSala.getUsuariosConectados(),
						this.usuario);
				if (mensaje.isEmpty()) {
					initialize();
				} else {
					JOptionPane.showConfirmDialog(this, mensaje, "Atencion...", JOptionPane.OK_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void unirseAUnaSala(int idSala) {
		if (JOptionPane.showConfirmDialog(this, "Desea unirse a la sala", "Confirmar...", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == 0) {
			String mensaje = this.lobby.unirseASala(idSala, this.usuario);
			if (mensaje.isEmpty()) {
				new VentanaChat().setVisible(true);
			} else {
				JOptionPane.showConfirmDialog(this, mensaje, "Atencion...", JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void main(String[] args) {
		new VentanaLobby(new Usuario(99, "Mingo")).setVisible(true);
	}

}