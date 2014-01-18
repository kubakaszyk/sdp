package vision.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import vision.PitchConstants;
import vision.Position;
import vision.VideoStream;
import vision.interfaces.VideoReceiver;

@SuppressWarnings("serial")
public class VisionGUI extends JFrame implements VideoReceiver {
        private final int videoWidth;
        private final int videoHeight;

        // Pitch dimension selector variables
        private boolean selectionActive = false;
        private Point anchor;
        private int a;
        private int b;
        private int c;
        private int d;

        // Stored to only have rendering happen in one place
        private BufferedImage frame;
        private int fps;
        private int frameCounter;
        private BufferedImage debugOverlay;

        private final JPanel videoDisplay = new JPanel();
        private final WindowAdapter windowAdapter = new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                        dispose();

                        System.exit(0);
                }
        };

        public VisionGUI(final int videoWidth, final int videoHeight, final VideoStream vStream) {

                super("Vision");
                this.videoWidth = videoWidth;
                this.videoHeight = videoHeight;


                Container contentPane = this.getContentPane();

                Dimension videoSize = new Dimension(videoWidth, videoHeight);
                BufferedImage blankInitialiser = new BufferedImage(videoWidth,
                                videoHeight, BufferedImage.TYPE_INT_RGB);
                getContentPane().setLayout(null);
                videoDisplay.setLocation(0, 0);
                this.videoDisplay.setMinimumSize(videoSize);
                this.videoDisplay.setSize(videoSize);
                contentPane.add(videoDisplay);

                this.setVisible(true);
                
                this.getGraphics().drawImage(blankInitialiser, 0, 0, null);
                Dimension frameSize = new Dimension(videoWidth, videoHeight);
                contentPane.setSize(frameSize);
                this.setSize(frameSize.width + 8, frameSize.height + 30);
                // Wait for size to actually be set before setting resizable to false.
                try {
                        Thread.sleep(200);
                } catch (InterruptedException e1) {
                        e1.printStackTrace();
                }
                this.setResizable(false);
                videoDisplay.setFocusable(true);
                this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        @Override
        public void sendFrame(BufferedImage frame, int fps, int frameCounter) {
                this.frame = frame;
                this.fps = fps;
                this.frameCounter = frameCounter;
        }


}