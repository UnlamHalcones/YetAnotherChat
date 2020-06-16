package ar.edu.unlam.cliente.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.edu.unlam.servidor.entidades.*;

public class VentanaIngresoCliente extends JFrame {
	 
		private JTextField tfUsername;
	    private JTextField tfIp;
	    private JFormattedTextField ftfPuerto;
	    private JLabel lbUsername;
	    private JLabel lbIp;
	    private JLabel lbPuerto;
	    private JButton btnLogin;
	    private JButton btnCancel;
	 
	    public VentanaIngresoCliente() {
	    	super("Iniciar Sesion");

	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	        //IngresoCliente ingCli = new IngresoCliente();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	 
	        lbUsername = new JLabel("Usuario: ");
	        cs.gridx = 0;
	        cs.gridy = 0;
	        cs.gridwidth = 1;
	        panel.add(lbUsername, cs);
	 
	        tfUsername = new JTextField(10);
	        cs.gridx = 1;
	        cs.gridy = 0;
	        cs.gridwidth = 2;
	        panel.add(tfUsername, cs);
	        tfUsername.setText("marce");
	        
	        tfUsername.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						//IngresoCliente(ingCli);
		            	IngresoCliente();
					}
					
					if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
	                    System.exit(0);
	                }
				}
			});
	 
	        lbIp = new JLabel("IP: ");
	        cs.gridx = 0;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(lbIp, cs);
	 
	        tfIp = new JTextField(10);
	        cs.gridx = 1;
	        cs.gridy = 1;
	        cs.gridwidth = 2;
	        panel.add(tfIp, cs);
	        tfIp.setText("192.168.0.1");
	        
	        tfIp.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						//IngresoCliente(ingCli);
		            	IngresoCliente();
					}
					
					if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
	                    System.exit(0);
	                }
				}
			});
	        
	        lbPuerto = new JLabel("Puerto: ");
	        cs.gridx = 0;
	        cs.gridy = 2;
	        cs.gridwidth = 1;
	        panel.add(lbPuerto, cs);
	 
	        ftfPuerto = new JFormattedTextField (10);
	        cs.gridx = 1;
	        cs.gridy = 2;
	        cs.gridwidth = 2;
	        panel.add(ftfPuerto, cs);
	        ftfPuerto.setText("1080");
	        
	        ftfPuerto.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						//IngresoCliente(ingCli);
		            	IngresoCliente();
					}
					
					if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
	                    System.exit(0);
	                }
				}
			});
	        
	        btnLogin = new JButton("Login");
	 
	        btnLogin.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e) {
	            	//IngresoCliente(ingCli);
	            	IngresoCliente();
	            }
	        });
	        
	        btnCancel = new JButton("Cancel");
	        btnCancel.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	            }
	        });
	        JPanel bp = new JPanel();
	        bp.add(btnLogin);
	        bp.add(btnCancel);
	 
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(bp, BorderLayout.PAGE_END);
	 
	        pack();
	        
	        this.add(btnLogin);
	        this.setSize(250, 150);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setLayout(new FlowLayout());
	        this.setLocationRelativeTo(null);
	        this.getContentPane().add(btnLogin);
	        this.setResizable(false);
	        this.setVisible(true);
	        panel.setFocusable(true);	      
	    }
	    
	    public void IngresoCliente() {
	    //public void IngresoCliente(IngresoCliente ingCli) {
	    	JDialog ld= new JDialog();
	    	//if (ingCli.Autenticacion(tfUsername.getText(), tfIp.getText(), Integer.valueOf(ftfPuerto.getText()))) {
	    		//ingCli = new IngresoCliente(tfUsername.getText(), tfIp.getText(), Integer.valueOf(ftfPuerto.getText()));
                JOptionPane.showMessageDialog(ld,
                        //"Bienvenido " + ingCli.getUserName() + "!",
                		"Bienvenido " +  "!",
                        "Login",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new VentanaChat().setVisible(true);
            /*} else {
                JOptionPane.showMessageDialog(ld,
                        "Los datos ingresados son incorrectos!",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
                tfUsername.setText("");
                tfIp.setText("");
                ftfPuerto.setText("1080");
            }*/
	    }
	    
	    public static void main(String[] args) {
	    	new VentanaIngresoCliente();
	    }
}
