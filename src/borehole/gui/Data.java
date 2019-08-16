/*
 * This code is for the MSc project named "BoreHoleImageSystem".
 * The purpose is to build a camera module which will be lowered into the antartic depths.
 * There it will provide a video stream and provide sensor data of.
 * 
 * The system consists of a Raspberry Pi in the camera module that is connected to several 
 * sensors and Arduino.
 * The external computer which is on the surface is connected to a microcontroller with 
 * commands for controlling camera angle and dimming of lights,
 * echo sounder and the RBPi. It will present and save data in addition to
 * handle user commands.
 */
package borehole.gui;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The data class is a storage box class that let the different threads change
 * and retrieve the different data. The class is a subclass of the java class
 * Observable, which allows for observers to subscribe and update their values
 * whenever a change happens. Thread safety is achived using synchronized
 * methods .
 *
 * @author Kristian Homdrom
 */
public final class Data extends Observable {

    private float pressure = 1000;
    private float altitude = 0;
    private float humidity = 0;

    //Set to standard value for no action
    private float lightValue = 1023;
    private float angle = 0;
    private float servoYAxis = 500;

    private float pitchAngle = 0;
    private float yawAngle = 0;
    private float rollAngle = 0;

    private float tempOutside = 0;
    private float tempCamera = 0;
    private float tempTop = 0;
    private float dewPoint = 0;

    private float channel1 = 0;
    private float channel2 = 0;
    private float channel3 = 0;
    private float channel4 = 0;

    private ArrayList<String> labels = new ArrayList();
    private float[] channelValues = new float[4];

    private String defaultIP = "";
    private BufferedImage videoImage;

    private long timer = System.currentTimeMillis();
    private boolean filtering = false;
    private boolean capture = false;
    private byte stopByte;
    private boolean record;
    private float pressSeaLevel = 1023;

    /**
     * Creates an object of the class Data.
     */
    public Data() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Drill Options.txt"));
            defaultIP = br.readLine();
            labels.add(0, br.readLine());
            labels.add(1, br.readLine());
            labels.add(2, br.readLine());
            labels.add(3, br.readLine());
            labels.add(4, br.readLine());
            labels.add(5, br.readLine());
            labels.add(6, br.readLine());
            labels.add(7, br.readLine());
            channelValues[0] = channel1;
            channelValues[1] = channel2;
            channelValues[2] = channel3;
            channelValues[3] = channel4;
        } catch (IOException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sets the default connection IP
     *
     * @param ip The default connection IP
     */
    public synchronized void setDefaultIP(String ip) {
        defaultIP = ip;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the default connection IP
     *
     * @return The default connection IP
     */
    public synchronized String getDefaultIP() {
        return defaultIP;
    }

    /**
     * Returns the value of the channel as a float. (Index 1-4)
     *
     * @param channel Index of channel
     * @return Value of the channel as float
     */
    public synchronized float getChannelValue(int channel) {
        if (channel < 0 && channel > 5) {
            return channelValues[channel - 1];
        } else {
            return (float) 0.001;
        }
    }

    /**
     * Sets the value of one of the inputs and notifies observers (Index 1-4).
     *
     * @param value Value of the channel
     * @param channel Index of the channel
     */
    public synchronized void setChannel(float value, int channel) {
        if (channel < 0 && channel > 5) {
            channelValues[channel - 1] = value;
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Updates the current pitch angle of the ROV and notifies observers
     *
     * @param angle Current pitch angle of the camera
     */
    public synchronized void setPitchAngle(float angle) {
        pitchAngle = angle;
        setChanged();
        notifyObservers();
    }

    /**
     * Retrieves the current pitch angle
     *
     * @return Current pitch angle of the camera
     */
    public synchronized float getPitchAngle() {
        return pitchAngle;
    }

    /**
     * Updates the current roll angle of the camera
     *
     * @param angle Current roll angle of the camera
     */
    public synchronized void setRollAngle(float angle) {
        rollAngle = angle;
        setChanged();
        notifyObservers();
    }

    /**
     * Retrieves the current roll angle
     *
     * @return Current roll angle of the camera
     */
    public synchronized float getRollAngle() {
        return rollAngle;
    }

    /**
     * Updates the curren yaw angle of the camera and notifies observers
     *
     * @param angle Current yaw angle of the camera
     */
    public synchronized void setYaw(float angle) {
        yawAngle = angle;
        setChanged();
        notifyObservers();
    }

    /**
     * Retrieves the current yaw angle
     *
     * @return Current yaw angle of the camera
     */
    public synchronized float getYawAngle() {
        return yawAngle;
    }

    /**
     * Updates the light brightness from the potentiometer
     *
     * @param light Light brightness set by user using potentiometer
     */
    public synchronized void setLightValue(float light) {
        if (light > 1000) {
            light = 1023;
        }
        lightValue = light;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the current light brightness
     *
     * @return Current light brightness set
     */
    public synchronized float getLightValue() {
        return lightValue;
    }

    /**
     * Updates the angle of camera in x-axis
     *
     * @param angle of camera in x-axis
     */
    public synchronized void setCameraAngle(float angle) {
        this.angle = angle;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the current angle of camera in x-axis
     *
     * @return Current angle of camera in x-axis
     */
    public synchronized float getCameraAngle() {
        return angle;
    }

    /**
     * Updates the servoMotorvalue of the y-axis motor from joystick
     *
     * @param yAxis Y-axis value for motors
     */
    public synchronized void setYAxis(float yAxis) {
        servoYAxis = yAxis;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the current Y-axis value of the joystick.
     *
     * @return Current Y-axis value of the joystick
     */
    public synchronized float getYaxis() {
        return servoYAxis;
    }

    /**
     * Updates the temp outside of the camera module and notifies observers
     *
     * @param temp temperature outside of the camera module
     */
    public synchronized void setTempOutSide(float temp) {
        this.tempOutside = temp;
        setChanged();
        notifyObservers();
    }

    /**
     * Retrieves the current temperature outside of the camera module
     *
     * @return temperature outside of the camera module
     */
    public synchronized float getTempOutside() {
        return tempOutside;
    }

    /**
     * Updates the current temperature around the camera
     *
     * @param temp Current temperature around the camera
     */
    public synchronized void setTempCamera(float temp) {
        this.tempCamera = temp;
        setChanged();
        notifyObservers();
    }

    /**
     * Retrieves the current temperature around the camera
     *
     * @return Current temperature around the camera
     */
    public synchronized float getTempCamera() {
        return tempCamera;
    }

    /**
     * Updates the current temperature around converters in the camera module
     *
     * @param temp Currenttemperature around converters in the camera module
     */
    public synchronized void setTempTop(float temp) {
        this.tempTop = temp;
        setChanged();
        notifyObservers();
    }

    /**
     * Retrieves the current temperature around converters in the camera module
     *
     * @return Current temperature around converters in the camera module
     */
    public synchronized float getTempTop() {
        return tempTop;
    }

    /**
     * Updates the current humidity within camera module
     *
     * @param hum current humidity within camera module
     */
    public synchronized void setHumidity(float hum) {
        humidity = hum;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns current humidity within camera module
     *
     * @return current humidity within camera module
     */
    public synchronized float getHumidity() {
        return humidity;
    }

    /**
     * Updates the current dewPoint within camera module
     *
     * @param dewP current humidity within camera module
     */
    public synchronized void setDewPoint(float dewP) {
        dewPoint = dewP;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the current dewPoint within camera module
     *
     * @return current dewPoint within camera module
     */
    public synchronized float getDewPoint() {
        return dewPoint;
    }

    /**
     * Updates the image of the video stream and notifies observers
     *
     * @param image New image in the video stream
     */
    public synchronized void setVideoImage(BufferedImage image) {
        videoImage = null;
        videoImage = image;
        setChanged();
        notifyObservers();
    }

    public synchronized void setFilteredVideoImage(BufferedImage filtered) {
        videoImage = null;
        videoImage = filtered;
        Kernel kernel = new Kernel(3, 3, new float[]{-1, -1, -1, -1, 9, -1, -1, -1, -1});
        BufferedImageOp op = new ConvolveOp(kernel);
        videoImage = op.filter(videoImage, null);
        setChanged();
        notifyObservers();
    }

    /**
     *
     * /**
     * Updates the pressure surrounding the camera module
     *
     * @param pres Pressure surrounding the camera module
     */
    public synchronized void setPressure(float pres) {
        pressure = pres;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the current pressure around the camera module
     *
     * @return Current pressure around the module
     */
    public synchronized float getPressure() {
        return pressure;
    }

    /**
     * Updates the altitude of the camera module
     *
     * @param alt Altitude of the camera module
     */
    public synchronized void setAltitude(float alt) {
        altitude = alt;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the current altitude if the camera module
     *
     * @return Current altitude of the module
     */
    public synchronized float getAltitude() {
        return altitude;
    }

    /**
     * Returns the current image in the video stream
     *
     * @return Current image in the video stream
     */
    public synchronized BufferedImage getVideoImage() {
        return videoImage;
    }

    /**
     * Set the state of the fitlering process, on/off
     *
     * @param filter filter status set by user
     */
    public synchronized void setFiltering(boolean filter) {
        filtering = filter;

    }

    /**
     * Return the state of image sharpen filtering
     *
     * @return return the state of fitlering
     */
    public boolean getFiltering() {
        return filtering;
    }

    /**
     * Set the value of stopbyte to indicate server program to shutDown
     *
     * @param value value to set StopByte to, 0 to stop and 1 to run as normal
     */
    public synchronized void setStopByte(byte value) {
        stopByte = value;
    }

    /**
     * return the value of the stopByte indicator for serverApplication
     *
     * @return return the value of the stopByte indicator
     */
    public synchronized byte returnStopByte() {
        return stopByte;
    }

    /**
     * Set capture to true in order to store image as a jpg in folder
     *
     * @param capture boolean paramter to activate
     */
    public synchronized void setCapture(boolean capture) {
        this.capture = capture;
        setChanged();
        notifyObservers();
        this.capture = false;
    }

    /**
     * return if a image should be stored or not
     *
     * @return return if a image should be stored or not
     */
    public synchronized boolean getCapture() {
        boolean value = capture;
        return value;
    }

    /**
     * Set recording value, determines if video to be stored or not.
     *
     * @param record record param determines if video to be stored or not
     */
    public synchronized void setRecording(boolean record) {
        this.record = record;
        setChanged();
        notifyObservers();
    }

    /**
     * return if recording should be stored or not
     *
     * @return return if recording should be stored or not
     */
    public synchronized boolean getRecording() {
        return record;
    }

    public synchronized void setPressureSeaLevel(int press) {
        this.pressSeaLevel = (float) press;
        setChanged();
        notifyObservers();
    }

    /**
     * return if recording should be stored or not
     *
     * @return return if recording should be stored or not
     */
    public synchronized float getPressSeaLevel() {
        return pressSeaLevel;
    }

}
