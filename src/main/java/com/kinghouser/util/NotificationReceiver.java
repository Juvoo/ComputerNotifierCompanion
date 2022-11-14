package com.kinghouser.util;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class NotificationReceiver implements Runnable {

    private static Socket clientSocket = null;
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;

    public static void setup() {
        try {
            clientSocket = new Socket("192.168.232.2", 36832);
            System.out.println("Connected to server");

            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.flush();
            System.out.println("Created output stream");
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Created input stream");
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Connection Problems..");
        }

        if (clientSocket != null && objectOutputStream != null && objectInputStream != null) {
            new Thread(new NotificationReceiver()).start();
            System.out.println("Started thread");
        }
    }

    @Override
    public void run() {
        Object object;
        try {
            while ((object = objectInputStream.readObject()) != null) {
                System.out.println(object);
                if (object instanceof Notification) {
                    ((Notification)object).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}