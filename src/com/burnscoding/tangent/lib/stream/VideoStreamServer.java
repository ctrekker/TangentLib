package com.burnscoding.tangent.lib.stream;

import com.sun.security.ntlm.Client;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class VideoStreamServer {
    private ServerSocket server;
    private Thread thread = null;
    private BufferedImage nextImage = null;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public VideoStreamServer(int port) throws IOException {
        server = new ServerSocket(port);
    }

    public boolean start() {
        if(thread == null) {
            thread = new Thread(() -> {
                while(true) {
                    Socket conn = null;
                    try {
                        conn = server.accept();
                    } catch(IOException e) {
                        System.out.println("ERROR: Failed to accept client connection");
                        e.printStackTrace();
                    }

                    if(conn != null) {
                        ClientHandler ch = new ClientHandler(conn);
                        clients.add(ch);
                        new Thread(ch).start();
                    }
                }
            });
            thread.start();
            return true;
        }
        else {
            System.out.println("WARN: Stream server already started");
            return false;
        }
    }
    public void sendImage(BufferedImage img) {
        nextImage = img;
        for(int i=0; i<clients.size(); i++) {
            if(!clients.get(i).sendImage(img)) {
                clients.remove(i);
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket conn;
        private BufferedImage img = null;
        private boolean closed = false;
        private CountDownLatch latch = new CountDownLatch(1);

        public ClientHandler(Socket conn) {
            this.conn = conn;
        }
        @Override
        public void run() {
            try {
                System.out.println("Connected to client");

                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                DataInputStream in = new DataInputStream(conn.getInputStream());

                while(true) {
                    latch.await();
                    StreamUtil.writeBufferedImage(img, out);
                    latch = new CountDownLatch(1);
                }
            } catch(IOException | InterruptedException e) {
                e.printStackTrace();
                closed = true;
            }
        }
        public boolean sendImage(BufferedImage img) {
            this.img = img;
            latch.countDown();
            return !closed;
        }
    }
}
