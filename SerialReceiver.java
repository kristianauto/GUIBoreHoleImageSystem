/*
 * This code is for the MSc project named "BoreHoleImageSystem".
 * The purpose is to build a camera module which will be lowered into the antartic depths.
 * There it will provide a video stream and provide sensor data.
 * 
 * The system consists of a Raspberry Pi in the camera module that is connected to several 
 * sensors and Arduino.
 * The external computer which is on the surface is connected to a microcontroller with 
 * commands for controlling camera angle and dimming of lights,
 * echo sounder and the RBPi. It will present and save data in addition to
 * handle user commands.
 */
package borehole.gui;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Handles serial connection on the USB port, establish connection, extract data by plug and play
 * @author Kristian Homdrom
 */
public class SerialReceiver implements Runnable {

    private boolean connected = false;
    private SerialPort serialPort;
    private final Data data;
    private int serialCount = 0;

    private int[] serialInArray = new int[4];

    /**
     * Constructor create link to data storage class
     *
     * @param data
     */
    public SerialReceiver(Data data) {
        this.data = data;

    }

    /**
     * Open the serial connection to the conneted microcontroller over serial
     * comm link
     */
    @SuppressWarnings("empty-statement")
    private synchronized void openConnection() {

        if (!connected) {
            SerialPort[] comPorts = SerialPort.getCommPorts();
            String com;
            for (SerialPort comPort : comPorts) {
                com = comPort.getSystemPortName();
                if (com.contains("COM")) {
                    serialPort = comPort;
                    serialPort.setBaudRate(115200);
                }
            }
            try {
                if (!this.serialPort.isOpen() && serialPort != null) {
                    System.out.println(this.serialPort.getSystemPortName());;
                    this.serialPort.openPort();
                    Thread.sleep(100);
                    connected = true;
                    System.out.println("connected" + serialPort.isOpen());
                    // if port failed to open get message
                } else {
                    System.out.println("Serialport does not exist");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Check if serial port is open
     *
     * @return state of the serialport
     */
    public boolean getConnection() {
        //return this.serialPort.isOpen();

        return connected;
    }

    /**
     * Code to be run continously
     */
    @Override
    public void run() {
        openConnection();

        if (getConnection());
        {
            getSerialData();

        }
    }

    /**
     * Get serialData from the comlink, added eventlistener on connected port as
     * to only read when new data arrives When event occurs read as much data as
     * there is available on the comlink
     */
    public void getSerialData() {

        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }
                byte[] newData = new byte[serialPort.bytesAvailable()];
                int numRead = serialPort.readBytes(newData, newData.length);
                try {
                    getValues(newData);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });
    }

    /**
     * Send the bytearray read from serial link to be converted to int which is
     * sent to Data class
     *
     * @param b bytearray from serial port
     */
    private void getValues(byte[] b) {
        int[] intArray = new int[b.length];
        // intArray[index]=value;
        for (int i = 0; i < b.length; i++) {
            //System.out.println("i " + i);
            intArray[i] = (b[i] & 0xFF) * 4;
        }
        if (intArray.length == 3) {
            data.setLightValue((float) intArray[0]);
            data.setCameraAngle((float) intArray[1]);
            data.setYAxis((float) intArray[2]);
        }

        if (serialCount == 2) {
            serialCount = 0;
        }
    }

}
