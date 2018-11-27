package com.burnscoding.tangent.lib.stream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class StreamUtil {
    public static void writeBufferedImage(BufferedImage img, DataOutputStream s) throws IOException {
        // Convert bufferedImage to byte array
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ImageIO.write(img, "png", b);
        b.flush();
        byte[] imgData = b.toByteArray();
        b.close();

        int imgSize = imgData.length;
        s.writeInt(imgSize);
        s.write(imgData);
    }
    public static BufferedImage readBufferedImage(DataInputStream s) throws IOException {
        ImageIO.setUseCache(false);

        int imgSize = s.readInt();
        if(imgSize <= 0) throw new IOException("Image size could not be determined");
        byte[] imgData = new byte[imgSize];

        s.readFully(imgData);

        ByteArrayInputStream b = new ByteArrayInputStream(imgData);

        BufferedImage out = ImageIO.read(b);
        return out;
    }
}
