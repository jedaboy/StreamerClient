package views;

/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
import daoRepository.DaoFactory;

import daoRepository.UsuarioTCP;
import entities.MusicData;
import entities.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jedaf
 */
public class MiniTela extends javax.swing.JFrame {

    private long frameCount;
    private double duration;
    private AudioFormat format;
    private Clip clip;
    boolean botaoNext = false;
    private boolean playing = false;

    private Timer playTimer;
    private boolean ignoreStateChange = false;
    //Mp3DataDao mp3DataDao = DaoFactory.createMp3DataDao();
    ImageIcon image;

    MusicData musicData;
    /**
     * Creates new form TestFrame
     */
    public MiniTela(MusicData musicData) throws SQLException , LineUnavailableException, IOException, UnsupportedAudioFileException {
      
        playTimer = musicData.getPlayTimer(); 
        playing = musicData.getPlaying();
        clip = musicData.getClip();    
        format = musicData.getFormat();
        duration = musicData.getDuration();
        image = musicData.getImage();
        frameCount = musicData.getFrameCount();

            
        initComponents();
        labelPic.setText("");
      
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent evt) {
                if (evt.getType().equals(LineEvent.Type.STOP)
                        || evt.getType().equals(LineEvent.Type.CLOSE)) {
                    action.setText("Play");
                    playing = false;
                    playTimer.stop();
                    updateState();
                }
            }
        });

        playTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                updateState();
            }
        });
         

            //responsavel por atualizar o timer
          
             labelPic.setIcon(image);

            // labelFileName.setText("Playing File: " + audioFilePath); //not important
            System.out.print((int) clip.getMicrosecondLength() / 1_000_000);
            // clip lenght in seconds? this feature will set the slider lenght to the same length of the audio clip
            //clip.start();
            action.setText("Stop");
            playing = true;
            playTimer.start();
            
             addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                  
                        dispose();
                        musicData.clipStop();
                    
                   
                }  catch (Exception ex) {
                    Logger.getLogger(TelaDashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        
    }

    public MiniTela() throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {

        initComponents();

        clipLoad();
        labelPic.setText("");

        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent evt) {
                if (evt.getType().equals(LineEvent.Type.STOP)
                        || evt.getType().equals(LineEvent.Type.CLOSE)) {
                    action.setText("Play");
                    playing = false;
                    playTimer.stop();
                    updateState();
                }
            }
        });
       
        playTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                updateState();
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                  
                        dispose();
                        musicData.clipStop();
                    
                   
                }  catch (Exception ex) {
                    Logger.getLogger(TelaDashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        slider = new javax.swing.JSlider();
        action = new javax.swing.JButton();
        labelTimeCounter = new javax.swing.JLabel();
        totalDuration = new javax.swing.JLabel();
        labelPic1 = new javax.swing.JLabel();
        labelPic = new javax.swing.JLabel();

        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        slider.setValue(0);
        slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderStateChanged(evt);
            }
        });
        getContentPane().add(slider, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 237, -1));

        action.setText("Play");
        action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actionMouseClicked(evt);
            }
        });
        getContentPane().add(action, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, -1, -1));

        labelTimeCounter.setText("Tempo: ");
        getContentPane().add(labelTimeCounter, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, 23));

        totalDuration.setText("Total duration: ");
        totalDuration.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                totalDurationAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        getContentPane().add(totalDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, -1, 26));
        getContentPane().add(labelPic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 0, 360, 290));

        labelPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo_1.4.jpg"))); // NOI18N
        getContentPane().add(labelPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 480, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void totalDurationAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_totalDurationAncestorAdded

        int time = (int) duration;
        //Mp3DataDao mp3DataDao = DaoFactory.createMp3DataDao();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos = converteSeg(time);
        //  totalFrameDuration.setText("Total frame duration:" + frameCount);
        totalDuration.setText(baos.toString());

    }//GEN-LAST:event_totalDurationAncestorAdded

    private void actionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actionMouseClicked
        // labelPic.setIcon(image);
        //carrega imagemIcon com blob

        //Mp3DataDao mp3DataDao = DaoFactory.createMp3DataDao();
        if (!playing) {

            //responsavel por atualizar o timer
            int frame = getDesiredFrame();
            if (frame >= frameCount) {
                frame = 0;
            }
            clip.setFramePosition(frame);

            // labelFileName.setText("Playing File: " + audioFilePath); //not important
            System.out.print((int) clip.getMicrosecondLength() / 1_000_000);
            // clip lenght in seconds? this feature will set the slider lenght to the same length of the audio clip
            clip.start();
            action.setText("Stop");
            playing = true;
            playTimer.start();
        } else {

            clip.stop();
            action.setText("Play");
            playing = false;
            playTimer.stop();
        }


    }//GEN-LAST:event_actionMouseClicked

    private void sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderStateChanged

        //---------------------Slider controler-----------------------------------
        Timer delayedUpdate = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                int frame = getDesiredFrame();
                clip.setFramePosition(frame);

                int time = (int) getCurrentTime();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos = converteSeg(time);
                labelTimeCounter.setText(baos.toString());

                //   currentFrame.setText("Current frame: " + frame);
                //  currentDuration.setText("Current duration: " + time);
            }
        });

        delayedUpdate.setRepeats(false);

        if (ignoreStateChange) {
            return;
        }
        delayedUpdate.restart();


    }//GEN-LAST:event_sliderStateChanged

    public void updateState() {
        ignoreStateChange = true;
        int frame = clip.getFramePosition();
        int progress = (int) (((double) frame / (double) frameCount) * 100);
        slider.setValue(progress);
//        currentFrame.setText("Current frame: " + getDesiredFrame());

        int time = (int) getCurrentTime();
        int valor = time;
        int horas = valor / 3600;
        int restoHoras = valor % 3600;
        int minutos = restoHoras / 60;
        int restoMinutos = restoHoras % 60;
        int segundos = restoMinutos;

        //  System.out.printf(String.valueOf(slider.getValue()));
        // System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
// Tell Java to use your special stream
        System.setOut(ps);
// Print some output: goes to your special stream
        System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);
// Put things back
        System.out.flush();
        System.setOut(old);
// Show what happened
        labelTimeCounter.setText(baos.toString());
        //  currentDuration.setText("Current duration: " + getCurrentTime());
        ignoreStateChange = false;
    }

    public double getCurrentTime() {
        int currentFrame = clip.getFramePosition();
        double time = (double) currentFrame / format.getFrameRate();
        return time;
    }

    public int getDesiredFrame() {
        int progress = slider.getValue();
        double frame = ((double) frameCount * ((double) progress / 100.0));
        return (int) frame;
    }

    //Em Desuso 
    public void clipLoad() throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {
       /* AudioInputStream converted = null;
        try {

            
            
            Mp3DataDao mp3DataDao = DaoFactory.createMp3DataDao();
    
           // image = new ImageIcon(mp3DataDao.recebeImagemBlob());
            //pega a musica do db
            InputStream iS = mp3DataDao.recebeInputStream2();
            //converte mp3 em wav apaga a copia da memoria rom deixando a musica apenas no buffer 
            byte[] byteArray = convert.mp3toWav(iS);

            //passamos o array de bytes para um formato aceito pela classe AudioInpuntStream e depois pegamos o cabeçalho para montar o audio
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            long frameRate = mp3DataDao.recebeFrameRate();
            //montamos o audio
            converted = new AudioInputStream(bis, convert.getAudioFormat(), frameRate);
            //print que retorna se o arquivo wav foi apagado ou nao
            System.out.println("AAAAAAAAAAAAA"+convert.getBool());
          
            format = converted.getFormat();
            frameCount = converted.getFrameLength();
            duration = ((double) frameCount) / format.getFrameRate();
            clip = AudioSystem.getClip();
            clip.open(converted);
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }*/
    }

    public ByteArrayOutputStream converteSeg(int time) {
        int valor = time;
        int horas = valor / 3600;
        int restoHoras = valor % 3600;
        int minutos = restoHoras / 60;
        int restoMinutos = restoHoras % 60;
        int segundos = restoMinutos;

        System.out.printf(String.valueOf(slider.getValue()));
        System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
// Tell Java to use your special stream
        System.setOut(ps);
// Print some output: goes to your special stream
        System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);
// Put things back
        System.out.flush();
        System.setOut(old);

        return baos;
    }

    public void setTotalDuration() {
        int time = (int) duration;
        //Mp3DataDao mp3DataDao = DaoFactory.createMp3DataDao();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos = converteSeg(time);
        //  totalFrameDuration.setText("Total frame duration:" + frameCount);
        totalDuration.setText(baos.toString());

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton action;
    private javax.swing.JLabel labelPic;
    private javax.swing.JLabel labelPic1;
    private javax.swing.JLabel labelTimeCounter;
    private javax.swing.JSlider slider;
    private javax.swing.JLabel totalDuration;
    // End of variables declaration//GEN-END:variables
}
