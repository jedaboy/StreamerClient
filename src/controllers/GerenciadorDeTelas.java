/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package controllers;

import daoRepository.UsuarioTCP;
import entities.MusicData;
import views.TelaCadastro;
import views.TelaDashBoard;
import views.TelaLogin;
import views.TelaGenerosFavoritos;
import views.TelaAvaliacaoMusica;
import entities.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import views.MiniTela;

/**
 *
 * @author jedaboy
 */
public class GerenciadorDeTelas {

    //secção de continuidade
    private MiniTela miniTela;
    private boolean playing = false;

    MusicData musicData;

    //variavel que determina o usuario em questão
    private Usuario usuario;
    private UsuarioTCP usuarioTCP;
    //variavel que determina qual lista será exibida para o usuario avaliar
    private boolean recomendacao;

    public GerenciadorDeTelas() {
    }

    public GerenciadorDeTelas(MusicData musicData) {

        this.musicData = musicData;

    }
    //construcao das telas
    TelaDashBoard dashTela;
    TelaLogin logTela;
    TelaCadastro cadTela;
    TelaGenerosFavoritos GenTela;
    TelaAvaliacaoMusica avTela;
    TelaAvaliacaoMusica avTelaCont;
   
    
    public void setUsuAtual(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuAtual() {
        return usuario;

    }
    
    public void setUsuTCPAtual(UsuarioTCP usuarioTCP) {
        this.usuarioTCP = usuarioTCP;
    }

    public UsuarioTCP getUsuTCPAtual() {
        return usuarioTCP;

    }

    public void setMiniTela(MiniTela miniTela, boolean playing) {
        this.miniTela = miniTela;
        this.playing = playing;

    }

    public MiniTela getMiniTela() {
        return miniTela;

    }

    public void setRecomendacao(boolean recomendacao) {
        this.recomendacao = recomendacao;
    }

    public boolean getRecomendacao() {
        return recomendacao;

    }

    public void chamaTelaLogin() {
        logTela = new TelaLogin();
        if (playing) {
            miniTela.dispose();
        }
        logTela.setVisible(true);
    }

    public void chamaTelaDashboard() {
        //esta tela é chamada após o login 
         if (usuario == null) {
                    System.out.println("Usuario Null");
                }

                if (usuarioTCP == null) {
                    System.out.println("Conexão Null TELAS");
                }

        dashTela = new TelaDashBoard(usuario, usuarioTCP);
        if (playing) {
            dashTela.setMiniTela(miniTela, playing);
            dashTela.setDataAudio(musicData);

        }
        //pelo fato de estar passando o "usuario" atual pelo construtor este metodo se torna desnecessario
        //dashTela.setUsuAtual(usuario);
        dashTela.setVisible(true);

    }

    public void chamaTelaCadastro() {
        cadTela = new TelaCadastro();
        if (playing) {
            miniTela.dispose();
        }
        cadTela.setVisible(true);
    }

    public void chamaTelaGenero() {
       //essa classe e  chamada após dashboard tela selecionar o botao generos favoritos, daqui o usuario atual é passado para a classe generos favoritos
       GenTela = new TelaGenerosFavoritos(usuarioTCP);
        if (playing) {
            miniTela.dispose();
        }
       GenTela.setUsuAtual(usuario);
       GenTela.setVisible(true);
    }

    public void chamaTelaAvaliar() {

            if (!playing) {
                try {
                    avTela = new TelaAvaliacaoMusica();
                    avTela.setRecomendacao(recomendacao);
                    avTela.setUsuAtual(usuario);
                    avTela.setVisible(true);
                    //  program = new S();
                } catch (IOException ex) {
                    Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                try {
                    avTelaCont = new TelaAvaliacaoMusica(musicData);
                    avTelaCont.setRecomendacao(recomendacao);
                    avTelaCont.setUsuAtual(usuario);
                    avTelaCont.setVisible(true);
                    //  program = new S();
                    miniTela.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
       // } catch (SQLException ex) {
          //  Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
       // } catch (LineUnavailableException ex) {
       //     Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (IOException ex) {
         //   Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (UnsupportedAudioFileException ex) {
        //    Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
       // }

    }
}
