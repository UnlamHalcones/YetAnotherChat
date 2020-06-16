package ar.edu.unlam.cliente.ventanas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.edu.unlam.servidor.entidades.*;

public class VentanaChat extends JFrame {

	private JSplitPane splitPane;
	private JPanel izqPanel;
	private JPanel derPanel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel inputPanel;
	private JTextField textField;
	private JButton btnEnviar;
	private JButton btnExportar;
	//IngresoCliente cliente;
	private JComboBox<String> usuariosConectados;
	Date fecha;

	// public VentanaChat(String usuario) {
	public VentanaChat() {

		setTitle("Sala PONER NOMBRE!!");
		setBounds(100, 100, 500, 500);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				textArea.requestFocus();
			}
		});

		splitPane = new JSplitPane();

		izqPanel = new JPanel();
		derPanel = new JPanel();

		scrollPane = new JScrollPane();
		textArea = new JTextArea();

		inputPanel = new JPanel();
		textField = new JTextField();
		btnEnviar = new JButton("Enviar");
		btnExportar = new JButton("Exportar Chat");

		btnExportar.setEnabled(false);

		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setEditable(false);
		textArea.setForeground(Color.RED);

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					agregarTextoTextAreaLocal(textField.getText() + "\n");
					selectAllTextoTextField(textField);
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

		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				agregarTextoTextAreaLocal(textField.getText() + "\n");
				selectAllTextoTextField(textField);
			}
		});

		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setToolTipText("Escriba su mensaje para enviar");
		btnEnviar.setToolTipText("Click para enviar mensaje");
		btnExportar.setToolTipText("Click para exportar el Chat");

		usuariosConectados = new JComboBox<String>();
		// for(Usuario us : Usuarios.values()) usuariosConectados.addItem(us.name());
		usuariosConectados.addItem("Todos");
		usuariosConectados.addItem("Juan");
		usuariosConectados.addItem("Pepe");
		usuariosConectados.addItem("Pablo");
		usuariosConectados.addItem("José");
		usuariosConectados.addItem("Pacho");

		usuariosConectados.setSelectedItem("Todos");
		usuariosConectados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		setPreferredSize(new Dimension(600, 500));
		getContentPane().setLayout(new GridLayout());
		getContentPane().add(splitPane);

		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(175);
		splitPane.setTopComponent(izqPanel);
		splitPane.setBottomComponent(derPanel);

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

		izqPanel.add(tree);

		derPanel.setLayout(new BoxLayout(derPanel, BoxLayout.Y_AXIS));
		derPanel.add(scrollPane);
		scrollPane.setViewportView(textArea);
		derPanel.add(inputPanel);

		inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

		inputPanel.add(textField);
		inputPanel.add(usuariosConectados);
		inputPanel.add(btnEnviar);
		inputPanel.add(btnExportar);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		pack();
	}

	private void agregarTextoTextAreaLocal(String texto) {
		fecha = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		textArea.append("Mi Usuario" + "(" + hourdateFormat.format(fecha) + ") ");
		textArea.append(usuariosConectados.getSelectedItem().toString() + ": \n");
		textArea.append(texto);
		textArea.setCaretPosition(textArea.getText().length());
		btnExportar.setEnabled(true);
	}

	private void selectAllTextoTextField(JTextField textField) {
		textField.requestFocus();
		textField.setSelectionStart(0);
		textField.setSelectionEnd(textField.getText().length());

	}

}
