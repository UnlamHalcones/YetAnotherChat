package ar.edu.unlam.servidor.graphics;

import ar.edu.unlam.servidor.ServidorChat;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VentanaServer extends JFrame {

    private JPanel contentPane;
    private JTextField portField;
    private ServidorChat server;
    private JButton disconnectButton;
    private JButton connectButton;
    
    public VentanaServer() {

        setTitle("Servidor YetChat");
        setResizable(false);
        setBounds(100, 100, 261, 131);

        contentPane = new JPanel();
        contentPane.setBorder(null);
        setContentPane(contentPane);
        
        connectButton = new JButton("Connect");
        connectButton.addActionListener(e -> {
            String portToConnect = this.portField.getText();
            if(!portToConnect.isEmpty()) {
                new Thread(() -> {
                    server = new ServidorChat(Integer.valueOf(portToConnect));
                    server.execute();
                }).start();
                connectButton.setEnabled(false);
                portField.setEnabled(false);
                disconnectButton.setEnabled(true);
            }
        });

        disconnectButton = new JButton("Disconnect");
        disconnectButton.setEnabled(false);
        disconnectButton.addActionListener(e -> {
//            server.disconnect();
            server = null;
            connectButton.setEnabled(true);
            portField.setEnabled(true);
            disconnectButton.setEnabled(false);
        });

        portField = new JTextField();
        portField.setColumns(10);
        portField.setText("9091");

        JLabel lblPuerto = new JLabel("Puerto");
        JPanel jPanel = new JPanel();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(jPanel,
                        "Seguro que desea salir?", "Cerrar Ventana?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    if(server != null) {
//                        server.disconnect();
                        server = null;
                    }
                    System.exit(0);
                }
            }
        });

        armarLayout(lblPuerto);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void armarLayout(JLabel lblPuerto) {
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addComponent(lblPuerto)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(portField))
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addComponent(connectButton)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(disconnectButton)))))
        			.addContainerGap(39, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblPuerto)
        				.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(connectButton)
        				.addComponent(disconnectButton))
        			.addGap(4)
        			.addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
    }

    public static void main(String[] args) {
        new VentanaServer();
    }
}
