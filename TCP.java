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

import java.io.*;
import java.net.*;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;

/**
 * Client class that handles the connection to the server, retrieves the data
 * stream and send commands to the server
 *
 * @author Kristian Homdrom
 */
public class TCP implements Runnable {

    private boolean connected = false;
    private Socket soc;
    private InputStream inputStream;
    private OutputStream outputStream;  
    private ByteBuffer dataArray;
    private String IP;
    private final int port = 5001;
    private PrintStream ps;
    private Data data;
    private byte stopByte=1;
    
    private float oldLightValue=0;
 
    public TCP(Data data) {
        this.dataArray=ByteBuffer.allocate(17);
        this.dataArray.clear();
        this.data = data;
    }
    /**
     * Connects the client to the server through a socket and saves the IP and
     * port to the global variables IP and port
     *
     * @param IP IP of the server to connect to
     * @throws IOException Throws an IOException when the connection is
     * unsuccessful
     */
    public void connect(String IP) throws IOException {
        soc = new Socket(IP, port);
        this.inputStream = soc.getInputStream();
        this.outputStream = soc.getOutputStream();
        this.ps = new PrintStream(outputStream);
        this.IP = IP;
        connected = true;
    }

    /**
     * Retrieves the byte array containing sensor data from the camera module and updates
     * these values in the data storage.
     *
     * @throws Exception Throws exception if header is invalid or data is null
     */
    public void receiveData() throws Exception {

        byte[] byteArray = new byte[40];
        if (inputStream.available() >= 40) {
            inputStream.read(byteArray, 0, 40);
            data.setPressure(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(0));
            data.setAltitude(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(4));
            data.setHumidity(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(8));
            
            data.setTempOutSide(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(12));
            data.setTempCamera(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(16));
            data.setTempTop(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(20));
            data.setDewPoint(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(24));
            
            data.setYaw(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(28));
            data.setPitchAngle(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(32));
            data.setRollAngle(ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getFloat(36));
           
        }
    }
   /**
    * Send command data to server and stopindicator.
    * TODO add servoaxis
    * @throws IOException 
    */
    public synchronized void sendData()throws IOException{
        this.dataArray.put(0,data.returnStopByte());
        this.dataArray.putFloat(1,data.getLightValue());
        this.dataArray.putFloat(5,data.getCameraAngle());
        this.dataArray.putFloat(9,data.getYaxis());
        this.dataArray.putFloat(13,data.getPressSeaLevel());
        byte[] message = new byte[17] ;
         for (int i = 0; i <this.dataArray.capacity(); i++) {

            message[i] = dataArray.get(i);
        }  
        DataOutputStream dout = new DataOutputStream(this.outputStream);
        dout.write(message);
        //NY MÅ TESTES!
        dout.flush();
    }
    /**
     * Returns the status of connection socket
     *
     * @return The connection status of the socket
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Closes the socket if the client is already connected
     *
     * @throws IOException Throws IOException if there is a problem connecting
     */
    public void disconnect() throws IOException {
        if (soc != null) {
            soc.close();
        }
        connected = false;
    }

    @Override
    public void run() {
        if (connected) {
            try {
                receiveData();
                sendData();
                //When stop is set, flood server with stop signal to make sure data is not lost.
                if (data.returnStopByte() == (byte) 2) {
                    for (int i = 0; i < 10; i++) {
                        sendData();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
                System.out.println(ex.getCause());

            }
        }
    }
}
