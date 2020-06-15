package ar.edu.unlam.cliente.ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
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
	private JButton btnEnviar;
	private JScrollPane scrollPane;
	private JScrollPane scrollUsuarios;
	private JTextArea textArea;
	private JLabel etiqueta;


	/**
	 * Create the frame.
	 */
	public VentanaChat() {
		
		setTitle("Ventana de Chat");
		setResizable(true);
		setBounds(100, 100, 450, 300);
		
		String[] usuarios = {"Jorge","Juan","Unknown","Jorge","Juan","Unknown","Jorge","Juan","asd","Unknown","Jorge","Juan","Unknown","Jorge","Juan","Unknown","Jorge","Juan","Unknown","Jorge","Juan","Unknown","Jorge","Juan","Unknown","Jorge","Juan","Unknown","Jorge","Juan","Unknown"};
		
		usuariosActivos = new JList<String>(usuarios);


		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				textField.requestFocus();
			}
		});
		//Panel general
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(1, 1));
		setContentPane(contentPane);

		//Panel envio de mensajes
		panelMensajeAEnviar = new JPanel();
		contentPane.add(panelMensajeAEnviar, BorderLayout.SOUTH);
		panelMensajeAEnviar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		
		
		//Panel con lista de usuarios conectados
		etiqueta = new JLabel("Usuarios:");
		usuariosConectados = new JPanel();
		contentPane.add(usuariosConectados, BorderLayout.EAST);
		usuariosConectados.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
		usuariosConectados.setBorder(null);
		usuariosConectados.setLayout(new BorderLayout(1, 1));

		usuariosConectados.add(etiqueta, BorderLayout.NORTH);
		usuariosConectados.add(usuariosActivos,  BorderLayout.SOUTH);
		
		
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
	               //abrir ventana chat
					//JOptionPane.showInputDialog("Aca se abre una ventana de chat");
	            }
			}
		});
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				if (!textField.getText().isEmpty() && arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 9));
					agregarTextoTextArea(
							"	Nickname "/* getUserNickName() */ + fecha.format(LocalDateTime.now()) + "\n");
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
					agregarTextoTextArea(textField.getText() + "\n");
					textField.setText("");
					new VentanaChat();
					// selectAllTextoTextField(textField);
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
		
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(30);
		panelMensajeAEnviar.add(textField);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				if (!textField.getText().isEmpty()) {
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 9));
					agregarTextoTextArea(
							"	Nickname "/* getUserNickName() */ + fecha.format(LocalDateTime.now()) + "\n");
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
					agregarTextoTextArea(textField.getText() + "\n");
					textField.setText("");
					// agregarTextoTextArea(textField.getText() + "\n");
					// selectAllTextoTextField(textField);
				}
			}
		});

		btnEnviar.setToolTipText("Click para enviar mensaje");
		panelMensajeAEnviar.add(btnEnviar);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		
		scrollUsuarios = new JScrollPane();
		scrollUsuarios.setEnabled(false);
		scrollUsuarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		usuariosConectados.add(scrollUsuarios, BorderLayout.CENTER);


		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		scrollUsuarios.setViewportView(usuariosActivos);
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaChat frame = new VentanaChat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
