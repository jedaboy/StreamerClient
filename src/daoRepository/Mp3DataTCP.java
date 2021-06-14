/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoRepository;

import entities.MusicData;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

/**
 *
 * @author jedaf
 */
public interface Mp3DataTCP {

  
    public ImageIcon enviaIdTCP() throws SQLException, UnsupportedAudioFileException, IOException;
    public void interrompeStreamTCP()throws SQLException, UnsupportedAudioFileException, IOException;
    public InputStream recebeInputStream2TCP() throws SQLException, UnsupportedAudioFileException, IOException;
    public long recebeFrameRateTCP() throws SQLException, UnsupportedAudioFileException, IOException;
    public ImageIcon recebeImagemTCP() throws SQLException, UnsupportedAudioFileException, IOException;
    public void setId(int userId);
    public int getId();
}
