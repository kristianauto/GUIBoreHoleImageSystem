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
 * Icons used for interfae a from Google material design under Apache License Version 2.0.

 */
package borehole.gui;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Main frame for GUI application 
 * Runs as a thread and obersvable to update data and video
 * whenever change is applied to data class
 * @author Kristian Homdrom
 */
public class DrillFrame extends javax.swing.JFrame implements Runnable, Observer {

    ImagePanel videoSheet;
    ImagePanel fullscreenVideoSheet;
    private BufferedImage videoImage;
    private final Data data;
    private long time = System.currentTimeMillis();
    private final int mode = 0;
    private final TCP client;
    private JSlider slider;

    /**
     * Creates new form DrillFrame
     *
     * @param data
     * @param client
     */
    public DrillFrame(Data data, TCP client) {
        initComponents();
        this.data = data;
        this.client = client;

        //Set the size of the sheet to project video
        videoSheet = new ImagePanel(cameraPanel.getWidth(), cameraPanel.getHeight());
        videoSheet.setBackground(cameraPanel.getBackground());
        fullscreenVideoSheet = new ImagePanel(cameraPanel1.getWidth(), cameraPanel1.getHeight());
        fullscreenVideoSheet.setBackground(cameraPanel1.getBackground());
        // Do not paint all pixels of sheet
        videoSheet.setOpaque(false);
        fullscreenVideoSheet.setOpaque(false);
        //Add the video to panel
        cameraPanel.add(videoSheet);
        cameraPanel1.add(fullscreenVideoSheet);
        captureButton.setEnabled(true);
        startSlider();
    }

    /**
     * Adds a changelistener to light value slider, only updates when stopped
     * moving Send values to dataClass, if values above 10 turn on lights.
     */
    private void startSlider() {
        lightBrightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                slider = (JSlider) evt.getSource();
                if (!slider.getValueIsAdjusting()) {
                    int value = 1023;
                    value = value - slider.getValue();

                    data.setLightValue(value);
                    try {
                        //If slider aboe value of 10, turn on light indicator
                        if (value < 1010) {
                            lightIndicator.setIcon(new ImageIcon(ImageIO.read(new File("src/borehole/gui/Images/lightIconOn.png"))));

                        } else {
                            lightIndicator.setIcon(new ImageIcon(ImageIO.read(new File("src/borehole/gui/Images/lightIcon.png"))));
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                }

            }
        });
    }

    /**
     * Calls the paintSheet method which updates the diplayed image.
     *
     * @param image The image to be displayed on the GUI
     */
    private void showImage(BufferedImage image) {
        if (fullscreen.isVisible()) {
            fullscreenVideoSheet.setSize(cameraPanel1.getSize());
            fullscreenVideoSheet.paintSheet(ImageUtils.resize(image, fullscreenVideoSheet.getParent().getWidth(), fullscreenVideoSheet.getParent().getHeight()));
        } else {
            videoSheet.paintSheet(ImageUtils.resize(image, videoSheet.getParent().getWidth(), videoSheet.getParent().getHeight()));
            videoSheet.setSize(cameraPanel.getSize());

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fullscreen = new javax.swing.JFrame();
        cameraPanel1 = new javax.swing.JPanel();
        exitFullscreenButton = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        helpframe = new javax.swing.JFrame();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        infoPanel2 = new javax.swing.JPanel();
        cameraPanel = new javax.swing.JPanel();
        fullscreenButton = new javax.swing.JButton();
        controlPanel = new javax.swing.JPanel();
        lightPanel = new javax.swing.JPanel();
        lightSeperator = new javax.swing.JSeparator();
        LightPowerHeader = new javax.swing.JLabel();
        lightIndicator = new javax.swing.JLabel();
        LightIndicator = new javax.swing.JProgressBar();
        lightBrightnessSlider = new javax.swing.JSlider();
        FilterPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        SharpeningFilter = new javax.swing.JToggleButton();
        filterSeperator = new javax.swing.JSeparator();
        pictureCapturePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        captureSeperator = new javax.swing.JSeparator();
        captureButton = new javax.swing.JButton();
        connectPanel = new javax.swing.JPanel();
        connectLabel = new javax.swing.JLabel();
        connectButton = new javax.swing.JToggleButton();
        connectSeparator = new javax.swing.JSeparator();
        recordPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        recordSeparator = new javax.swing.JSeparator();
        recordToggleButton = new javax.swing.JToggleButton();
        cameraAnglePanel = new javax.swing.JPanel();
        cameraAngles = new javax.swing.JLabel();
        pitchLabel = new javax.swing.JLabel();
        YawLabel = new javax.swing.JLabel();
        rollLabel = new javax.swing.JLabel();
        modelPanel = new javax.swing.JPanel();
        cameraHousingPanel = new javax.swing.JPanel();
        cameraHousingLabel = new javax.swing.JLabel();
        tempCameraLabel = new javax.swing.JLabel();
        tempTopLabel = new javax.swing.JLabel();
        dewPointLabel = new javax.swing.JLabel();
        humidityLabel = new javax.swing.JLabel();
        tempOutsideLabel = new javax.swing.JLabel();
        generalPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pressureLabe = new javax.swing.JLabel();
        altitudeLabel = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuTools = new javax.swing.JMenu();
        jMenuItemAltitude = new javax.swing.JMenuItem();
        jMenuItemresetPicture = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuAbout = new javax.swing.JMenuItem();

        fullscreen.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        fullscreen.setFocusTraversalPolicyProvider(true);
        fullscreen.setLocationByPlatform(true);
        fullscreen.setUndecorated(true);
        fullscreen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fullscreenKeyPressed(evt);
            }
        });

        cameraPanel1.setBackground(new java.awt.Color(0, 0, 0));
        cameraPanel1.setForeground(new java.awt.Color(255, 255, 255));
        cameraPanel1.setToolTipText("");
        cameraPanel1.setMinimumSize(new java.awt.Dimension(450, 320));
        cameraPanel1.setPreferredSize(new java.awt.Dimension(718, 580));

        exitFullscreenButton.setBackground(new java.awt.Color(0, 0, 0));
        exitFullscreenButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/exitfullscreenbutton.png"))); // NOI18N
        exitFullscreenButton.setContentAreaFilled(false);
        exitFullscreenButton.setMinimumSize(new java.awt.Dimension(30, 30));
        exitFullscreenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitFullscreenButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cameraPanel1Layout = new javax.swing.GroupLayout(cameraPanel1);
        cameraPanel1.setLayout(cameraPanel1Layout);
        cameraPanel1Layout.setHorizontalGroup(
            cameraPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cameraPanel1Layout.createSequentialGroup()
                .addGap(0, 708, Short.MAX_VALUE)
                .addComponent(exitFullscreenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        cameraPanel1Layout.setVerticalGroup(
            cameraPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cameraPanel1Layout.createSequentialGroup()
                .addGap(0, 572, Short.MAX_VALUE)
                .addComponent(exitFullscreenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout fullscreenLayout = new javax.swing.GroupLayout(fullscreen.getContentPane());
        fullscreen.getContentPane().setLayout(fullscreenLayout);
        fullscreenLayout.setHorizontalGroup(
            fullscreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cameraPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
        );
        fullscreenLayout.setVerticalGroup(
            fullscreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cameraPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );

        helpframe.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        helpframe.setTitle("About");
        helpframe.setType(java.awt.Window.Type.POPUP);

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("<html>This code is for the Master thesis named \"Borehole image system\". The purpose is to build a prototype  camera module that is set to work in an antartic enviroment and also act as a multi-sensor platform.  <br/><br/>The system consists of a Raspberry Pi set in the camera module that is connected an Arduino microcontroller and several sensors. The external computer set on the surface vessel is connected to an Arduino controlling user input ovver serial communication and to the RBPi using Ethernet. It will present and save data in addition to handle user commands.<br/><br/> Created by Kristian S. Homdrom<html>");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout helpframeLayout = new javax.swing.GroupLayout(helpframe.getContentPane());
        helpframe.getContentPane().setLayout(helpframeLayout);
        helpframeLayout.setHorizontalGroup(
            helpframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpframeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(helpframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(helpframeLayout.createSequentialGroup()
                        .addGap(0, 160, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(0, 161, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        helpframeLayout.setVerticalGroup(
            helpframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpframeLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BoreHole Image System\n");
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(600, 480));

        infoPanel2.setBackground(new java.awt.Color(28, 28, 28));
        infoPanel2.setForeground(new java.awt.Color(255, 255, 255));
        infoPanel2.setToolTipText("");
        infoPanel2.setMinimumSize(new java.awt.Dimension(600, 480));
        infoPanel2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                infoPanel2ComponentHidden(evt);
            }
        });

        cameraPanel.setBackground(new java.awt.Color(0, 0, 0));
        cameraPanel.setForeground(new java.awt.Color(255, 255, 255));
        cameraPanel.setToolTipText("");
        cameraPanel.setAlignmentX(0.8F);
        cameraPanel.setAlignmentY(0.8F);
        cameraPanel.setMinimumSize(new java.awt.Dimension(450, 320));
        cameraPanel.setPreferredSize(new java.awt.Dimension(718, 580));

        fullscreenButton.setBackground(new java.awt.Color(0, 0, 0));
        fullscreenButton.setForeground(new java.awt.Color(255, 255, 255));
        fullscreenButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/Fullscreenbutton.png"))); // NOI18N
        fullscreenButton.setContentAreaFilled(false);
        fullscreenButton.setFocusable(false);
        fullscreenButton.setHideActionText(true);
        fullscreenButton.setName(""); // NOI18N
        fullscreenButton.setPreferredSize(new java.awt.Dimension(30, 30));
        fullscreenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullscreenButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cameraPanelLayout = new javax.swing.GroupLayout(cameraPanel);
        cameraPanel.setLayout(cameraPanelLayout);
        cameraPanelLayout.setHorizontalGroup(
            cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cameraPanelLayout.createSequentialGroup()
                .addGap(0, 768, Short.MAX_VALUE)
                .addComponent(fullscreenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        cameraPanelLayout.setVerticalGroup(
            cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cameraPanelLayout.createSequentialGroup()
                .addContainerGap(619, Short.MAX_VALUE)
                .addComponent(fullscreenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        controlPanel.setBackground(new java.awt.Color(28, 28, 28));
        controlPanel.setMinimumSize(new java.awt.Dimension(150, 140));
        controlPanel.setPreferredSize(new java.awt.Dimension(768, 190));

        lightPanel.setBackground(new java.awt.Color(28, 28, 28));

        lightSeperator.setBackground(new java.awt.Color(204, 204, 204));
        lightSeperator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        LightPowerHeader.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LightPowerHeader.setForeground(new java.awt.Color(255, 255, 255));
        LightPowerHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LightPowerHeader.setText("Light Brightness");

        lightIndicator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/lightIcon.png"))); // NOI18N

        LightIndicator.setForeground(new java.awt.Color(255, 255, 51));
        LightIndicator.setMaximum(1023);
        LightIndicator.setToolTipText("");

        lightBrightnessSlider.setBackground(new java.awt.Color(28, 28, 28));
        lightBrightnessSlider.setMaximum(1023);
        lightBrightnessSlider.setValue(0);

        FilterPanel1.setBackground(new java.awt.Color(28, 28, 28));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Sharpen Filter");

        SharpeningFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/Toggle_off.png"))); // NOI18N
        SharpeningFilter.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/Toggle_on.png"))); // NOI18N
        SharpeningFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SharpeningFilterActionPerformed(evt);
            }
        });

        filterSeperator.setBackground(new java.awt.Color(204, 204, 204));
        filterSeperator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout FilterPanel1Layout = new javax.swing.GroupLayout(FilterPanel1);
        FilterPanel1.setLayout(FilterPanel1Layout);
        FilterPanel1Layout.setHorizontalGroup(
            FilterPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FilterPanel1Layout.createSequentialGroup()
                .addGroup(FilterPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FilterPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(SharpeningFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FilterPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(FilterPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(FilterPanel1Layout.createSequentialGroup()
                    .addGap(110, 110, 110)
                    .addComponent(filterSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(18, Short.MAX_VALUE)))
        );
        FilterPanel1Layout.setVerticalGroup(
            FilterPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FilterPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SharpeningFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(FilterPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(FilterPanel1Layout.createSequentialGroup()
                    .addComponent(filterSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout lightPanelLayout = new javax.swing.GroupLayout(lightPanel);
        lightPanel.setLayout(lightPanelLayout);
        lightPanelLayout.setHorizontalGroup(
            lightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lightPanelLayout.createSequentialGroup()
                .addGroup(lightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lightPanelLayout.createSequentialGroup()
                        .addComponent(lightIndicator)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LightPowerHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lightPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(lightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LightIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lightBrightnessSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22)
                .addComponent(lightSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FilterPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );
        lightPanelLayout.setVerticalGroup(
            lightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lightSeperator)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lightPanelLayout.createSequentialGroup()
                .addGroup(lightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LightPowerHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lightIndicator))
                .addGap(22, 22, 22)
                .addComponent(lightBrightnessSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LightIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(FilterPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pictureCapturePanel.setBackground(new java.awt.Color(28, 28, 28));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Snapshot");

        captureSeperator.setBackground(new java.awt.Color(204, 204, 204));
        captureSeperator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        captureButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/capture1.png"))); // NOI18N
        captureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                captureButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pictureCapturePanelLayout = new javax.swing.GroupLayout(pictureCapturePanel);
        pictureCapturePanel.setLayout(pictureCapturePanelLayout);
        pictureCapturePanelLayout.setHorizontalGroup(
            pictureCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pictureCapturePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pictureCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(captureButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(pictureCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pictureCapturePanelLayout.createSequentialGroup()
                    .addGap(0, 91, Short.MAX_VALUE)
                    .addComponent(captureSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pictureCapturePanelLayout.setVerticalGroup(
            pictureCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pictureCapturePanelLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(captureButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pictureCapturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pictureCapturePanelLayout.createSequentialGroup()
                    .addComponent(captureSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        connectPanel.setBackground(new java.awt.Color(28, 28, 28));

        connectLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        connectLabel.setForeground(new java.awt.Color(255, 255, 255));
        connectLabel.setText("Connect");

        connectButton.setBackground(new java.awt.Color(28, 28, 28));
        connectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/Toggle_off.png"))); // NOI18N
        connectButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/Toggle_on.png"))); // NOI18N
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        connectSeparator.setBackground(new java.awt.Color(204, 204, 204));
        connectSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout connectPanelLayout = new javax.swing.GroupLayout(connectPanel);
        connectPanel.setLayout(connectPanelLayout);
        connectPanelLayout.setHorizontalGroup(
            connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectPanelLayout.createSequentialGroup()
                .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(connectLabel)
                    .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 61, Short.MAX_VALUE))
            .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, connectPanelLayout.createSequentialGroup()
                    .addContainerGap(78, Short.MAX_VALUE)
                    .addComponent(connectSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        connectPanelLayout.setVerticalGroup(
            connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectPanelLayout.createSequentialGroup()
                .addComponent(connectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 62, Short.MAX_VALUE))
            .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(connectSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
        );

        recordPanel.setBackground(new java.awt.Color(28, 28, 28));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Record");

        recordSeparator.setBackground(new java.awt.Color(204, 204, 204));
        recordSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        recordToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/startRecord.png"))); // NOI18N
        recordToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/borehole/gui/images/stopRecord.png"))); // NOI18N
        recordToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout recordPanelLayout = new javax.swing.GroupLayout(recordPanel);
        recordPanel.setLayout(recordPanelLayout);
        recordPanelLayout.setHorizontalGroup(
            recordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(recordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(recordToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(recordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, recordPanelLayout.createSequentialGroup()
                    .addContainerGap(95, Short.MAX_VALUE)
                    .addComponent(recordSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(21, 21, 21)))
        );
        recordPanelLayout.setVerticalGroup(
            recordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordPanelLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recordToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 89, Short.MAX_VALUE))
            .addGroup(recordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(recordPanelLayout.createSequentialGroup()
                    .addComponent(recordSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(recordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(controlPanelLayout.createSequentialGroup()
                    .addGap(366, 366, 366)
                    .addComponent(pictureCapturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(309, Short.MAX_VALUE)))
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(connectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(controlPanelLayout.createSequentialGroup()
                    .addComponent(pictureCapturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 31, Short.MAX_VALUE)))
        );

        cameraAnglePanel.setBackground(new java.awt.Color(40, 40, 40));

        cameraAngles.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cameraAngles.setForeground(new java.awt.Color(255, 255, 255));
        cameraAngles.setText("Camera angles");

        pitchLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pitchLabel.setForeground(new java.awt.Color(255, 255, 255));
        pitchLabel.setText("<html>Pitch angle:<br/>-3.34°");

        YawLabel.setBackground(new java.awt.Color(28, 28, 28));
        YawLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        YawLabel.setForeground(new java.awt.Color(255, 255, 255));
        YawLabel.setText("<html>Yaw angle:<br/>0°");

        rollLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rollLabel.setForeground(new java.awt.Color(255, 255, 255));
        rollLabel.setText("<html>Roll angle:<br/>0.84°");

        javax.swing.GroupLayout cameraAnglePanelLayout = new javax.swing.GroupLayout(cameraAnglePanel);
        cameraAnglePanel.setLayout(cameraAnglePanelLayout);
        cameraAnglePanelLayout.setHorizontalGroup(
            cameraAnglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cameraAnglePanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(cameraAnglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rollLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(YawLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraAngles)
                    .addComponent(pitchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        cameraAnglePanelLayout.setVerticalGroup(
            cameraAnglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cameraAnglePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cameraAngles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(YawLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pitchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rollLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        modelPanel.setBackground(new java.awt.Color(28, 28, 28));

        javax.swing.GroupLayout modelPanelLayout = new javax.swing.GroupLayout(modelPanel);
        modelPanel.setLayout(modelPanelLayout);
        modelPanelLayout.setHorizontalGroup(
            modelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        modelPanelLayout.setVerticalGroup(
            modelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
        );

        cameraHousingPanel.setBackground(new java.awt.Color(40, 40, 40));

        cameraHousingLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cameraHousingLabel.setForeground(new java.awt.Color(255, 255, 255));
        cameraHousingLabel.setText("Camera Housing");

        tempCameraLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tempCameraLabel.setForeground(new java.awt.Color(255, 255, 255));
        tempCameraLabel.setText("<html>Temp camera:<br/>20°C");

        tempTopLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tempTopLabel.setForeground(new java.awt.Color(255, 255, 255));
        tempTopLabel.setText("<html>Temp top:<br/>22°C");

        dewPointLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dewPointLabel.setForeground(new java.awt.Color(255, 255, 255));
        dewPointLabel.setText("<html>dewPoint:<br/>15°C");

        humidityLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        humidityLabel.setForeground(new java.awt.Color(255, 255, 255));
        humidityLabel.setText("<html>Humidity:<br/>1000%");

        tempOutsideLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tempOutsideLabel.setForeground(new java.awt.Color(255, 255, 255));
        tempOutsideLabel.setText("<html>Temp outside:<br/>-4°C");

        javax.swing.GroupLayout cameraHousingPanelLayout = new javax.swing.GroupLayout(cameraHousingPanel);
        cameraHousingPanel.setLayout(cameraHousingPanelLayout);
        cameraHousingPanelLayout.setHorizontalGroup(
            cameraHousingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cameraHousingPanelLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(cameraHousingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tempOutsideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dewPointLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tempCameraLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraHousingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tempTopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(humidityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        cameraHousingPanelLayout.setVerticalGroup(
            cameraHousingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cameraHousingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cameraHousingLabel)
                .addGap(2, 2, 2)
                .addComponent(tempCameraLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tempTopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(humidityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dewPointLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tempOutsideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        generalPanel.setBackground(new java.awt.Color(40, 40, 40));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("General info");

        pressureLabe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pressureLabe.setForeground(new java.awt.Color(255, 255, 255));
        pressureLabe.setText("<html>Pressure:<br/>1007Pa");

        altitudeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        altitudeLabel.setForeground(new java.awt.Color(255, 255, 255));
        altitudeLabel.setText("<html>Altitude:<br/>10m");

        javax.swing.GroupLayout generalPanelLayout = new javax.swing.GroupLayout(generalPanel);
        generalPanel.setLayout(generalPanelLayout);
        generalPanelLayout.setHorizontalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(altitudeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pressureLabe, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        generalPanelLayout.setVerticalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pressureLabe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(altitudeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout infoPanel2Layout = new javax.swing.GroupLayout(infoPanel2);
        infoPanel2.setLayout(infoPanel2Layout);
        infoPanel2Layout.setHorizontalGroup(
            infoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanel2Layout.createSequentialGroup()
                .addGroup(infoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cameraPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                    .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(infoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanel2Layout.createSequentialGroup()
                        .addGroup(infoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cameraAnglePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(generalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cameraHousingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(modelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        infoPanel2Layout.setVerticalGroup(
            infoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanel2Layout.createSequentialGroup()
                .addComponent(cameraPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
            .addGroup(infoPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(infoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cameraHousingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(infoPanel2Layout.createSequentialGroup()
                        .addComponent(cameraAnglePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(generalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(modelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenuFile.setText("File");

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar.add(jMenuFile);

        jMenuTools.setText("Tools");

        jMenuItemAltitude.setText("SetAltitudePressure");
        jMenuItemAltitude.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jMenuItemAltitudeInputMethodTextChanged(evt);
            }
        });
        jMenuItemAltitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAltitudeActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemAltitude);

        jMenuItemresetPicture.setText("ResetPicture");
        jMenuItemresetPicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemresetPictureActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemresetPicture);

        jMenuBar.add(jMenuTools);

        jMenuHelp.setText("Help");
        jMenuHelp.setToolTipText("");

        jMenuAbout.setText("About");
        jMenuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAboutActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuAbout);

        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fullscreenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullscreenButtonActionPerformed
        Rectangle maximumWindowBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int width = (int) maximumWindowBounds.getWidth();
        int height = (int) maximumWindowBounds.getHeight();
        fullscreen.setLocationRelativeTo(this);
        //Skal være false
        this.setVisible(false);
        fullscreen.setExtendedState(MAXIMIZED_BOTH);
        fullscreen.setUndecorated(true);
        fullscreen.setVisible(true);
        exitFullscreenButton.setBounds(width - exitFullscreenButton.getWidth(), height - exitFullscreenButton.getHeight() - 25, 30, 30);
    }//GEN-LAST:event_fullscreenButtonActionPerformed

    private void exitFullscreenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitFullscreenButtonActionPerformed
        fullscreen.setVisible(false); //endret fra false
        fullscreen.dispose();
        this.setVisible(true);
    }//GEN-LAST:event_exitFullscreenButtonActionPerformed

    private void fullscreenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fullscreenKeyPressed
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            fullscreen.dispose();
        }
        System.out.println(key);
    }//GEN-LAST:event_fullscreenKeyPressed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed

        data.setStopByte((byte) 0);
        System.out.println("exit");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(DrillFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (data.returnStopByte() == (byte) 0) {
            System.exit(0);
        }

    }//GEN-LAST:event_jMenuItemExitActionPerformed


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        helpframe.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAboutActionPerformed
        helpframe.setVisible(true);
        helpframe.setSize(helpframe.getPreferredSize());
        helpframe.setLocation(this.getLocation().x, this.getLocation().y);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuAboutActionPerformed

    private void infoPanel2ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_infoPanel2ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_infoPanel2ComponentHidden

    private void SharpeningFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SharpeningFilterActionPerformed

        if (SharpeningFilter.isSelected()) {
            data.setFiltering(true);
        } else {
            data.setFiltering(false);
        }
    }//GEN-LAST:event_SharpeningFilterActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed

        //Add conenction from connect 
        if (connectButton.isSelected()) {
            String ip = (String) JOptionPane.showInputDialog(this, "Enter IP", "Connection", JOptionPane.PLAIN_MESSAGE, null, null, data.getDefaultIP());
            try {
                client.connect(ip);
                data.setStopByte((byte) 0);
                Thread.sleep(100);
            } catch (IOException | InterruptedException ex) {
                JOptionPane.showMessageDialog(this,
                        "Connection failed.",
                        "Conncetion error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            data.setStopByte((byte) 2);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DrillFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (client.isConnected()) {

                try {
                    Thread.sleep(100);
                    client.disconnect();
                    JOptionPane.showMessageDialog(this,
                            "Successfully disconnected.",
                            "Disconnected",
                            JOptionPane.PLAIN_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Failed to disconnect.",
                            "Disconnect error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DrillFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Not connected to server, connect before disconnecting");
            }
        }

    }//GEN-LAST:event_connectButtonActionPerformed

    private void captureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_captureButtonActionPerformed
        // capture a image to be stored as JPG
        data.setCapture(true);
    }//GEN-LAST:event_captureButtonActionPerformed

    private void recordToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordToggleButtonActionPerformed
        // TODO add your handling code here:
        if (recordToggleButton.isSelected()) {
            data.setRecording(true);
        } else {
            data.setRecording(false);
        }
    }//GEN-LAST:event_recordToggleButtonActionPerformed

    private void jMenuItemAltitudeInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jMenuItemAltitudeInputMethodTextChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jMenuItemAltitudeInputMethodTextChanged

    private void jMenuItemAltitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAltitudeActionPerformed
        // TODO add your handling code here:
        JFrame f = new JFrame("Set hPa");
        //submit button
        JButton b = new JButton("Submit");
        b.setBounds(100, 100, 140, 40);

        JLabel labelInfo = new JLabel();
        labelInfo.setText("Set location's pressure (hPa) at sea level.");
        labelInfo.setBounds(1, 10, 250, 20);

        //enter name label
        JLabel label = new JLabel();
        label.setText("Enter hPa :");
        label.setBounds(10, 10, 100, 100);
        //empty label which will show event after button clicked
        JLabel label1 = new JLabel();
        label1.setBounds(10, 110, 200, 100);
        //textfield to enter name
        JTextField textfield = new JTextField();
        textfield.setBounds(110, 50, 130, 30);
        //add to frame
        f.add(labelInfo);
        f.add(label1);
        f.add(textfield);
        f.add(label);
        f.add(b);
        f.setSize(300, 300);
        f.setLayout(null);
        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String text = textfield.getText().toLowerCase();
                try {
                    int number = Integer.parseInt(text);
                    if (number < 2000 && number > 200) {
                        label1.setText("Set new value " + number);
                        data.setPressureSeaLevel(number);
                    }
                } catch (Exception e) {
                    label1.setText("not a valid input use int values, no signs or char ");
                }

            }
        });

    }//GEN-LAST:event_jMenuItemAltitudeActionPerformed

    private void jMenuItemresetPictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemresetPictureActionPerformed
        // TODO add your handling code here:
        data.setCameraAngle(0);
    }//GEN-LAST:event_jMenuItemresetPictureActionPerformed

    Action exitFullscreenAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            exitFullscreenButton.doClick();
        }
    };


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FilterPanel1;
    private javax.swing.JProgressBar LightIndicator;
    private javax.swing.JLabel LightPowerHeader;
    private javax.swing.JToggleButton SharpeningFilter;
    private javax.swing.JLabel YawLabel;
    private javax.swing.JLabel altitudeLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel cameraAnglePanel;
    private javax.swing.JLabel cameraAngles;
    private javax.swing.JLabel cameraHousingLabel;
    private javax.swing.JPanel cameraHousingPanel;
    private javax.swing.JPanel cameraPanel;
    private javax.swing.JPanel cameraPanel1;
    private javax.swing.JButton captureButton;
    private javax.swing.JSeparator captureSeperator;
    private javax.swing.JToggleButton connectButton;
    private javax.swing.JLabel connectLabel;
    private javax.swing.JPanel connectPanel;
    private javax.swing.JSeparator connectSeparator;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JLabel dewPointLabel;
    private javax.swing.JButton exitFullscreenButton;
    private javax.swing.JSeparator filterSeperator;
    private javax.swing.JFrame fullscreen;
    private javax.swing.JButton fullscreenButton;
    private javax.swing.JPanel generalPanel;
    private javax.swing.JFrame helpframe;
    private javax.swing.JLabel humidityLabel;
    private javax.swing.JPanel infoPanel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuAbout;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAltitude;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemresetPicture;
    private javax.swing.JMenu jMenuTools;
    private javax.swing.JSlider lightBrightnessSlider;
    private javax.swing.JLabel lightIndicator;
    private javax.swing.JPanel lightPanel;
    private javax.swing.JSeparator lightSeperator;
    private javax.swing.JPanel modelPanel;
    private javax.swing.JPanel pictureCapturePanel;
    private javax.swing.JLabel pitchLabel;
    private javax.swing.JLabel pressureLabe;
    private javax.swing.JPanel recordPanel;
    private javax.swing.JSeparator recordSeparator;
    private javax.swing.JToggleButton recordToggleButton;
    private javax.swing.JLabel rollLabel;
    private javax.swing.JLabel tempCameraLabel;
    private javax.swing.JLabel tempOutsideLabel;
    private javax.swing.JLabel tempTopLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        try {
            videoImage = ImageIO.read(new File("src/borehole/gui/Images/bilde.jpg"));
            //videoImage = rotateImageByDegrees(videoImage, 90);
        } catch (IOException ex) {
            Logger.getLogger(BoreHoleGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.showImage(videoImage);
        this.setVisible(true); //To change body of generated methods, choose Tools | Templates.    
        //
        try {

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        this.showImage(videoImage);
    }

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
        g2d.drawImage(img, 0, 0, this);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();

        return rotated;
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println(data.getPitchAngle());
        BufferedImage img = null;
        try {
            img = rotateImageByDegrees(data.getVideoImage(), data.getCameraAngle());

        } catch (Exception e) {
            //Exceptiuion is made, doess not affect performance? 
        }
        this.showImage(img);
        pressureLabe.setText("<html>Pressure:<br/>" + data.getPressure() + "Pa");
        altitudeLabel.setText("<html>Altitude:<br/>" + data.getAltitude() + "m");
        humidityLabel.setText("<html>Humidity:<br/>" + data.getHumidity() + "%");

        tempOutsideLabel.setText("<html>Temp outside:<br/>" + data.getTempOutside() + "°C");
        tempCameraLabel.setText("<html>Temp camera:<br/>" + data.getTempCamera() + "°C");
        tempTopLabel.setText("<html>Temp top:<br/>" + data.getTempTop() + "°C");
        dewPointLabel.setText("<html>DewPoint:<br/>" + data.getDewPoint() + "°C");

        rollLabel.setText("<html>Roll angle:<br/>" + data.getRollAngle() + "°");
        pitchLabel.setText("<html>Pitch angle:<br/>" + data.getPitchAngle() + "°");
        YawLabel.setText("<html>Yaw angle:<br/>" + data.getYawAngle() + "°");
        int value = (int) data.getLightValue();
        LightIndicator.setValue(1023 - value);
        try {
            //If slider aboe value of 10, turn on light indicator
            if (value < 1010) {
                lightIndicator.setIcon(new ImageIcon(ImageIO.read(new File("src/borehole/gui/Images/lightIconOn.png"))));

            } else {
                lightIndicator.setIcon(new ImageIcon(ImageIO.read(new File("src/borehole/gui/Images/lightIcon.png"))));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
