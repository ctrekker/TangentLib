package com.burnscoding.tangent.lib.stream;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class WebcamStreamServer extends ImageStreamServer {
    private ImageStreamServerRequestHandler rh = new WebcamStreamServerRequestHandler();
    public WebcamStreamServer(int port) throws IOException {
        super(port);
        setRequestHandler(rh);
    }

    private class WebcamStreamServerRequestHandler implements ImageStreamServerRequestHandler {
        private Webcam webcam;
        public WebcamStreamServerRequestHandler() {
            webcam = Webcam.getWebcams().get(0);
            webcam.open();
        }
        public WebcamStreamServerRequestHandler(Webcam webcam) {
            this.webcam = webcam;
        }
        @Override
        public BufferedImage handle() {
            if(webcam != null) {
                return webcam.getImage();
            }
            return null;
        }
    }
}
