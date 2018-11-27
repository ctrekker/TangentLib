package com.burnscoding.tangent.lib.stream;

import com.burnscoding.tangent.lib.image.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ImageStreamServer {
    private ServerSocket server;
    private Thread thread = null;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private ImageStreamServerRequestHandler requestHandler;
    public ImageStreamServer(int port) throws IOException {
        this(port, () -> {return null;});
    }
    public ImageStreamServer(int port, ImageStreamServerRequestHandler requestHandler) throws IOException {
        server = new ServerSocket(port);
        this.requestHandler = requestHandler;
    }

    public boolean start() {
        if(thread == null) {
            thread = new Thread(() -> {
                while(true) {
                    Socket conn = null;
                    try {
                        conn = server.accept();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }

                    if(conn != null) {
                        ClientHandler ch = new ClientHandler(conn, requestHandler);
                        new Thread(ch).start();
                    }
                }
            });
            thread.start();

            return true;
        }

        System.out.println("WARN: Image stream server already started");
        return false;
    }
    public void setRequestHandler(ImageStreamServerRequestHandler handler) {
        requestHandler = handler;
    }

    private class ClientHandler implements Runnable {
        private final Socket conn;
        private final ImageStreamServerRequestHandler requestHandler;

        public ClientHandler(Socket conn, ImageStreamServerRequestHandler requestHandler) {
            this.conn = conn;
            this.requestHandler = requestHandler;
        }

        @Override
        public void run() {
            try {
                DataInputStream in = new DataInputStream(conn.getInputStream());
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                while(true) {
                    String command = in.readUTF();
                    if(command.equalsIgnoreCase("REQUEST")) {
                        BufferedImage img = requestHandler.handle();
                        if(img != null) StreamUtil.writeBufferedImage(img, out);
                        else StreamUtil.writeBufferedImage(ImageUtil.getDefaultImage(), out);
                    }
                    else if(command.equalsIgnoreCase("EXIT")) {
                        conn.close();
                        break;
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
