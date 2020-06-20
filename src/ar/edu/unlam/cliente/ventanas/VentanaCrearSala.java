package ar.edu.unlam.cliente.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ar.edu.unlam.entidades.Cliente;
import ar.edu.unlam.entidades.SalaChat;
import ar.edu.unlam.entidades.Usuario;

public class VentanaCrearSala extends JDialog {

	private static final long serialVersionUID = -2013488546574761682L;
	private JTextField nombreDeLaSala;
	private JTextField cantidadParticipantes;
	private Usuario usuario;
	private Cliente cliente;
	private boolean actualiza;

	public VentanaCrearSala() {
		initialize();
	}

	public VentanaCrearSala(JFrame padre, Usuario usuario) {
		super(padre, "Crear sala...", true);
		this.usuario = usuario;
		this.cliente = Cliente.getInstance();
		this.actualiza = false;
		initialize();
	}

	private void initialize() {
		this.setBounds(100, 100, 365, 164);
		this.getContentPane().setLayout(null);
		this.setResizable(false);

		JLabel lblNombreDeLaSala = new JLabel("Nombre de la sala:");
		lblNombreDeLaSala.setBounds(15, 24, 150, 14);
		this.getContentPane().add(lblNombreDeLaSala);

		JLabel lblCantidadDeParticipantes = new JLabel("Cantidad de participantes:");
		lblCantidadDeParticipantes.setBounds(15, 55, 150, 14);
		this.getContentPane().add(lblCantidadDeParticipantes);

		nombreDeLaSala = new JTextField();
		nombreDeLaSala.setColumns(50);
		nombreDeLaSala.setBounds(135, 24, 200, 20);
		nombreDeLaSala.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (nombreDeLaSala.getText().length() > 29) {
					e.consume();
				}
			}
		});
		this.getContentPane().add(nombreDeLaSala);

		cantidadParticipantes = new JTextField();
		cantidadParticipantes.setColumns(3);
		cantidadParticipantes.setBounds(175, 55, 39, 20);
		cantidadParticipantes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
					e.consume();
				} else if (cantidadParticipantes.getText().length() > 1) {
					e.consume();
				}
			}
		});
		this.getContentPane().add(cantidadParticipantes);

		JButton btnCrear = new JButton("Crear");
		btnCrear.setBounds(80, 88, 89, 23);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearSala();
			}
		});
		this.getContentPane().add(btnCrear);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(180, 88, 89, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarVentana();
			}
		});
		this.getContentPane().add(btnCancelar);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void crearSala() {
		if (nombreDeLaSala.getText().length() == 0) {
			JOptionPane.showConfirmDialog(this, "Debe ingresar el nombre de la sala.", "Atencion...",
					JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
		} else if (cantidadParticipantes.getText().length() == 0) {
			JOptionPane.showConfirmDialog(this, "Debe definir la cantidad de participantes [1 a 99 participantes].",
					"Atencion...", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
		} else if (Integer.parseInt(cantidadParticipantes.getText()) <= 0) {
			JOptionPane.showConfirmDialog(this, "Debe definir la cantidad de participantes [1 a 99 participantes].",
					"Atencion...", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
		} else if (JOptionPane.showConfirmDialog(this, "Desea crear la sala", "Confirmar...",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			this.cliente.crearSalaEnServer(new SalaChat(0, nombreDeLaSala.getText(),
					Integer.parseInt(cantidadParticipantes.getText()), this.usuario));

			String mensaje = "";
			if (mensaje.isEmpty()) {
				this.actualiza = true;
				cerrarVentana();
			} else {
				JOptionPane.showConfirmDialog(this, mensaje, "Atencion...", JOptionPane.CLOSED_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void cerrarVentana() {
		this.setVisible(false);
		this.dispose();
	}

	public boolean actualizaSalas() {
		return actualiza;
	}

}
