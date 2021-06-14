/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package daoRepository.impl;

import java.sql.Connection;
import dbRepository.DB;
import daoRepository.Mp3DataTCP;
import entities.Mensagem;
import entities.MusicData;
import entities.Status;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Mp3DataClientTCP implements Mp3DataTCP {

    private Connection connection;
   
    private int userId;

    private Socket skt = null;

    public Mp3DataClientTCP(Socket skt) {
        this.skt = skt;
    }

    public int getId() {

        return userId;
    }

    public void setId(int userId) {

        this.userId = userId;
    }

    @Override
    public ImageIcon enviaIdTCP() throws SQLException,
            UnsupportedAudioFileException, IOException {
        try {
            Scanner sc = new Scanner(System.in);
            // skt = new Socket("3.134.125.175", 19224);

            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = new ObjectOutputStream(skt.getOutputStream());
            oIS = new ObjectInputStream(skt.getInputStream());
            
            System.out.println("Enviando Mensagem");

            String msg = "Envia_id " + userId;
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("STREAM");
            m.setStatus(Status.STREAM_ID);
            m.setParam("Musica_id", userId);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer
            System.out.println("Tier 1: enviaId");

            ImageIcon image;

            Mensagem r = (Mensagem) oIS.readObject();
            image = (ImageIcon) r.getParam("Image");

            return image;

            //return true;
        } catch (Exception ex) {
            System.out.println("TCP conection error: enviaIdTCP");
            Logger.getLogger(Mp3DataClientTCP.class.getName()).log(Level.SEVERE, null, ex);
            // return false;
        }
        return null;
    }

    @Override
    public void interrompeStreamTCP() throws SQLException,
            UnsupportedAudioFileException, IOException {
        try {
            Scanner sc = new Scanner(System.in);
            // skt = new Socket("3.134.125.175", 19224);

            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = new ObjectOutputStream(skt.getOutputStream());

            System.out.println("Interrupção");

            String msg = "Interrupção: musica selecionada -   " + userId;
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("STREAM");
            m.setStatus(Status.STREAM_END);
            m.setParam("Musica_id", userId);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer
            System.out.println("Tier 1: interrompe");

            //return true;
        } catch (Exception ex) {
            System.out.println("TCP conection error: interrompeIdTCP");
            Logger.getLogger(Mp3DataClientTCP.class.getName()).log(Level.SEVERE, null, ex);
            // return false;
        }
    }

    @Override
    public InputStream recebeInputStream2TCP() throws SQLException, UnsupportedAudioFileException, IOException {

        ResultSet rs = null;
        PreparedStatement st = null;
        String query = "SELECT * FROM blobb WHERE ID = ?";

        st = connection.prepareStatement(query);
        st.setInt(1, userId);
        rs = st.executeQuery();

        rs.next();
        InputStream bS = rs.getBinaryStream("data_music");
        long fr = rs.getLong("frame_rate");
        InputStream is = new BufferedInputStream(rs.getBinaryStream("imagem_capa"));
        Image image = ImageIO.read(is);
        ImageIcon imageIc = new ImageIcon(image);
        MusicData md = new MusicData(rs.getBinaryStream("data_music"), rs.getLong("frame_rate"), imageIc);

        return bS;

    }

    @Override
    public long recebeFrameRateTCP() throws SQLException, UnsupportedAudioFileException, IOException {
        ImageIcon image;
        ResultSet rs = null;
        PreparedStatement st = null;
        String query = "SELECT frame_rate FROM blobb WHERE ID = ?";

        st = connection.prepareStatement(query);
        st.setInt(1, userId);
        rs = st.executeQuery();
        rs.next();

        long bS = rs.getLong("frame_rate");

        return bS;
    }

    @Override
    public ImageIcon recebeImagemTCP() throws SQLException, UnsupportedAudioFileException, IOException {

        return null;

    }

}
