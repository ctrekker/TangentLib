package com.burnscoding.tangent.lib.stream;

import java.awt.image.BufferedImage;

public interface VideoStreamClientFrameHandler {
    public void handle(BufferedImage frame);
}
