
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

/**
 * Main class for the User interface for camera module project.
 * @author Kristian Homdrom
 */
public class BoreHoleGUI {

    /**
     * Creates all class objects and runs them using the executor framework as scheduled Threads
     * Adds obersvers for the data class to notify dependant classes about changes without adding high coupling
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Data data = new Data();

        DataLogger logger = new DataLogger(data);
        TCP client = new TCP(data);
        DrillFrame frame = new DrillFrame(data, client);
        ImageToVideo encoder = new ImageToVideo(data);
        SerialReceiver serialComm = new SerialReceiver(data);
        UDP videoStream = new UDP(data);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);
        //causes doRun.run() to be executed asynchronously on the AWT event dispatching thread.
        //This will happen after all pending AWT events have been processed.
        //This method should be used when an application thread needs to update the GUI.

        SwingUtilities.invokeLater(frame);

        data.addObserver(logger);
        data.addObserver(frame);
        data.addObserver(encoder);
        executor.scheduleAtFixedRate(logger,
                3000, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(encoder,
                20, 40, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(client,
                0, 50, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(serialComm,
                0, 10, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(videoStream,
                0, 1, TimeUnit.MILLISECONDS);
        //Shut down the program when System.exit(0) has been called, exit button pressed
        Runtime.getRuntime()
                .addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        executor.shutdown();
                        //In case user forget to stop recording
                        encoder.finishVideo();
                    }
                },
                        "Shutdown-thread"));
    }

}
