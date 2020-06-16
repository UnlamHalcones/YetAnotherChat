package ar.edu.unlam.cliente.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import ar.edu.unlam.entidades.*;
public class VentanaChat extends JFrame{
	
	private Chat chat;
	private JPanel contentPane;
	private JPanel panelMensajeAEnviar;
	private JTextField textField;
	private JButton btnEnviar;
	private JScrollPane scrollPane;
	private JTextArea textArea;

	/**
	 * Create the frame.
	 */
	public VentanaChat() {
		
		this.chat = new Chat("Juan", "Roberta");
		
		setTitle("Chat entre "+chat.get_usr1()+" y "+chat.get_usr2());
		setResizable(false);
		setBounds(100, 100, 450, 500);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				textField.requestFocus();
			}
		});

		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(1, 1));
		setContentPane(contentPane);

		panelMensajeAEnviar = new JPanel();
		contentPane.add(panelMensajeAEnviar, BorderLayout.SOUTH);
		panelMensajeAEnviar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					agregarTextoTextAreaLocal(textField.getText() + "\n");
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

		textField.setToolTipText("Escriba su mensaje para enviar");
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(30);
		panelMensajeAEnviar.add(textField);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				agregarTextoTextAreaLocal(textField.getText() + "\n");
				selectAllTextoTextField(textField);
			}
		});

		btnEnviar.setToolTipText("Click para enviar mensaje");
		panelMensajeAEnviar.add(btnEnviar);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

	private void agregarTextoTextAreaLocal(String texto) {
		textArea.append(chat.get_usr1()+": "+texto); // ingresar quien escribe el texto
		textArea.setCaretPosition(textArea.getText().length());
	}

	private void selectAllTextoTextField(JTextField textField) {
		textField.requestFocus();
		textField.setSelectionStart(0);
		textField.setSelectionEnd(textField.getText().length());
		
	}
}
