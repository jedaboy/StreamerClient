package daoRepository;

import entities.Musica;
import entities.Usuario;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public interface MusicaTCP {

    public ImageIcon enviaIdTCP(int cont) throws SQLException, UnsupportedAudioFileException, IOException;

    List<Musica> listarMusicasTCP();

    List<Musica> recomendacoesTCP(Usuario usuario);

    List<Musica> musicasFavoritasNaoAvaliadasTCP(Usuario usuario);

    //List<Musica> musicasNaoAvaliadas(Usuario usuario);
    public List<Integer> musicasFavoritasJaAvaliadasTCP(Usuario usuario);

    Musica getMusicaByIdTCP(Integer id);

    void avaliarMusicaTCP(Usuario usuario, Musica musica, Integer nota);

    public void setId(int userId);

    public int getId();

    public void interrompeStreamTCP() throws SQLException,
            UnsupportedAudioFileException, IOException;
}
