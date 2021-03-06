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
		} else if (JOptionPane.showConfirmDialog(this, "Desea crear la sala", "Confirmar...",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			this.cliente.crearSalaEnServer(nombreDeLaSala.getText());
			this.cerrarVentana();
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
