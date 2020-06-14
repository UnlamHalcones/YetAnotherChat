package ar.edu.unlam.servidor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientePrueba extends Thread {

    public ClientePrueba(String ip, int puerto, String userName) throws IOException {

        Socket socket = new Socket(ip, puerto);

        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(userName);

        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        Scanner scanner = new Scanner(System.in);

        Thread hiloEscritura = new Thread(() -> {
            try {
                String str = scanner.nextLine();
                while (!str.equals("DISCONNECT")) {

                    dataOutputStream.writeUTF(str);
                    str = scanner.nextLine();

                }
                dataOutputStream.writeUTF(str);
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
                System.exit(0);
                System.out.println("disconnect from server");
            } catch (IOException e) {
                System.err.println("Error con el servidor." + e.getMessage());
            }
        });

        Thread hiloLectura = new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String messageFromServer = dataInputStream.readUTF();
                    System.out.println(messageFromServer);
                } catch (IOException e) {
                    System.err.println("Error con el servidor." + e.getMessage());
                }
            }

        });

        hiloEscritura.start();
        hiloLectura.start();

    }

    public static void main(String[] args) {
        try {
            new ClientePrueba("localhost", Integer.valueOf(args[0]), args[1]);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
