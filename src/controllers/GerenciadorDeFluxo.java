/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package controllers;

import daoRepository.UsuarioTCP;
import entities.MusicData;
import entities.Usuario;
import java.io.IOException;
import java.sql.SQLException;
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
public class GerenciadorDeFluxo {

    private int contFlux = 0;
    private Usuario usuario;
    private UsuarioTCP usuarioTCP;
    private boolean recomendacao;
    GerenciadorDeTelas gTelas;
    //secção de continuidade
    private MiniTela miniTela;
    private boolean playing = false;

    MusicData musicData;

    public GerenciadorDeFluxo() {
    }

    public GerenciadorDeFluxo(MusicData musicData) throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        this.musicData = musicData;
    }

    public void setContFlux(int contFlux) {
        this.contFlux = contFlux;
    }

    public int getContFlux() {
        return contFlux;

    }

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

    public void setRecomendacao(boolean recomendacao) {
        this.recomendacao = recomendacao;
    }

    public boolean getRecomendacao() {
        return recomendacao;
    }

    public MiniTela getMiniTela() {
        return miniTela;
    }

    public void setMiniTela(MiniTela miniTela, boolean playing) {
        this.miniTela = miniTela;
        this.playing = playing;

    }

    public boolean getIsPlaying() {
        return playing;

    }

    public void fluxoPrincipal() {

        if (contFlux == 0) {

            gTelas = new GerenciadorDeTelas();
            gTelas.chamaTelaLogin();
        }
        if (contFlux == 1) {
            //é chamado pela classe logintela caso não tenha cadastro  
            gTelas = new GerenciadorDeTelas();
            gTelas.chamaTelaCadastro();
        }
        if (contFlux == 2) {
            //esta parte é chamada pela classe LoginTEla  após o login ser efetuado com sucesso    
            //passa o usuario atual para a classe Dashboard, o usuario atual é sempre passado para que haja a permanencia da ciencia de quem esta logado em todas as classes após o login

            if (playing == true) {
                gTelas = new GerenciadorDeTelas(musicData);
                gTelas.setMiniTela(miniTela, playing);
                if (usuario == null) {
                    System.out.println("Usuario Null");
                }

                if (usuarioTCP == null) {
                    System.out.println("Conexão Null FLUX");
                }

                gTelas.setUsuAtual(usuario);
                gTelas.setUsuTCPAtual(usuarioTCP);
                gTelas.chamaTelaDashboard();

            } else {
                gTelas = new GerenciadorDeTelas();
                if (usuario == null) {
                    System.out.println("Usuario Null");
                }

                if (usuarioTCP == null) {
                    System.out.println("Conexão Null FLUX");
                }
                gTelas.setUsuTCPAtual(usuarioTCP);
                gTelas.setUsuAtual(usuario);
                gTelas.chamaTelaDashboard();
            }

        }
        if (contFlux == 3) {
            //esta parte é chamada pela classe DashboardTela e o usuario atual é passado para o gerenciador de telas     
            gTelas = new GerenciadorDeTelas();
            gTelas.setUsuTCPAtual(usuarioTCP);
            gTelas.setUsuAtual(usuario);         
            gTelas.chamaTelaGenero();
        }
        if (contFlux == 4) {
            //esta parte é chamada pela classe DashboardTela e o usuario atual é passado para o gerenciador de telas     
            if (playing == true) {
                gTelas = new GerenciadorDeTelas(musicData);
                gTelas.setMiniTela(miniTela, playing);
                gTelas.setUsuAtual(usuario);
                gTelas.chamaTelaAvaliar();
            } else {
                gTelas = new GerenciadorDeTelas();
                gTelas.setRecomendacao(recomendacao);
                gTelas.setUsuAtual(usuario);
                gTelas.chamaTelaAvaliar();
            }
        }

    }
}
