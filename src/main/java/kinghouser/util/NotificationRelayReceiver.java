package kinghouser.util;

import java.io.*;
import java.net.Socket;

public class NotificationRelayReceiver {

    public static void setup() {
        try {
            Socket socket = new Socket("192.168.1.77", 5000);
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
                ((Notification) objectInputStream.readObject()).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
