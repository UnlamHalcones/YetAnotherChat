package ar.edu.unlam.cliente.ventanas;

import java.awt.BorderLayout;
//import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VentanaChat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6422904877311999241L;
	private JPanel contentPane;
	private JPanel panelMensajeAEnviar;
	private JPanel usuariosConectados;

	private JList<String> usuariosActivos;

	private JTextField textField;
	private JButton btnDescarga;
	private JButton btnEnviar;
	private JScrollPane scrollPane;
	private JScrollPane scrollUsuarios;
	private JTextArea textArea;
	private JLabel etiqueta;

	/**
	 * Create the frame.
	 */
	/**
	 * 
	 */
	public VentanaChat() {

		setTitle("Ventana de Chat");
		setResizable(true);
		setBounds(100, 100, 450, 300);
		GridBagConstraints gbc = new GridBagConstraints();

		String[] usuarios = { "Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan", "asd", "Unknown",
				"Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan",
				"Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown", "Jorge", "Juan", "Unknown" };

		usuariosActivos = new JList<String>(usuarios);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				textField.requestFocus();
			}
		});
		// Panel general
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(1, 1));
		setContentPane(contentPane);

		// Panel envio de mensajes
		panelMensajeAEnviar = new JPanel();
		contentPane.add(panelMensajeAEnviar, BorderLayout.SOUTH);
		panelMensajeAEnviar.setLayout(new GridBagLayout());
		// panelMensajeAEnviar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

		// Panel con lista de usuarios conectados
		etiqueta = new JLabel("Usuarios:");
		usuariosConectados = new JPanel();
		contentPane.add(usuariosConectados, BorderLayout.EAST);
		usuariosConectados.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		usuariosConectados.setBorder(null);
		usuariosConectados.setLayout(new BorderLayout(1, 10));

		usuariosConectados.add(etiqueta, BorderLayout.NORTH);
		usuariosConectados.add(usuariosActivos, BorderLayout.SOUTH);

		usuariosActivos.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() == 2) {
					// abrir ventana chat
					
					//JOptionPane.showInputDialog("Aca se abre una ventana de chat");
				}
			}
		});

		textField = new JTextField(30);
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				if (!textField.getText().isEmpty() && arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 9));
					agregarTextoTextArea(
							"  @Nickname " /* getUserNickName() */ + fecha.format(LocalDateTime.now()) + "\n");
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
					agregarTextoTextArea(textField.getText() + "\n");
					textField.setText("");
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
		textField.setToolTipText("Escriba su mensaje para enviar");

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 10.0;
		gbc.weighty = 10.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelMensajeAEnviar.add(textField, gbc);

		btnEnviar = new JButton("Enviar");

		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				if (!textField.getText().isEmpty()) {
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 9));
					agregarTextoTextArea(
							"  @Nickname " /* getUserNickName() */ + fecha.format(LocalDateTime.now()) + "\n");
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
					agregarTextoTextArea(textField.getText() + "\n");
					textField.setText("");
				}
			}
		});

		btnEnviar.setToolTipText("Click para enviar mensaje");

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.NONE;
		panelMensajeAEnviar.add(btnEnviar, gbc);

		// panelMensajeAEnviar.add(btnEnviar);

		btnDescarga = new JButton("Descarga");

		btnDescarga.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(textArea.getText());
			}
		});

		btnDescarga.setToolTipText("Click para descargar la conversación");

		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.NONE;
		panelMensajeAEnviar.add(btnDescarga, gbc);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane);

		scrollUsuarios = new JScrollPane();
		scrollUsuarios.setEnabled(false);
		scrollUsuarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		usuariosConectados.add(scrollUsuarios);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		scrollUsuarios.setViewportView(usuariosActivos);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

	}

	private void agregarTextoTextArea(String texto) {
		textArea.append(texto);
		textArea.setCaretPosition(textArea.getText().length());
	}

	private void selectAllTextoTextField(JTextField textField) {
		textField.requestFocus();
		textField.setSelectionStart(0);
		textField.setSelectionEnd(textField.getText().length());
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaChat().setVisible(true);
		/*
		 * EventQueue.invokeLater(new Runnable() { public void run() { try { VentanaChat
		 * frame = new VentanaChat(); frame.setVisible(true); } catch (Exception e) {
		 * e.printStackTrace(); } } });
		 */
	}

}