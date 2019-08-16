
/*
 * This code is for the MSc project named "BoreHoleImageSystem".
 * The purpose is to build a camera module which will be lowered into the antartic depths.
 * There it will provide a video stream and provide sensor data of.
 * 
 * The system consists of a Raspberry Pi in the camera module that is connected to several 
 * sensors and Arduino.
 * The external computer which is on the surface is connected to a microcontroller with 
 * commands for control.amera angle and dimming of lights
 */
package borehole.gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This class processes incoming images from a DatagramPacket. The class
 * receives an image on a DatagramSocket and convert it as a BufferedImage.
 *
 * @author Kristian Homdrom
 */
public class UDP implements Runnable {

    static BufferedImage image;

    private boolean debug = false;

    private Data data;
    private DatagramSocket videoSocket;

    private long timer = System.currentTimeMillis();

    /**
     * Constructor class for UDP client class Initializing UDP socket on port
     * 5002, and create data class object
     *
     * @param data link to Data object
     */
    public UDP(Data data) {
        try {
            this.data = data;
            videoSocket = new DatagramSocket(5002);
        } catch (SocketException e) {
            System.out.println(e);
        }

    }

    /**
     * Return status if filtering method is to be used on video
     *
     * @return filtering status
     */
    private boolean getFilterStatus() {
        boolean dofilter = data.getFiltering();
        return dofilter;
    }

    /**
     * Rotate the full bufferedImage by degrees
     *
     * @param img image to be rotated
     * @param angle
     * @return
     */
    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        //casta til imageObserver
        g2d.drawImage(img, 0, 0, (ImageObserver) this);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();

        return rotated;
    }

    /**
     * Thread method to run continously, receive incoming datapackets and
     * convert datagram into bufferedImage.
     */
    @Override
    public void run() {
        try {
            if (System.currentTimeMillis() - timer > 60000) {
                videoSocket.close();
                videoSocket = new DatagramSocket(5002);
                timer = System.currentTimeMillis();
                System.out.println("Reconnected");
            }
            //Creating new DatagramPacket form the packet recived on the videoSocket
            byte[] receivedData = new byte[25000];
            DatagramPacket receivePacket = new DatagramPacket(receivedData,
                    receivedData.length);
            if (receivePacket.getLength() > 0) {
                long startTime = System.currentTimeMillis();
                //Updates the videoImage from the received DatagramPacket
                videoSocket.receive(receivePacket);

                if (debug) {
                    System.out.println("Videopackage received");
                }
                //Reads incomming byte array into a BufferedImage
                ByteArrayInputStream bais = new ByteArrayInputStream(receivedData);
                image = ImageIO.read(bais);
                //BufferedImage img = rotateImageByDegrees(this.image, 90);
                //Check whether incoming pciture stream is to be fitlered with sharpening filter or not
                if (getFilterStatus()) {
                    data.setFilteredVideoImage(image);
                } else {
                    data.setVideoImage(image);
                }
                long endTime = System.currentTimeMillis();
                receivedData = null;
                bais = null;
                if (debug) {
                    System.out.println(endTime - startTime);
                }

            }

        } catch (SocketException ex) {
            Logger.getLogger(UDP.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(UDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
