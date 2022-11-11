package kinghouser.util;

import java.io.*;
import java.net.*;

public class NotificationRelayReceiver {

    public static void setup() {
        try {
            Socket socket = new Socket("192.168.43.1", 44985);
            System.out.println("Connected to server");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            while (objectInputStream.readObject() != null) {
                System.out.println("Received object...");
                ((Notification) objectInputStream.readObject()).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
