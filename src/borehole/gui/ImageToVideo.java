
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jcodec.api.awt.AWTSequenceEncoder;

/**
 * This class converts buffered image to a video file.
 *
 * @author Kristian Homdrom
 *
 */
public class ImageToVideo implements Runnable, Observer {

    private BufferedImage videoImage;
    private Data data;
    private AWTSequenceEncoder enc;
    private int frame = 0;
    private LocalDateTime tid;
    private boolean recording = false;

    /**
     * Creates an instance of a video encoder and create the MP4 file and gives
     * it a unique name
     *
     * @param data Data containing the images
     */
    public ImageToVideo(Data data) {
        this.data = data;
    }

    /**
     * Method that returns the current date
     *
     * @return returns the current day.month.year to create new logfile
     */
    private String getDate() {

        tid = LocalDateTime.now();
        String minute, hour, day, month, year;
        if (tid.getMinute() < 10) {
            minute = "0" + Integer.toString(tid.getMinute());
        } else {
            minute = Integer.toString(tid.getMinute());
        }
        if (tid.getHour() < 10) {
            hour = "0" + Integer.toString(tid.getHour());
        } else {
            hour = Integer.toString(tid.getHour());
        }
        String time = hour + "." + minute + " ";
        if (tid.getDayOfMonth() < 10) {
            day = "0" + Integer.toString(tid.getDayOfMonth());
        } else {
            day = Integer.toString(tid.getDayOfMonth());
        }
        if (tid.getMonthValue() < 10) {
            month = "0" + Integer.toString(tid.getMonthValue());
        } else {
            month = Integer.toString(tid.getMonthValue());
        }
        year = Integer.toString(tid.getYear());
        String date = time + day + "." + month + "." + year;
        return date;
    }

    /**
     * Thread method starts capturing images to video
     */
    @Override
    public void run() {
        try {
            //if recording objects are created, encode images to video 
            if (recording) {

                enc.encodeImage(videoImage);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * When new value arrives in data class update video for videostorage and
     * capture image if set.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        videoImage = data.getVideoImage();
        //Capture a single picture to be stored.
        if (data.getCapture() && (videoImage != null)) {
            captureSingleImage();
        }
        //If signal for recording and video exist create objects for recording
        if (data.getRecording() && (videoImage != null) && (!recording)) {
            startVideo();
        }
        //if recording and get stopsignal, end the video
        if (recording) {
            if (!data.getRecording()) {

                finishVideo();
            }
        }

    }

    /**
     * Stores a single image with date and timestamp
     */
    private void captureSingleImage() {

        try {
            File outputfile = new File(getDate() + ".jpg");
            ImageIO.write(videoImage, "jpg", outputfile);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Start the video storing, create objects and start encoding frames into
     * file
     */
    private void startVideo() {
        try {
            String fileName = "Drill Video " + getDate() + ".mp4";
            //enc = AWTSequenceEncoder.create24Fps(new File(fileName));
            enc = AWTSequenceEncoder.create24Fps(new File(fileName));
            System.out.println("enc " + enc.toString());
            recording = true;
        } catch (Exception ex) {
            Logger.getLogger(ImageToVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Encodes the remaining images and terminates the video
     */
    public void finishVideo() {
        try {
            recording = false;
            enc.finish();
        } catch (Exception ex) {
            Logger.getLogger(ImageToVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
