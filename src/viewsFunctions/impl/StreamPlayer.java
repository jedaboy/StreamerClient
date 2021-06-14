/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package viewsFunctions.impl;

import controllers.GerenciadorDeFluxo;
import viewsFunctions.TelaFactory;
import daoRepository.DaoFactory;
import daoRepository.Mp3DataTCP;
//import daoRepository.MusicaDao;
//import daoRepository.UsuarioDao;
import entities.Musica;
import entities.Usuario;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import viewsFunctions.TelaInterface;
import daoRepository.MusicaTCP;
import daoRepository.UsuarioTCP;
import daoRepository.impl.Mp3DataClientTCP;
import dbRepository.DB;
import entities.Mensagem;
import entities.MusicData;
import entities.Status;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jedaboy
 */
public class StreamPlayer extends javax.swing.JFrame {

    DefaultListModel lista = new DefaultListModel(); // cria uma lista compativel com  o campo do Jlist (propriedades, model, lista)
    DefaultListModel lista2 = new DefaultListModel(); // Esta lista2 é da jList que tem as musicas recomendadas
    ImageIcon image; //imagem que acompanha a musica
    MusicData musicData;

    private Usuario usuario;
    private Timer playTimer;
    private Socket skt;
    private AudioInputStream ain = null;
    private int aux;
    private int flag = 0;
    private int duracao = 0;
    private int cont = 0;
    private int b = 0;
    private int duracaoAnterior = 0;
    private int secondsToSkip = 0;
    private SourceDataLine sourceDataLine = null;
    private Mp3DataTCP mp3DataClientTCP;
    private boolean boolFlag = false;
    private boolean ignoreStateChange = false;
    private boolean started = false;
    private boolean bool = true;
    private int a;
    int repeat = 0;
    boolean recomendacao;

    private ObjectInputStream oIS = null;
    private ObjectOutputStream oOS = null;

    // getters e setters para saber qual é o usuario atual
    public void setUsuAtual(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuAtual() {
        return usuario;
    }

    // getters e setters para determinar qual a jlist a  ser exibida
    public void setRecomendacao(boolean recomendacao) {
        this.recomendacao = recomendacao;
    }

    public boolean getRecomendacao() {
        return recomendacao;
    }

    //Principal construtor da classe
    public StreamPlayer() throws IOException, SQLException {


    }

 


 
    private void actionMouseClicked(java.awt.event.MouseEvent evt) {                                    
        //labelPic.setIcon(image);
        //carrega imagemIcon com blob

        if (!started) {
            if (cont == 0) {
                try {
                    //oOS = new ObjectOutputStream(skt.getOutputStream());
                    // oIS = new ObjectInputStream(skt.getInputStream());
                    // Mensagem r = (Mensagem) oIS.readObject();
                    // Mensagem reply = null;   
                    // image = (ImageIcon) r.getParam("Image");
                    // labelPic.setIcon(image);
                    System.out.println("Cont: " + cont);
                    System.out.println("SourceLoad! ");
                    sourceLoad();
                } catch (SQLException ex) {
                    Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("sourceDataLine start! ");
                sourceDataLine.start();
                started = true;

            }
           
            

            playTimer.start();

        } else {
            System.out.println("sourceDataLine stop! ");
            sourceDataLine.stop();
            started = false;           
            playTimer.stop();

        }


    }                                   

    private void listaRecomendacoesMouseClicked(java.awt.event.MouseEvent evt) {                                                

       
                    new Thread() {
                        @Override
                        public void run() {
                            try {

                                sourceLoad();
                                sleep(70);
                                sourceDataLine.start();
                                started = true;
                                playTimer.start();

                                System.out.println("---------------------------------------FIM DA THREAD DE SKIP-------------------------------------------");
                            } catch (UnsupportedAudioFileException ex) {
                                Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }.start();
                }
                                                         

    //carrego o audio
    public void sourceLoad() throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        new Thread() {
            @Override
            public void run() {
                System.out.println("Tier 2");
                try {

                    InputStream audioSrc = skt.getInputStream();
                    // add buffer for mark/reset support

                    InputStream bufferedIn = new BufferedInputStream(audioSrc);
                    ain = AudioSystem.getAudioInputStream(bufferedIn);

                    if (flag == 0) {
                        System.out.println("Marca de Reset no segundo: " + cont);
                        ain.mark(Integer.MAX_VALUE);
                        flag = 1;
                    };

                    System.out.println(" Iniciando player de musica ");
                    tocaStreamDeMusica();
                    System.out.println("FIM DA THREAD SOURCELOAD ");

                } catch (IOException ex) {
                    Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(StreamPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }.start();
    }

 

    /////////////////////////////////////////// SOURCE DATA LINE IMPLEMENTACAO///////////////////////////////////////////////////////////
 

    //pote de ouro!
    private void selecionaPontoDaMusica(int secondsToSkip)
            throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {

        AudioFormat audioFormat = ain.getFormat();
        long bytesToSkip = 16384 * secondsToSkip;
        long justSkipped = 0;

        ain.skip(bytesToSkip);

        //System.out.println("");
    }

    long total = 0;

    //G(OLD)
    public void tocaStreamDeMusica()
            throws IOException, UnsupportedAudioFileException,
            LineUnavailableException {

        try {

            //informação sobre o formato da stream
            AudioFormat format = ain.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            // se o formato não for suportado de forma direta, tenta se efetuar a conversão
            // para PCM.
            if (!AudioSystem.isLineSupported(info)) {
                // Este é o formato PCM para o qual queremos converter nosso audio
                // Estes parametros são informações sobre o formato do audio 
                // não precisa entender para usos casuais.
                AudioFormat pcm
                        = new AudioFormat(format.getSampleRate(), 16,
                                format.getChannels(), true, false);
                // Get a wrapper stream around the input stream that does the
                // transcoding for us.

                //Pega um (wrapper: empacotamento(?) ao redor da stream de entrada que faz a transcrição
                //para nos(???)
                ain = AudioSystem.getAudioInputStream(pcm, ain);

                // atualiza o formato e a variavel "info para os novos dados que foram transcritos
                format = ain.getFormat();
                info = new DataLine.Info(SourceDataLine.class, format);
            }

            // Abre a linha através da qual tocaremos o stream de audio
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(format);

            // aloca um buffer para ler e escrever a stream de entrada na linha
            // Make it large enough to hold 4k audio frames*.
            // perceba que a SourceDataLine possui o proprio buffer interno.
            int framesize = format.getFrameSize();
            byte[] buffer = new byte[1024]; // o buffer
            int numbytes = 0;                               // a quantia de bytes
          
            // nos ainda não iniciamos a linha
            int bytesread = 0;
            while (bytesread != -1 && bool) {  // saimos do loop quando chegamos no fim da stream
                // Primeiro, lemos alguns bytes da stream de entrada
                // interruptor = Thread.interrupted();
                if (started) {
                    bytesread = ain.read(buffer, numbytes, buffer.length - numbytes);
                    // se não tiver mais bytes para se ler estamos encerrados
                }
                if (Thread.interrupted()) {
                    break;
                }

                numbytes += bytesread;
                total += bytesread;
                // Agora nos pegamos um pouco de bytes de audio  para escrever nesta linha,
                // tocamos a linha que ira reproduzir os dados que estiverem nela
                if (cont == 0 && !started && repeat != 1) {

                    sourceDataLine.start();

                    System.out.println("Tocando");
                    started = true;
                }

                // We must write bytes to the line in an integer multiple of
                // the framesize.  So figure out how many bytes we'll write.
                //precisamos escrever os bytes na linha em um inteiro multiplo
                //do tamnho do framesize, então .... quantos bytes nos iremos escrever
                int bytestowrite = (numbytes / framesize) * framesize;

                // Now write the bytes. The line will buffer them and play
                // them. This call will block until all bytes are written.
                //agora escrevemos os bytes a linha ira buffer(?) e então nos tocaremos ela, 
                // Esta chamada será bloqueada até que todos os bytes sejam gravados.
                if (started) {
                    sourceDataLine.write(buffer, 0, bytestowrite);
                }
                // If we didn't have an integer multiple of the frame size, 
                // then copy the remaining bytes to the start of the buffer.
                // Se não tivermos um múltiplo inteiro do tamanho do frame,
                // então copiamos os bytes restantes para o início do buffer.

                System.out.println(total);
                numbytes = 0;
            }

            // Now block until all buffered sound finishes playing.
            // Agora bloqueamos até que todo o som armazenado no buffer termine de tocar.
            //sourceDataLine.drain();
        } finally { // Always relinquish the resources we use
            //sempre feche os recusos que utilizar (guarde, encerre ou algo do genero)
            System.out.println("finally  Stream player");
            System.out.println(total);
            ain.skip(total);
            if (sourceDataLine != null) {
                //System.out.println("SOURCE DATA LINE IS CLOSED ");
                // sourceDataLine.close();
                // started = false;
            }
            if (ain != null) {
                //System.out.println("AUDIO INPUT STREAM IS CLOSED ");
                // ain.close();
            }
        }
    }


  
}
