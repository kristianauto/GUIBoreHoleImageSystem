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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

/**
 * This class creates a text file and logs the data every second. The text file
 * contains time stamps and the desired data.
 *
 * @author Kristian Homdrom
 */
public class DataLogger implements Observer, Runnable {

    private String dataString = " NaN";
    private Data data;
    private LocalDateTime now;
    File file;

    /**
     * Creates an instance of DataLogger and saves the current timestamp
     *
     * @param data The Data object containing the data to be logged
     */
    public DataLogger(Data data) {
        this.data = data;
        file = new File("Data Log " + getDate() + ".txt");
        setDataLabels();

    }

    /**
     * Method that returns the current date
     *
     * @return returns the current day.month.year to create new logfile
     */
    private String getDate() {
        String minute, hour, day, month, year;
        now = LocalDateTime.now();
        if (now.getMinute() < 10) {
            minute = "0" + Integer.toString(now.getMinute());
        } else {
            minute = Integer.toString(now.getMinute());
        }
        if (now.getHour() < 10) {
            hour = "0" + Integer.toString(now.getHour());
        } else {
            hour = Integer.toString(now.getHour());
        }
        String time = hour + "." + minute + " ";
        if (now.getDayOfMonth() < 10) {
            day = "0" + Integer.toString(now.getDayOfMonth());
        } else {
            day = Integer.toString(now.getDayOfMonth());
        }
        if (now.getMonthValue() < 10) {
            month = "0" + Integer.toString(now.getMonthValue());
        } else {
            month = Integer.toString(now.getMonthValue());
        }
        year = Integer.toString(now.getYear());
        String date = time + day + "." + month + "." + year;
        return date;
    }

    /**
     * Writes a header in the text file to indicate a new logging session
     */
    private void setDataLabels() {

        try {
            String seperator = System.getProperty("line.separator");
            String startString = seperator + seperator + "----- New session! -----" + seperator;
            writeToFile(startString);
            String channelLabels = "               ";
            dataString = " | Pressure: " + String.valueOf(data.getPressure());
            channelLabels += fixedLengthString("| Pressure", dataString.length());
            dataString += " | Humidity: " + String.valueOf(data.getHumidity());
            channelLabels += fixedLengthString("| Humidity", dataString.length() + 17 - channelLabels.length());
            dataString += " | TempOutside: " + String.valueOf(data.getTempOutside());
            channelLabels += fixedLengthString("| TempOutside", dataString.length() + 17 - channelLabels.length());
            dataString += " | TempTop: " + String.valueOf(data.getTempTop());
            channelLabels += fixedLengthString("| TempTop", dataString.length() + 17 - channelLabels.length());
            dataString += " | TempCamera: " + String.valueOf(data.getTempCamera());
            channelLabels += fixedLengthString("| TempCamera", dataString.length() + 17 - channelLabels.length());
            dataString += " | DewPoint: " + String.valueOf(data.getDewPoint());
            channelLabels += fixedLengthString("| DewPoint", dataString.length() + 17 - channelLabels.length());
            writeToFile(channelLabels);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Writes a string the the text file
     *
     * @param data String to write to the text file
     */
    private void writeToFile(String data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(data + System.getProperty("line.separator"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        float pressure = 10 * data.getPressure();
        pressure = (Math.round(pressure));
        pressure = pressure / 10;
        float hum = (Math.round(100 * data.getHumidity())) / 100;
        float tempOut = (Math.round(100 * data.getTempOutside())) / 100;
        float tempTop = (Math.round(100 * data.getTempTop())) / 100;
        float tempC = (Math.round(100 * data.getTempCamera())) / 100;
        float dew = (Math.round(100 * data.getDewPoint())) / 100;
        dataString = " | Pressure: " + String.valueOf(pressure);
        dataString += " | Humidity: " + String.valueOf(hum);
        dataString += " | TempOutside: " + String.valueOf(tempOut);
        dataString += " | TempTop: " + String.valueOf(tempTop);
        dataString += " | TempCamera: " + String.valueOf(tempC);
        dataString += " | DewPoint: " + String.valueOf(dew);
    }

    @Override
    public void run() {

        try {
            now = LocalDateTime.now();
            String second, minute, hour;
            if (now.getSecond() < 10) {
                second = "0" + Integer.toString(now.getSecond());
            } else {
                second = Integer.toString(now.getSecond());
            }
            if (now.getMinute() < 10) {
                minute = "0" + Integer.toString(now.getMinute());
            } else {
                minute = Integer.toString(now.getMinute());
            }
            if (now.getHour() < 10) {
                hour = "0" + Integer.toString(now.getHour());
            } else {
                hour = Integer.toString(now.getHour());
            }
            String time = hour + ":" + minute + ":" + second;
            //Write retrieved data to textfile with timestamp
            writeToFile("Time: " + time + dataString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Returns a string at a fixed length
     *
     * @param string String to change length of
     * @param length Wanted length of string
     * @return String at new fixed length
     */
    public static String fixedLengthString(String string, int length) {
        return String.format("%-" + length + "s", string);
    }

}
