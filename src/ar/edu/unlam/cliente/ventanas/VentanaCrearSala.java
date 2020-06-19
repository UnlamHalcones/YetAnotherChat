package ar.edu.unlam.cliente.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	private static final long serialVersionUID = -5133533693845359439L;
	private JTextField nombreDeLaSala;
	private JTextField cantidadParticipantes;
	private Usuario usuario;
	private Cliente cliente;
	private boolean actualiza;

	public VentanaCrearSala(JFrame padre, Usuario usuario, Cliente cliente) {
		super(padre, "Crear sala...", true);
		this.usuario = usuario;
		this.cliente = cliente;
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
		this.getContentPane().add(nombreDeLaSala);

		cantidadParticipantes = new JTextField();
		cantidadParticipantes.setColumns(3);
		cantidadParticipantes.setBounds(175, 55, 39, 20);
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
		if (JOptionPane.showConfirmDialog(this, "Desea crear la sala", "Confirmar...", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == 0) {
			int cantidad = Integer.parseInt(cantidadParticipantes.getText());
			String mensaje = this.cliente.lobby.crearSala(new SalaChat(0, nombreDeLaSala.getText(), cantidad, this.usuario));
			if (mensaje.isEmpty()) {
				this.actualiza = true;
				cerrarVentana();
			} else {
				JOptionPane.showConfirmDialog(this, mensaje, "Atencion...", JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE);
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
