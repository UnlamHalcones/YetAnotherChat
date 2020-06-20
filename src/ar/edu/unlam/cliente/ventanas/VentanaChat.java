package ar.edu.unlam.cliente.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import ar.edu.unlam.cliente.archivos.ManejadorArchivos;
import ar.edu.unlam.entidades1.*;

public class VentanaChat extends JFrame {

	private JSplitPane splitPane;
	private JPanel izqPanel;
	private JPanel derPanel;
	private JScrollPane scrollPane;
	private JScrollPane scrollPaneUsuarios;
	private JTextPane textArea;
	private JPanel inputPanel;
	private JTextField textField;
	private JButton btnEnviar;
	private JButton btnExportar;
	// IngresoCliente cliente;
	private JComboBox<String> usuariosConectados;
	private SalaChat salaChat;
	private Usuario usuarioSeleccionado;
	private JList<Usuario> usuariosActivos;
	private DefaultListModel modUsuarios;
	private HashMap<Long, Instant> tiempoConexionUsuario;

	public VentanaChat(SalaChat salaChat) {
		this.salaChat = salaChat;
		setTitle(salaChat.getNombreSala());
		setBounds(100, 100, 500, 500);

		String[] usuarios = { "Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan", "asd", "Unknown",
				"Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan",
				"Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown" };

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(splitPane, "¿Esta seguro que desea salir de la sala?",
						"Salir de sala", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == JOptionPane.YES_OPTION) {
					// Desconectar del servidor
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} else {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				textArea.requestFocus();
			}
		});

		splitPane = new JSplitPane();

		izqPanel = new JPanel();
		derPanel = new JPanel();

		scrollPane = new JScrollPane();
		scrollPaneUsuarios = new JScrollPane();
		textArea = new JTextPane();

		inputPanel = new JPanel();
		textField = new JTextField();
		btnEnviar = new JButton("Enviar");
		btnExportar = new JButton("Exportar Chat");

		btnExportar.setEnabled(false);

		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setEditable(true);
		textArea.setForeground(Color.RED);

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO HACER EL ENVIO DE UN MENSAJE
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					String mensaje = textField.getText();
					if (usuarioSeleccionado == null) {
						Cliente.getInstance().enviarMensaje(salaChat, mensaje);
					} else {
						Cliente.getInstance().enviarMensaje(usuarioSeleccionado, salaChat, mensaje);
					}
//				agregarTextoTextAreaLocal(mensaje + "\n");
					selectAllTextoTextField(textField);
				}

			}
		});

		textField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {

				Object obj = e.getSource();
				if (obj instanceof JTextField) {
					selectAllTextoTextField((JTextField) obj);
				}

			}
		});

		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String mensaje = textField.getText();
				if (usuarioSeleccionado == null) {
					Cliente.getInstance().enviarMensaje(salaChat, mensaje);
				} else {
					Cliente.getInstance().enviarMensaje(usuarioSeleccionado, salaChat, mensaje);
				}
//				agregarTextoTextAreaLocal(mensaje + "\n");
				selectAllTextoTextField(textField);
			}
		});

		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jFileChooser = new JFileChooser();
				String userHome = System.getProperty("user.home");
				userHome = userHome + "/Downloads";

				jFileChooser.setCurrentDirectory(new java.io.File(userHome));

				jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				if (jFileChooser.showOpenDialog(btnExportar) == JFileChooser.APPROVE_OPTION) {
					ManejadorArchivos.rootPath = jFileChooser.getSelectedFile().getAbsolutePath();
					Cliente.getInstance().solicitarLog(salaChat.getId());
				}
			}
		});

		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setToolTipText("Escriba su mensaje para enviar");
		btnEnviar.setToolTipText("Click para enviar mensaje");
		btnExportar.setToolTipText("Click para exportar el Chat");

		usuariosConectados = new JComboBox<String>();
		usuariosConectados.addItem("Todos");
		salaChat.getUsuariosInSala().forEach(usuario -> {
			usuariosConectados.addItem(usuario.getUserName());
		});

		usuariosConectados.setSelectedItem("Todos");
		usuariosConectados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Actualizo a quien le quiero mandar el mensaje
				usuarioSeleccionado = null;
				String selectedItem = (String) usuariosConectados.getSelectedItem();
				if (selectedItem != null && !selectedItem.equalsIgnoreCase("Todos")) {
					Usuario usuarioByUserName = salaChat.getUsuarioByUserName(selectedItem);
					if (usuarioByUserName != null) {
						usuarioSeleccionado = usuarioByUserName;
					}
				}
			}
		});

		setPreferredSize(new Dimension(600, 500));
		getContentPane().setLayout(new GridLayout());
		getContentPane().add(splitPane);

		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(175);
		splitPane.setTopComponent(izqPanel);
		splitPane.setBottomComponent(derPanel);

		izqPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
		izqPanel.setLayout(new BorderLayout(1, 2));

		// usuariosActivos = new JList<String>(usuarios);

		mostrarUsuariosEnSala();

		usuariosActivos = new JList<Usuario>();
		modUsuarios = new DefaultListModel<Usuario>();
		tiempoConexionUsuario = new HashMap<>();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");

		tiempoConexionUsuario = salaChat.getTiempoConexionUsuario();

		salaChat.getUsuariosInSala().forEach(usuario -> {
			if (tiempoConexionUsuario.containsKey(usuario.getId())) {
				modUsuarios.addElement(usuario.getUserName() + " "
						+ formato.format(Date.from(tiempoConexionUsuario.get(usuario.getId()))));
			}
		});
		usuariosActivos.setModel(modUsuarios);

		DefaultMutableTreeNode abuelo = new DefaultMutableTreeNode("Usuarios Conectados");
		DefaultTreeModel modelo = new DefaultTreeModel(abuelo);
		JTree tree = new JTree(modelo);

		DefaultMutableTreeNode usuario1 = new DefaultMutableTreeNode("Usuario 1");
		DefaultMutableTreeNode hijoCon = new DefaultMutableTreeNode("15:00:00");
		DefaultMutableTreeNode hijoEst = new DefaultMutableTreeNode("Estado: Conectado");
		DefaultMutableTreeNode usuario2 = new DefaultMutableTreeNode("Usuario 2");
		DefaultMutableTreeNode hijoCon2 = new DefaultMutableTreeNode("16:45:20");
		DefaultMutableTreeNode hijoEst2 = new DefaultMutableTreeNode("Estado: Desconectado");

		modelo.insertNodeInto(usuario1, abuelo, 0);
		modelo.insertNodeInto(usuario2, abuelo, 1);
		modelo.insertNodeInto(hijoCon, usuario1, 0);
		modelo.insertNodeInto(hijoEst, usuario1, 1);
		modelo.insertNodeInto(hijoCon2, usuario2, 0);
		modelo.insertNodeInto(hijoEst2, usuario2, 1);

		izqPanel.add(usuariosActivos);
		// izqPanel.add(tree);

		usuariosActivos.setPreferredSize(izqPanel.getMinimumSize());

		derPanel.setLayout(new BoxLayout(derPanel, BoxLayout.Y_AXIS));
		derPanel.add(scrollPane);
		scrollPane.setViewportView(textArea);
		derPanel.add(inputPanel);

		scrollPaneUsuarios = new JScrollPane();
		scrollPaneUsuarios.setEnabled(false);
		scrollPaneUsuarios.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneUsuarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		izqPanel.add(scrollPaneUsuarios);
		scrollPaneUsuarios.setViewportView(usuariosActivos);

		inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

		inputPanel.add(textField);
		inputPanel.add(usuariosConectados);
		inputPanel.add(btnEnviar);
		inputPanel.add(btnExportar);

		setLocationRelativeTo(null);

		pack();
	}

	private void agregarMensajeTextAreaLocal(Mensaje mensaje) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
				.withZone(ZoneId.systemDefault());
		String formatedDate = formatter.format(mensaje.getInstantCreacion());

		Color messageColor = Color.BLACK;
		String stringMessage = mensaje.getUserCreador().getUserName() + "(" + formatedDate + ") : " + mensaje.getData()
				+ "\n";
		if (mensaje.getUserDestino() != null) {
			messageColor = Color.RED;
			if (mensaje.getUserCreador().equals(Cliente.getInstance().getUser())) {
				stringMessage = mensaje.getUserDestino().getUserName() + "(" + formatedDate + ") : " + mensaje.getData()
						+ "\n";
			}
		}

		appendToPane(stringMessage, mensaje.getUserDestino() != null ? Color.RED : Color.BLACK);
		btnExportar.setEnabled(true);
	}

	private void appendToPane(String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.setCharacterAttributes(aset, false);
		textArea.replaceSelection(msg);
	}

	private void selectAllTextoTextField(JTextField textField) {
		textField.requestFocus();
		textField.setSelectionStart(0);
		textField.setSelectionEnd(textField.getText().length());

	}

	public SalaChat getSalaChat() {
		return salaChat;
	}

	public void setSalaChat(SalaChat salaChat) {
		this.salaChat = salaChat;
	}

	public void actualizarMensajes(Mensaje clientMessage) {
		this.agregarMensajeTextAreaLocal(clientMessage);
	}

	public void actualizarUsuarios(Set<Usuario> usuariosInSala) {
		this.salaChat.setUsuariosInSala(usuariosInSala);

		this.mostrarUsuariosEnSala();
	}

	private void mostrarUsuariosEnSala() {
		System.out.println("Actualizo los usuarios en la sala");
		for (Usuario usuario : salaChat.getUsuariosInSala()) {
			System.out.println(usuario.getUserName() + " esta en la sala");
		}

	}
}
